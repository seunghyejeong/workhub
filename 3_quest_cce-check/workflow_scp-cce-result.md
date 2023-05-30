# 자동화 진행  shell script 만들기 

허접한 쉘 스크립트... 문법..

```shell
#!/bin/bash
  
set -e -x

SCRIPT_DIR=/var/vcap/jobs/cce-service/scripts
CCE_DIR=/var/vcap/cce
BOSH_NAME=micro-bosh
DEPLOYMENT_NAME=mysql
VM_NAME=mysql-broker

<%

if_p('cce_scripts') do |scripts|

  scripts.each do |script|
    script_name = script['script_name']
    set_env = ''
    if script['env_variable'] then set_env = script['env_variable'] end
    input = ''
    if script['params'] then input = script['params'] end
%>
echo <%= "#{set_env}" %> >>  ${SCRIPT_DIR}/cce-service.sh
echo "
./<%= "#{script_name}" %> << EOF
<%= spec.ip %>
<%= "#{input}" %>
EOF
" >>  ${SCRIPT_DIR}/cce-service.sh
echo "#######[CCE :: <%= "#{script_name}" %> ::  END]####################" >>  ${SCRIPT_DIR}/cce-service.sh

<%
  end
end

<%
  end
end
%>

mkdir -p ${CCE_DIR}
cp -r ${SCRIPT_DIR}/* ${CCE_DIR}
vim ${CCE_DIR}/move-to-inception.sh

echo "for loop in `ls /tmp | find . -type f name "*.txt"`
        do $loop
bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/*.txt ~/workspace
        done
        " >> ${CCE_DIR}/move-to-inception.sh



chmod +x ${CCE_DIR}/*.bin
chmod +x ${CCE_DIR}/*.sh

cd ${CCE_DIR}


source ${CCE_DIR}/cce-service.sh
source ${cCE_DIR}/move-to-inception.sh
```

: 내가 표현하고 싶은건 "TXT 파일이 여러개 있다면 그 txt파일 개수만큼 loop를 돌면서 scp 기능을 구현하여 inception으로 파일을 이동시켜줘!!" 임..;;ㅎ

오류

```shell

+ SCRIPT_DIR=/var/vcap/jobs/cce-service/scripts
+ CCE_DIR=/var/vcap/cce
+ BOSH_NAME=micro-bosh
+ DEPLOYMENT_NAME=mysql
+ VM_NAME=mysql-broker
+ mkdir -p /var/vcap/cce
+ cp -r /var/vcap/jobs/cce-service/scripts/apache_v4.1.bin /var/vcap/jobs/cce-service/scripts/cce-service.sh /var/vcap/jobs/cce-service/scripts/docker_v4.1.bin /var/vcap/jobs/cce-service/scripts/elasticsearch_v4.1.bin /var/vcap/jobs/cce-service/scripts/hadoop_v4.1.bin /var/vcap/jobs/cce-service/scripts/influxDB_v4.1.bin /var/vcap/jobs/cce-service/scripts/mongodb_v4.1.bin /var/vcap/jobs/cce-service/scripts/mysql_5.6_v4.1.bin /var/vcap/jobs/cce-service/scripts/mysql_5.7_v4.1.bin /var/vcap/jobs/cce-service/scripts/mysql_5.7_v4.1_PaaS-TA.bin /var/vcap/jobs/cce-service/scripts/mysql_8.0_v4.1.bin /var/vcap/jobs/cce-service/scripts/postgresql_v4.1.bin /var/vcap/jobs/cce-service/scripts/rabbitmq_v4.1.bin /var/vcap/jobs/cce-service/scripts/redis_v4.1.bin /var/vcap/jobs/cce-service/scripts/tomcat_v4.1.bin /var/vcap/cce
+ vim /var/vcap/cce/move-to-inception.sh
Vim: Warning: Output is not to a terminal
Vim: Warning: Input is not from a terminal
```

이제부터 아래와 같이 쉘스크립트 시행착오시리즈로 작성해보겠음.



# shell script

```shell

echo <%= "#{set_env}" %> >>  ${SCRIPT_DIR}/cce-check-to-inception.sh 

files=(`ls /tmp | find . -type f name "*.txt"`)

for file in ${files[@]}
do
echo bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/*.txt ~/workspace  >> ${SCRIPT_DIR}/cce-check-to-inception.sh
done

exit 0
```


# 배열이 통으로 들어갔구나~


```shell

#!/bin/bash

COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin

mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


declare -a files
cd ${COPY_DIR}
files=`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`         #txt파일을 모두 배열에 담음 
echo ${files}

length=${#files[@]}
echo ${length}

for (( i=0; i <= ${length}; i++))
do
        echo $i ${files[$i]}
done
```

결과

```consolel
ubuntu@paasta-ta-bami-inception-1:~/workspace$ source test.sh 
./test1.txt ./test2.txt ./test3.txt
1
0 ./test1.txt ./test2.txt ./test3.txt
1
```


# 경로를 배열에 담는것은 성공..

```shell
#!/bin/bash
  
COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin

mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


cd ${COPY_DIR}
files=(`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`)
echo ${files[*]}




for file in "${files[@]}"
do
        echo $i ${file[$i]}
done
```

결과

```console
ubuntu@paasta-ta-bami-inception-1:~/workspace$ source test.sh 
./test1.txt ./test2.txt ./test3.txt
./test1.txt
./test2.txt
./test3.txt
```

# cce-check-to-inception.sh 에 반복 돌며 text file 입력 된거 확인

```shell
#!/bin/bash
  
COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin
TMP_DIR=/tmp



mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


cd ${COPY_DIR}
files=(`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`)


for file in "${files[@]}"
do
        echo ${file[$i]} >> ${COPY_DIR}/cce-check-to-inception.sh
done
```

cce-check-to-inception.sh

```shell
./test1.txt
./test2.txt
./test3.txt
~                                                                                                                                                                      
~            
```
# 이젠 뭘 echo 하고 >> cce-check-to-inception.sh로 가야하지?  고민..

# result 폴더 생성까지 성공  . 파일 복사 안됨 

```shell
l####################### 검사된 script ---> inception ( 이동)################
mkdir -p ${TMP_DIR}
cp -r ${CCE_DIR}/* ${TMP_DIR}
cd ${TMP_DIR}


echo files=(`ls ${TMP_DIR} | find -maxdepth 1 -name "*.txt"`)        # .txt파일을 찾아서 files에 배열로 담음

#반복문 돌며 files의 개수 만큼 scp 해주기
echo
for file in "${files[@]}"                               
do
        echo "bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/${file[$i]} /tmp"  >>  ${TMP_DIR}/cce-checkresult-throw-inception.sh 
done
echo
############################## cce-check-to-inception.sh 파일 작성 #########
```

# result.sh에는 scp문법 하나만 들어가있어도 될거같다는 생각.

```shell
#!/bin/bash
CCE_DIR=/var/vcap/cce
TMP_DIR=/tmp/cce-result
BOSH_NAME=micro-bosh
DEPLOYMENT_NAME=mysql
VM_NAME=mysql-broker




files=(`ls ${CCE_DIR} | find -maxdepth 1 -name "*.txt"`)

exit
exit

mkdir -p ${TMP_DIR}
cd ${TMP_DIR}

for file in "${files[@]}"
do
 bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/${file[$i]} /tmp >> {TMP_DIR}/cce-result-scp.sh
done

chmod +x ${TMP_DIR}/*.sh
source {TMP_DIR}/cce-result-scp.sh
```

# 뭔가 엄청난 배열? 이런게 필요한게 아니었다.

1. scp는 여러파일을 한번에 가져온다.
2. echo는 "" 가 필요없다.
3. ">>" 기준 우측에 파일명에 좌측의 명령어를 복사해준다.


```shell
## cce-result 파일 목록 검사 
#files=(`ls ${CCE_DIR} | find -maxdepth 1 -name "*.txt"`)        # .txt파일을 찾아서 files에 배열로 담음

## inception으로 나가기
exit
exit


## cce-result 폴더 생성
mkdir -p ${TMP_DIR}
cd ${TMP_DIR}
## scp.sh 생성 
touch scp-cce-result.sh
## scp 실행 명령어 echo
echo bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/*.txt ${TMP_DIR} >> ${TMP_DIR}/scp-cce-result.sh
ㅊㅇㅊㅇ 
chmod +x ${TMP_DIR}/*.sh
source ${TMP_DIR}/scp-cce-result.sh
```

cce file을 복사 안해줌..
cd 
```shell
## cce결과 이동 
mkdir -p ${TMP_DIR}
cp ${CCE_DIR}/*.txt ${TMP_DIR}

## inception으로 나가기
exit


## cce-result 폴더 생성
mkdir -p ${TMP_DIR}
cd ${TMP_DIR}
## scp.sh 생성 
touch scp-cce-result.sh
## scp 실행 명령어 echo
echo bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:/tmp/*.txt ${TMP_DIR} >> ${TMP_DIR}/scp-cce-result.sh

echo chmod +x ${TMP_DIR}/*.sh >> ${TMP_DIR}/scp-cce-result.sh
echo source ${TMP_DIR}/scp-cce-result.sh >> ${TMP_DIR}/scp-cce-result.sh
```




# scp 최종 스크립트

```shell

#!/bin/bash
  
BOSH_NAME=micro-bosh
DEPLOYMENT_NAME=mysql
VM_NAME=mysql
TMP_DIR=/tmp/scp-cce-result

## cce-result 폴더 생성
mkdir ${TMP_DIR}
cd ${TMP_DIR}
## scp.sh 생성 
touch scp-cce-result.sh
## scp 실행 명령어 echo
echo bosh -e ${BOSH_NAME} -d ${DEPLOYMENT_NAME} scp ${VM_NAME}:${TMP_DIR}/*.txt ${TMP_DIR}

chmod +x ${TMP_DIR}/*.sh 
source ${TMP_DIR}/scp-cce-result.sh 
```

