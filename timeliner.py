#!/usr/local/bin/python

import requests
import json
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
import datetime

url = "http://localhost:8080/api/get-alarms"
response = requests.get(url)

if(response.ok):
    jsonData = json.loads(response.content)

    print("The response contains {0} properties".format(len(jsonData)))
    print("\n")

    alarmsByTime = {}

    for alarm in jsonData:
        alarmTimeStamp = alarm['alarmTime']

        try:
            alarmTimeObj = datetime.datetime.strptime(alarmTimeStamp, '%Y-%m-%dT%H:%M:%S.%f')
        except:
            # just in case times given manually without microseconds
            alarmTimeObj = datetime.datetime.strptime(alarmTimeStamp, '%Y-%m-%dT%H:%M:%S')
        # truncate times to hours
        alarmTime = alarmTimeObj.replace(minute=0, second=0, microsecond=0)

        if not alarmTime in alarmsByTime:
            alarmsByTime[alarmTime] = 1
        else:
            alarmsByTime[alarmTime] += 1

    if (alarmsByTime):
        series = pd.Series(alarmsByTime)
        print(series)
        ax = plt.figure().gca()
        ax.yaxis.set_major_locator(MaxNLocator(integer=True))
        series.plot()
        ax.set_ylim(ymin=0)
        plt.show()
    else:
        print("No alarms available")

print("Done")
