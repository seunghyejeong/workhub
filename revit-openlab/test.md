---
## 접속정보
ip:
175.45.209.219

user:
ncloud

pw:
D8T+L6BJ9N?d

접속키: gju
https://www.gov-ncloud.com/nsa/gju

root 계정 정보
chim@gwangju.ac.kr / rhkdwn1234%(광주1234%)

## vm 정보
server name: pub-vm1
ip: 175.45.209.219
password: D8T+L6BJ9N?d
username: ncloud

API access key ID: JswkqxqkfzrTiLP7e4qn
Secret Key: h5FFjQtlmmgXgUXl70AW3a5tp9PpV1RRbKG16Y8G

## DB정보
DB server name: paasta-admin-db
DB username: paasta2023
DB password: paasta2023!

ncp-paasta-admin-key.pem

---

## test

1. cf login
$ cf login -a api.sys.openlab-01.kr --sso

API endpoint: api.sys.openlab-01.kr

Temporary Authentication Code ( Get one at https://uaa.sys.openlab-01.kr/passcode ):

: 해당 URL 접속 후 "Ncloud SSO" link 클릭 
: passcode 복사 및 붙여넣기 진행
: user / org / space  확인 

2. role 지정
$ cf set-space-role 45329a60-d510-11ec-88d1-005056a7aca7 system system-space SpaceDeveloper

3. 정보 확인
$ cf space-users system system-space

---


1. ORG 생성
$ cf create-org ORG

example:
```bash
$ cf create org test
Creating org help as 45329a60-d510-11ec-88d1-005056a7aca7...
OK
``` 

2. SPACE 생성
$ cf create-space SPACE


example:
```bash
$ cf create-space test
Creating space helper in org system as 45329a60-d510-11ec-88d1-005056a7aca7...
OK
```

3. 생성 확인
```bash
$ cf orgs
$ cf spaces

4. scale 
$ cf scale test-node-app -i NUM

example:
```bash
$ cf scale test-node-app -i 2
Scaling app test-node-app in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...

Instances starting...

Showing current scale of app test-node-app in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...

name:                test-node-app
requested state:     started
isolation segment:   placeholder
routes:              test-node-app.apps.sys.openlab-01.kr
last uploaded:       Wed 05 Apr 16:19:52 KST 2023
stack:
buildpacks:
isolation segment:   placeholder

type:           web
sidecars:
instances:      1/2
memory usage:   1024M
     state      since                  cpu    memory       disk        logging      details
#0   running    2023-04-05T07:20:32Z   0.0%   12.1M of 0   140K of 0   0/s of 0/s
#1   starting   2023-04-05T07:43:30Z   0.0%   0 of 0       0 of 0      0/s of 0/s

ncloud@pub-vm1:~$ cf apps
Getting apps in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...

name                 requested state   processes   routes
ncp-service-broker   started           web:1/1     ncp-service-broker.apps.sys.openlab-01.kr
test-node-app        started           web:2/2     test-node-app.apps.sys.openlab-01.kr

```


4. sample app 배포
sample app : spring-music


- java 설치
$ sudo apt update
$ sudo apt install openjdk-8-jdk

- spring-music 설치
$  git clone https://github.com/cloudfoundry-samples/spring-music
$ cd spring-music
$ ./gradlew clean assemble
$ cf push

```bash
.
.
.
Waiting for app spring-music to start...

Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...
Instances starting...

name:                spring-music
requested state:     started
isolation segment:   placeholder
routes:              spring-music-excellent-sable-pv.apps.sys.openlab-01.kr
last uploaded:       Wed 05 Apr 17:44:28 KST 2023
stack:
buildpacks:
isolation segment:   placeholder
```

: "requested state" 값 확인

- 설치 app 확인
```bash
$ cf apps
Getting apps in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...

name                 requested state   processes                               routes
spring-music         started           web:1/1, executable-jar:0/0, task:0/0   spring-music-excellent-sable-pv.apps.sys.openlab-01.kr
```

4. app log 확인

$ cf logs spring-music 

- route에 출력된 domain을 web-browser 이용해 접속 
- delete albume, edit, add 등등 웹 기능 동작 확인
- 기능이 작동 될 때마다 console의 로그가 잘 올라오면 정상 작동

5. domain 생성

$ cf create-private-domain ORG DOMAIN

example:
```bash
$ cf create-private-domain system openlab-01.kr
Creating domain openlab-01.kr for org system as 45329a60-d510-11ec-88d1-005056a7aca7...
OK
```

- 확인

```bash
$ cf domains
Getting domains in org system as 45329a60-d510-11ec-88d1-005056a7aca7...

name                     availability   internal   protocols
test.com                 private                   http
```

6. domain map-route (sample App과 연결)

cf map-route APPNAME DOMAIN --hostname HOSTNAME

example:
```bash
$ cf map-route test-node-app openlab-01.kr --hostname user
Creating route user openlab-01.kr for org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...
OK

Mapping route user.openlab-01.kr to app test-node-app in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...
OK
```

- 확인
$ cf apps
: routes에 생성한 domain이 추가되어 있으면 정상
