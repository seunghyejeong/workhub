### cce-check 

---
배포된 각 VM에 CCE 점검 Script를 복사하고, 각 VM마다 접속해서 환경설정을 맞추어 CCE 점검 Script를 실행하던 불편함을 해소하기 위해,
Bosh로 배포되는 VM을 대상으로 "배포와 동시에 CCE 점검 Script 자동 실행" 이 가능하도록 개발 되었습니다.

#### Configuration
```
├── operations
│   ├── bosh
│   │   └── cce-check.yml
│   ├── paasta
│   │   └── cce-check.yml
│   └── service
│       ├── glusterfs
│       ├── lifecycle-service
│       ├── mongodb
│       ├── mysql
│       ├── rabbitmq
│       ├── redis
│       ├── source-control-service
│       └── web-ide
├── release
│   ├── config
│   │   ├── blobs.yml
│   │   └── final.yml
│   ├── create-release.sh
│   ├── jobs
│   │   ├── cce-bosh
│   │   ├── cce-common
│   │   └── cce-service
│   ├── packages
│   └── src
└── runtime-config
    ├── cce-check-conf.yml
    └── update-rc.sh
```

#### apply runtime-config 
```
$ cd runtime-config
$ sh update-rc.sh
```

#### apply cce-check
```
### operations/<DEPLOYMENT_NAME>/cce-check.yml 파일 <DEPLOYMENT_PATH>로 복사
$ cp operations/<DEPLOYMENT_NAME>/cce-check.yml <DEPLOYMENT_PATH>/

### deploy script에 option 추가
$ vi <DEPLOYMENT_PATH>/deploy-<IAAS>.sh
  -o cce-check.yml \

### deploy
$ ./deploy-<IAAS>.sh
```

#### check cce-result
```
### VM 접속
- bosh VM 접속
$ ssh jumpbox@<BOSH_IP> -i jumpbox.key 

- 기타 VM 접속 
e.g.) $ bosh ssh -d paasta database

### CCE 결과 확인
$ cd /var/vcap/cce/
```
