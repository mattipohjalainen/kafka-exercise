# Alarm exercise

Alarm exercise contains Kotlin based application to produce and consume alarm
messages through Kafka. For producing alarms the application provides REST
API for sending alarms. The application contains one consumer, that currently
does nothing but just writes the consumed alarm to log. In addition, application
provides REST API to read alarms from Kafka.

Application contains bash script (histo.sh) to draw histogram of alarm types of alarms 
sent to kafka, and a python script to draw timeline of alarmTimes of the alarms
sent to kafka. Alarm times are truncated to hours, timeliner.py shows number
of alarms in each hour.

## Building application

In kafka-exercise folder, hit './gradlew build' command. As result,
alarm-exercise-&lt;version&gt;.jar appears to build/libs folder.

## Preparing environment

In order to draw histogram, make sure that jq JSON processor is available in
your environment. In order to draw timeline, make sure following python modules
are installed in your environment:
- requests
- json
- pandas
- matplotlib
- datetime

Install missing modules, for instance 'pip install requests'.
Make sure DOCKER_HOST_IP environment variable has value localhost.

Start kafka: 'docker-compose up -d'

Start alarm-service by executing alarm-exercise-&lt;version&gt;.jar

## Sending alarms to alarm-exercise

Use for example curl or postman to send alarm to alarm exercise API:
curl -X POST http://localhost:8080/api/message --data '{"alarmingNode":"Matti","alarmType":"Teppo"}' -H "Content-Type: application/json"

Or: curl -X POST http://localhost:8080/api/message --data '{"alarmingNode":"Matti","alarmType":"Teppo", "alarmTime":"2021-04-07T18:05:22"}' -H "Content-Type: application/json"

Alarm time is optional, if missing from request alarm-exercise uses current 
date and time as alarm time.

## Drawing histogram about alarm types

Just execute histo.sh.

## Drawing timeline of amount of alarms hourly

Execute timeliner.py: './timeliner.py'