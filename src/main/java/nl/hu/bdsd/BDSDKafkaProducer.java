package nl.hu.bdsd;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Producer Code for BDSD-course.
 *
 * @author Roelant Ossewaarde (Hogeschool Utrecht), based on example code from www.tutorialkart.com
 */

public class BDSDKafkaProducer extends Thread {
    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;
    private static final String KAFKA_SERVER_URL = "localhost";
    private static final int KAFKA_SERVER_PORT = 9092;
    private static final String CLIENT_ID = "BDSDKafkaProducer";
    private static final Set<String> filenames = new HashSet<>();

    public BDSDKafkaProducer(String topic, Boolean isAsync, Set<String> filenames) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
        this.filenames.addAll(filenames);

    }

    private List<String> readTextFile(String filename) throws IOException {
        URL url = getClass().getResource(filename);
        File file = new File(url.getPath());
        List<String> resultList = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    resultList.add(line);
                }
            }
        }
        return resultList;
    }

    public void run() {
        int messageNo = 1;
        try {
            for (String filename : filenames) {
                for (String line : readTextFile(filename)) {
                    String messageStr = line;
                    long startTime = System.currentTimeMillis();
                    if (isAsync) { // Send asynchronously
                        producer.send(new ProducerRecord<>(topic,
                                messageNo,
                                messageStr), new MyCallback(startTime, messageNo, filename, messageStr));
                    } else { // Send synchronously
                        try {
                            producer.send(new ProducerRecord<>(topic,
                                    messageNo,
                                    messageStr)).get();
                            System.out.println("Sent message: (" + messageNo + ", " + filename + ", " + messageStr + ")");
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    ++messageNo;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}

class MyCallback implements Callback {
    private final long startTime;
    private final int key;
    private final String message;
    private final String filename;
    public MyCallback(long startTime, int key, String filename, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
        this.filename=filename;
    }

    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                    "message(" + key + ", " + filename + ", " + message + ") sent to partition(" + metadata.partition() +
                            "), " +
                            "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }
}