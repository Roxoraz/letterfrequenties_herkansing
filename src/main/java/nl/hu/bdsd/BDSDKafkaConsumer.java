package nl.hu.bdsd;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Scanner;
import nl.hu.bdsd.LanguageIdentifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;
import java.util.*;


public class BDSDKafkaConsumer extends Thread {
    private static Logger log = LoggerFactory.getLogger("BDSDKafkaConsumer");
    private final String topic;
    private final Boolean isAsync;
    private final KafkaConsumer<String, String> kafkaConsumer;

    public BDSDKafkaConsumer(String topic, boolean isAsync) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("client.id", "BDSDKafkaProducer");
        properties.put("group.id", "mygroup");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer <String, String>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    public static final String TOPIC = "sentences";

    public static void main(String[] args) {
        boolean isAsync = false;
        BDSDKafkaConsumer consumerThread = new BDSDKafkaConsumer(TOPIC, isAsync);
        consumerThread.start();
    }

    public static String sortCountBigrams(ArrayList<String> bigrams) {
        String outputResult = "";
        Collections.sort(bigrams);
        String curr = bigrams.get(0);
        int counter = 1;
        for (String next : bigrams) {
            if (next.equals(curr)) {
                counter++;
            } else {
                outputResult += curr + " " + Double.toString(counter) + " ";
                counter = 1;
                curr = next;
            }
        }
        return outputResult;
    }


    public void run() {
        Set topics = new HashSet<String>();
        topics.add(topic);
        kafkaConsumer.subscribe(topics);
        pollForNewRecords(kafkaConsumer);
    }

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i < str.length() - n + 1; i++)
            ngrams.add(str.substring(i, i + n));
        return ngrams;
    }

    private void pollForNewRecords(KafkaConsumer consumer) {
        Scanner in = new Scanner(System.in);
        LanguageIdentifier li = new LanguageIdentifier();
        try {
            li.train("english",  new File("files/english.txt"));
            li.train("dutch", new File("files/dutch.txt"));
        } catch(Exception e) {
            System.out.println("Error in training text: " + e);
        }

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records)
                {
                    System.out.println("Message: (key: " + record.key() + ", with value: " + record.value() + ") at on partition " + record.partition() + " at offset " + record.offset());
                    String str1 = record.value();
                    String output = li.evaluate(str1);
                    System.out.println("Mmmm.. That text was " + output);
                    System.out.println("-------------------------------------");
                }
            }
        } finally {
            consumer.close();
        }
    }
}