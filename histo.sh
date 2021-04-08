#! /bin/bash

containsElement () {
  local e match="$1"
  shift
  for e; do
	[[ "$e" == "$match" ]] && return 0;
done
  return 1
}

DELIMITER=","
topic=alarmTopic
waitTime=1000
command="docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 --topic $topic --from-beginning \
--timeout-ms $waitTime"

alarmTypes=()
typesDistinc=()

jsons=$($command | grep alarm)

for line in $jsons
do
	alarmType=(`echo $line | jq -r '.alarmType'`)
	alarmTypes+=("${alarmType}${DELIMITER}")

	containsElement $alarmType "${typesDistinc[@]}"  || typesDistinc+=($alarmType)

done

echo "Histogram of ${typesDistinc[@]} occurances"
echo
echo "Alarm type Amount"
histo="==============================================================================================================+"
for atype in ${typesDistinc[@]}
do
	count=(`echo "${alarmTypes[@]}" | tr "${DELIMITER}" "\n" | grep -c "$atype"`)
	echo -n "$atype      "
	echo ${histo:0:$count}
done

echo
echo "Done"
