#!/bin/bash
clear
echo "Clean ..."
rm -rf ~/.m2/repository/knossys/
rm -rf ./lib/knossys/knossys/1.0.0/*
cp -v ../DBSerialization/DBSerialization-master/target/dbserialization-1.0.0.jar ./lib/knossys/dbserialization/1.0.0
rm -rf ./target
