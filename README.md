De werking van de applicatie werkt door middel van apache kafka.
Zoals in de foto (screenshot) te zien is, worden de inkomende teksten van de producer, behandeld door de consumer.

Om dit te laten werken worden er 2 processen gestart:
 -zookeeper-server-start.bat c:\kafka\config\zookeeper.properties
 -kafka-server-start.bat c:\kafka\config\server.properties

Hiermee word zookeeper en de server aan het werk gezet.
Binnen kafka is er een topic aangemaakt voor sentences. Die bekijkt of er zinnen worden geproduceerd.
De producer van de applicatie, verzend/streamt data naar de topic
Deze taak wordt aan het werk gezet via maven met:
mvn package && mvn exec:java -Dexec.mainClass="nl.hu.bdsd.BDSDKafkaProducerRunner"

De consumer is hierop ge-subscribed en al het gestreamde data wordt hierdoor opgepakt.
Deze consumer zorgt ervoor dat de werking van mijn languageidentifier, worddt uitgevoerd op de binnenstromende tekst.

Deze languageidentifier werkt aan de hand van bigrams.
Om dit te laten werken, is er een tokenizer die ervoor zorgt dat er tokens gemaakt worden van de teksten.
Deze worden vervolgens gebruikt om de bigrams te maken.
Uiteindelijk kijkt de languageidentifier na de uitslagen en wordt er vergeleken welke taal de hoogste waarschijnlijkheid heeft.
