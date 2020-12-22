# Kafka-producer te gebruiken bij opdracht "Letterfrequenties"
Deze code moet je gebruiken als Kafka-producer bij het oplossen van de opdracht "Letterfrequenties" (https://canvas.hu.nl/courses/1111/assignments/25365). De producer gaat er van uit dat er al een topic bestaat in Kafka met de naam `sentences`. Je moet dat dus aanmaken, of in de code van dee producer aanpassen dat er een ander topic gebruikt wordt. 

## Code uitvoeren

De volgende code bouwt en voert de producer uit. 

`mvn package && mvn exec:java -Dexec.mainClass="nl.hu.bdsd.BDSDKafkaProducerRunner"`

## Voorbeeld-run:


    Roelants-MacBook-Pro:kafkaproducer roelant$ mvn package -Dmaven.test.skip=true && mvn exec:java -DexemainClass="nl.hu.bdsd.BDSDKafkaProducerRunner"
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------------------------------------------------------------
    [INFO] Building kafka-producer 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ kafka-producer ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 2 resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ kafka-producer ---
    [INFO] Nothing to compile - all classes are up to date
    [INFO] 
    [INFO] --- maven-resources-plugin:3.0.2:testResources (default-testResources) @ kafka-producer ---
    [INFO] Not copying test resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ kafka-producer ---
    [INFO] Not compiling test sources
    [INFO] 
    [INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ kafka-producer ---
    [INFO] Tests are skipped.
    [INFO] 
    [INFO] --- maven-jar-plugin:3.1.0:jar (default-jar) @ kafka-producer ---
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 1.399 s
    [INFO] Finished at: 2019-05-27T22:56:43+02:00
    [INFO] Final Memory: 13M/309M
    [INFO] ------------------------------------------------------------------------
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------------------------------------------------------------
    [INFO] Building kafka-producer 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ kafka-producer ---
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    Sent message: (1, /sentences.en.txt, Why Apollo 10 Stopped Just 47,000 Feet From the Moon In a year when)
    Sent message: (2, /sentences.en.txt, we’ll celebrate Apollo 11’s 50th anniversary, it’s worth remembering)
    Sent message: (3, /sentences.en.txt, the pathfinders who completed the same mission with one critical)
    Sent message: (4, /sentences.en.txt, order: don’t actually land on the moon.  The command service module,)
    Sent message: (5, /sentences.en.txt, dubbed “Charlie Brown” for the Apollo 10 mission to the moon, viewed)
    Sent message: (6, /sentences.en.txt, from the lunar module “Snoopy” in 1969.  Soon we will recognize the)
    Sent message: (7, /sentences.en.txt, 50th anniversary of the first humans to walk on the moon.  We remember)

```

