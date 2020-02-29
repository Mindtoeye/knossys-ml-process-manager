#!/bin/bash
clear
./clean.sh

mvn compile assembly:single

java -cp ./target/kmlprocessmanagement-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.knossys.rnd.process.FauxTaskManager2
