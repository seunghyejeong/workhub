#!/bin/bash

DEPLOYMENT_NAME=paasta
VMS=(`bosh ds | cut -f 1 | sort | uniq `)
INSTAN_NAME=(`bosh -d ${DEPLOYMENT_NAME} vms | cut -f 1 | sort  |  uniq `)
IP=(`bosh -d ${DEPLOYMENT_NAME} vms | cut -f 4  | sort | uniq`)
LINUX_INFO_PATH=~/workspace/cce2023/cce-result/linuxinfo


mkdir -p ${LINUX_INFO_PATH}

for INSTANCE in ${INSTAN_NAME[@]}
do
        mkdir -p ${LINUX_INFO_PATH}/api/${INSTANCE[@]}
        bosh -e micro-bosh -d ${DEPLOYMENT_NAME} scp ${INSTANCE[@]}:/tmp/scripts/*.txt ${LINUX_INFO_PATH}
done

