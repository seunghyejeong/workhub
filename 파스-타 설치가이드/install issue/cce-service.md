---
title: cce-check install/  issue / troble 
date: 2023-01-02
---


# 3) cce-cehck release 사용
    .sh 실행했을 때 정상적으로 넘어오는지 확인
    release 파일을 커스터마이징 해서 배포 이후 파일을 inception으로 이동.
    : 진행중


# troble

```shell
ID   State       Started At                    Finished At                   User   Deployment  Description        Result  
664  processing  Wed Jan  4 08:30:37 UTC 2023  -                             admin  mysql       create deployment  -  
642  timeout     Wed Jan  4 03:32:47 UTC 2023  Wed Jan  4 03:32:47 UTC 2023  admin  mysql       create deployment  -  
```
: 왜안되지 ?

# troble err.out 

```shell
Task 664 | 08:59:28 | L executing post-stop: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary)
Task 664 | 08:59:29 | L installing packages: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary)
Task 664 | 08:59:31 | L configuring jobs: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary)
Task 664 | 08:59:31 | L executing pre-start: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary)
Task 664 | 08:59:32 | L starting jobs: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary)
Task 664 | 08:59:43 | L executing post-start: mysql-broker/194ed540-9554-43c7-b40e-e7fb76d34673 (0) (canary) (00:00:30)
Task 664 | 00:49:02 | Error: Timed out sending 'get_task' to instance: 'mysql/42fa33d3-bb81-4f2c-853b-c202088574e5', agent-id: '861a-c077-4ca9-bf16-876209926a59' after 45 seconds

Task 664 Started  Wed Jan  4 08:30:42 UTC 2023
Task 664 Finished Thu Jan  5 00:49:02 UTC 2023
Task 664 Duration 16:18:20
Task 664 error

Updating deployment:
  Expected task '664' to succeed but state is 'error'

Exit code 1
```

: bosh tasks

```shell
ubuntu@paasta-ta-bami-inception-1:~/workspace/service-deployment/mysql$ bosh tasks
Using environment '10.160.61.6' as client 'admin'

ID  State  Started At  Finished At  User  Deployment  Description  Result  

0 tasks

Succeeded
```

: logs 확인하기

: bosh tasks 664 --debug

```shell

{"time":1672879742,"error":{"code":450002,"message":"Timed out sending ''get_task'' to instance: ''mysql/42fa33d3-bb81-4f2c-853b-c202088574e5'', agent-id: ''8610844a-c077-4ca9-bf16-876209926a59'' after 45 seconds"}}
', "result_output" = '', "context_id" = '' WHERE ("id" = 664)
D, [2023-01-05T09:49:02.059191 #30850] [task:664] DEBUG -- DirectorJobRunner: (0.000985s) (conn: 8940) COMMIT
I, [2023-01-05T09:49:02.059413 #30850] []  INFO -- DirectorJobRunner: Task took 16 hours 18 minutes 24.450094337000337 seconds to process.

Task 664 error

Capturing task '664' output:
  Expected task '664' to succeed but state is 'error'

Exit code 1
```

: mysql ssh login 
..뭐찾을수가없다..ㅎㅎ

# proxy ip 변경 후 재배포 -- 실패 

1. 원인 파악
  - cf apps에 남아있는 지우지 못하는 app이 원인이 될 수 있나 ?
  - 혹은 route되어있는 app들로 인해 그 영역대의 mysql이 이용되고 있다고 생각하나 ..?

"delayed_jobs"



bosh/0:/var/vcap/sys/log/postgres-cce
initdb: warning: enabling "trust" authentication for local connections
You can change this by editing pg_hba.conf or using the option -A, or
--auth-local and --auth-host, the next time you run initdb.

# 실패 .. 설치 lock없이 끊는 방법

```shell
ubuntu@paasta-ta-bami-inception-1:~/workspace/bami/cce-check-master/release$ bosh -d mysql logs
Using environment '10.160.61.6' as client 'admin'

Using deployment 'mysql'

Task 702

Task 702 | 04:55:32 | Fetching logs for mysql/6cf35c41-6583-48cd-b5fc-dbde565e00f3 (0): Finding and packing log files
Task 702 | 04:55:32 | Fetching logs for mysql/3a53de69-094c-4952-a56f-5a56a1950d5b (2): Finding and packing log files
Task 702 | 04:55:32 | Fetching logs for proxy/49e0626e-2592-45a5-b914-f4a3c6ed4cfd (0): Finding and packing log files
Task 702 | 04:55:32 | Fetching logs for mysql-broker/e16bcf96-3f68-4df5-b71c-2823eebd913d (0): Finding and packing log files
Task 702 | 04:55:32 | Fetching logs for mysql/b9e3dcec-9463-4548-b956-0b0de73a179f (1): Finding and packing log files
Task 702 | 04:55:33 | Fetching logs for proxy/49e0626e-2592-45a5-b914-f4a3c6ed4cfd (0): Finding and packing log files (00:00:01)
Task 702 | 04:55:33 | Fetching logs for mysql-broker/e16bcf96-3f68-4df5-b71c-2823eebd913d (0): Finding and packing log files (00:00:01)
Task 702 | 04:56:17 | Fetching logs for mysql/6cf35c41-6583-48cd-b5fc-dbde565e00f3 (0): Finding and packing log files (00:00:45)
                    L Error: Timed out sending 'fetch_logs' to instance: 'mysql/6cf35c41-6583-48cd-b5fc-dbde565e00f3', agent-id: 'b6fc2086-2275-48c6-bca0-486dc5c7d530' after 45 seconds
Task 702 | 04:56:17 | Fetching logs for mysql/3a53de69-094c-4952-a56f-5a56a1950d5b (2): Finding and packing log files (00:00:45)
                    L Error: Timed out sending 'fetch_logs' to instance: 'mysql/3a53de69-094c-4952-a56f-5a56a1950d5b', agent-id: 'bd1c137a-b6b5-46a5-9b65-dd82f14a2959' after 45 seconds
Task 702 | 04:56:17 | Fetching logs for mysql/b9e3dcec-9463-4548-b956-0b0de73a179f (1): Finding and packing log files (00:00:45)
                    L Error: Timed out sending 'fetch_logs' to instance: 'mysql/b9e3dcec-9463-4548-b956-0b0de73a179f', agent-id: '6aaec542-7c02-4e70-8cae-3790c410b608' after 45 seconds
Task 702 | 04:56:17 | Error: Timed out sending 'fetch_logs' to instance: 'mysql/6cf35c41-6583-48cd-b5fc-dbde565e00f3', agent-id: 'b6fc2086-2275-48c6-bca0-486dc5c7d530' after 45 seconds

Task 702 Started  Thu Jan  5 04:55:32 UTC 2023
Task 702 Finished Thu Jan  5 04:56:17 UTC 2023
Task 702 Duration 00:00:45
Task 702 error

Fetching logs:
  Expected task '702' to succeed but state is 'error'

Exit code 1
```

: 하면 로그를 받고 알아서 끊긴다..

# ssh jumpbox login 

```shell
bosh/0:/var/vcap/sys/log/pre-start-script# vi pre-start.stdout.log
```

```shell
added disable_symlinks in nginx.conf
added root default config  in nginx.conf
modified libreadline-so-6-issue-in-ubuntu-18-04
                                         
"pre-start.stdout.log" 3 lines, 126 bytes
```


# 생각해보니까.. cce.yml / cce-chek.yml
- 사용자 guide에  cce 조취를 취하여 배포하고 싶을 때 -o operation/cce.yml을 실행하도록 되어있다.

- 지금까지 deploy.sh 파일에 -o 옵션을 cce.yml 과 cce-check를 함께 넣어 배포했다.

- 그런데 cce-chek를 하는 관리자는 cce.yml을 적용시킨 즉 cce 조취를 취하여 deploy하는것이 아니다.

- 그래서 deploy.sh에 -o operation/cce.yml을 빼고 -o cce-check.yml만 넣어서 deploy했다.

```shell
Task 719 | 06:06:47 | Compiling packages: op-mysql-java-broker/d66383ebb0a3bf65bfb3ed6e052eb30c35a06b4057da4bf4006c464c379f9c4a
Task 719 | 06:06:47 | Compiling packages: openjdk/6a6af1fd7eebab1c4d975a14c1ef269faeb62c01dc45de66579af51f710c34de
Task 719 | 06:06:47 | Compiling packages: pxc-cluster-health-logger/54d48f99dd24cd1531db975c8378ae3aea7f96be8d51143aed12e40274601f77
Task 719 | 06:06:47 | Compiling packages: galera-init/a75bf3b51c760faeaf0c6203afe91fde51ded2ab2d44bd4a7c22c2c65a3d8632
Task 719 | 06:06:47 | Compiling packages: galera-agent/dc98348485971c6624072f38c6c491ed403b4c354088eb0096d52e5ee97c1810
Task 719 | 06:09:00 | Compiling packages: openjdk/6a6af1fd7eebab1c4d975a14c1ef269faeb62c01dc45de66579af51f710c34de (00:02:13)
Task 719 | 06:09:29 | Compiling packages: pxc-cluster-health-logger/54d48f99dd24cd1531db975c8378ae3aea7f96be8d51143aed12e40274601f77 (00:02:42)
Task 719 | 06:09:30 | Compiling packages: op-mysql-java-broker/d66383ebb0a3bf65bfb3ed6e052eb30c35a06b4057da4bf4006c464c379f9c4a (00:02:43)
Task 719 | 06:09:33 | Compiling packages: galera-init/a75bf3b51c760faeaf0c6203afe91fde51ded2ab2d44bd4a7c22c2c65a3d8632 (00:02:46)
Task 719 | 06:09:33 | Compiling packages: galera-agent/dc98348485971c6624072f38c6c491ed403b4c354088eb0096d52e5ee97c1810 (00:02:46)
``` 

결과가 조금 다르긴 한데 ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,, 아직 모르겠음 ㅠ
  : 실패 


# 질문

1. runtime-config를 올리면 기본 설치만 하더라도 배포된 서비스가 설치되는가 ?
    : yes runtime에 말려있기 때문에 기본 설치만 해도 cce-check "Linux"가 실행되는것.
```shell
  # DEPLOY
bosh -e ${BOSH_ENVIRONMENT} -n -d mysql deploy --no-redact mysql.yml \
    -l ${COMMON_VARS_PATH} \
    -l vars.yml \
```

: 배포했는데 

```shell
addons:
+ - jobs:
+   - name: bpm
+     release: bpm
+   name: bpm
+ - include:
+     stemcell:
+     - os: ubuntu-trusty
+     - os: ubuntu-xenial
+     - os: ubuntu-bionic
+   jobs:
+   - name: cce-common
+     release: cce-check
+   name: cce-check
+ - include:
+     stemcell:
+     - os: ubuntu-trusty
+     - os: ubuntu-xenial
+     - os: ubuntu-bionic
```

로 올라옴 그리고 cc-cehck에 stemcell ubuntu-bionic을 추가해주었음.

# linux 파일만 업로드 되었다.

1. mysql ssh 접속 
```shell
mysql/b0f7623a-986b-4f05-80b1-b5d0e53ecf5b:~$ sudo su
mysql/b0f7623a-986b-4f05-80b1-b5d0e53ecf5b:/var/vcap/bosh_ssh/bosh_659c25608fbd469# cd /var/vcap/
mysql/b0f7623a-986b-4f05-80b1-b5d0e53ecf5b:/var/vcap# cd cce/
mysql/b0f7623a-986b-4f05-80b1-b5d0e53ecf5b:/var/vcap/cce# ll
total 600
drwxr-xr-x  2 root root   4096 Jan  6 16:04 ./
drwxr-xr-x 12 root root   4096 Jan  6 16:07 ../
-rwxr-x--x  1 root root  30598 Jan  6 16:02 apache_v4.1.bin*
-rwxr-x--x  1 root root    134 Jan  6 16:02 cce-common.sh*
-rwxr-x--x  1 root root     97 Jan  6 16:02 cce-service.sh*
-rwxr-x--x  1 root root  43516 Jan  6 16:02 docker_v4.1.bin*
-rwxr-x--x  1 root root  24177 Jan  6 16:02 elasticsearch_v4.1.bin*
-rwxr-x--x  1 root root  25651 Jan  6 16:02 hadoop_v4.1.bin*
-rwxr-x--x  1 root root  19494 Jan  6 16:02 influxDB_v4.1.bin*
-rw-r--r--  1 root root   4022 Jan  6 16:04 Linux_c31166ae-6b0d-48d8-b98d-dc913c038cb2_10.160.64.121_0106_Ref.txt
-rw-r--r--  1 root root  28370 Jan  6 16:04 Linux_c31166ae-6b0d-48d8-b98d-dc913c038cb2_10.160.64.121_0106.txt
-rwxr-x--x  1 root root 114311 Jan  6 16:02 linux_v4.1.bin*
-rwxr-x--x  1 root root  42296 Jan  6 16:02 mongodb_v4.1.bin*
-rwxr-x--x  1 root root  34997 Jan  6 16:02 mysql_5.6_v4.1.bin*
-rwxr-x--x  1 root root  35307 Jan  6 16:02 mysql_5.7_v4.1.bin*
-rwxr-x--x  1 root root  36188 Jan  6 16:02 mysql_5.7_v4.1_PaaS-TA.bin*
-rwxr-x--x  1 root root  35829 Jan  6 16:02 mysql_8.0_v4.1.bin*
-rwxr-x--x  1 root root  28698 Jan  6 16:02 postgresql_v4.1.bin*
-rwxr-x--x  1 root root  18300 Jan  6 16:02 rabbitmq_v4.1.bin*
-rwxr-x--x  1 root root  24562 Jan  6 16:02 redis_v4.1.bin*
-rwxr-x--x  1 root root  27520 Jan  6 16:02 tomcat_v4.1.bin*
```

: 되었으나 linux파일만 업로드 되었음 ( linux는 runtime-config만 올렸을 때도 되긴 함.)

# inception으로 파일 옮기기

```shell
ubuntu@paasta-ta-bami-inception-1:~$ bosh -e micro-bosh -d mysql scp mysql-broker:/tmp/Linux_a3cf3f3c-b55c-4ba5-bd07-0df3667ffc48_10.160.64.124_0106.txt ~/workspace
Using environment '10.160.61.6' as client 'admin'

Using deployment 'mysql'

Task 954. Done
mysql-broker/d89e4ce4-0a0d-461b-a2bf-7d44e863d7cb: stderr | Unauthorized use is strictly prohibited. All access and activity
mysql-broker/d89e4ce4-0a0d-461b-a2bf-7d44e863d7cb: stderr | is subject to logging and monitoring.

Succeeded
```


# TRY(mysql ccecheck ) - mysql cf apps 설치 후 root 비밀번호 생성해주기 

1. 이유
  mysql cce-check yml파일에 mysql root paasword를 import하는게 있어서 ..? 


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
# delete  releasell

bosh -d mysql deld
bosh clean-up --all
bosh delete-release cce-check
cd ~/workspace/cce-check-master/release
rm -rf dev_releases/
rm -rf .dev_builds/
rm cce-check-0.1.0.tgz


# create release
cd ~/workspace/cce-check-master/release
bosh create-release --name=cce-check --sha2 --version=0.1.0 --tarball=cce-check-0.1.0.tgz --force
cd ~/workspace/cce-check-master/runtime-config
bosh update-runtime-config -n --name=cce-check ./cce-check-conf.yml
