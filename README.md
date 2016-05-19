# YourMDTask

This is the soultion to the YourMD problem. More about the problem can be found at the file "Coding assignment for a Java Developer position in YourMD "

Build and Run Instructions:

If you already have Gradle installed - gradle build && java -jar build\libs\yourmdmicroservice-0.1.0.jar (if using windows, take care about / or \)

else using gradle wrapper you can run as below

./gradlew build && java -jar build/libs/yourmdmicroservice-0.1.0.jar (if using windows, take care about / or \)

The above instruction uses the default phrases file located, provided by the test. if you want to supply new phrases file,
please use the parameter --phrasesFile=<absolute path to phrases file > while starting the server/jar


Testing can be done by  curl -Lk "http://localhost:8080/matchphrase?input=I%20have%20a%20headache%20sore%20throat"


