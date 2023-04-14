#!/bin/bash

DEPLOYMENT_NAME=portal-api
VMS=(`bosh ds | cut -f 1 | sort | uniq `)
VMS_LENGTH=${#VMS[@]}
INSTAN_NAME=(`bosh -d ${DEPLOYMENT_NAME} vms | cut -f 1 | sort  |  uniq `)
INSTAN_LENGTH=${#INSTAN_NAME[@]}

#echo ${DEPLOYMENT_NAME}

#for ((i =1; i < ${VMS_LENGTH}; i++))
#do

#        VMLIST+=${VMS[i]}

#done

#echo -----------
#echo "VMLIST:" ${VMLIST}
#echo -----------

for INSTANCE in ${INSTAN_NAME[@]}
do
#        echo "INSTANCENAME: " ${INSTANCE}
        bosh -e micro-bosh -d ${DEPLOYMENT_NAME} scp ./cce-scripts.zip ${INSTANCE[@]}:/tmp/
done
