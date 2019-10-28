#!/bin/bash
cd /cristi
sudo fuser -k 8090/tcp
sudo java -jar smarthouse-0.0.1-SNAPSHOT.jar
