package nl.hu.bdsd;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roelant on 27/05/2019.
 */
public class BDSDKafkaProducerRunner {
    public static final String TOPIC = "sentences";
    public static final Set<String> filenames = new HashSet<>();

    private static void addFiles() {
        filenames.add("/sentences.nl.txt");
        filenames.add("/sentences.en.txt");
    }

    public static void main(String[] args) {
        boolean isAsync = false;
        addFiles();
        BDSDKafkaProducer producerThread = new BDSDKafkaProducer(TOPIC, isAsync, filenames);
        producerThread.start();
    }
}
