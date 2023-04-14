 Rapid7 insightVM 설치

## 1. 설치 파일 및 체크섬 파일 다운로드 

- 다운로드 경로 :: https://docs.rapid7.com/insightvm/download

  ```shell
  $ wget https://download2.rapid7.com/download/InsightVM/Rapid7Setup-Linux64.bin
  $ wget https://download2.rapid7.com/download/InsightVM/Rapid7Setup-Linux64.bin.sha512sum
  ```

<br />

## 2. 설치 시 루트 권한으로 진행

```shell
$ sudo su
```

<br />

## 3. 파일 유효성 검사

- OK 메시지 반환 확인
- 확인 실패 시 설치 프로그램 다시 다운로드 및 시도

```
# sha512sum -c Rapid7Setup-Linux64.bin.sha512sum 
Rapid7Setup-Linux64.bin: OK
```

<br />

## 4. 설치 프로그램 권한 수정

```
# chmod +x Rapid7Setup-Linux64.bin
```

<br />

## 5. 설치 프로그램 실행

```
# ./Rapid7Setup-Linux64.bin -c
Unpacking JRE ...
Starting Installer ...
```

- 설치 시 입력 값 참고

  ```shell
  ********************************************************************************
  Welcome to the Rapid7 Installation Wizard
  ********************************************************************************
  
  Rapid7 Vulnerability Management reduces your organization's risk by
  dynamically collecting data and analyzing risk across vulnerabilities,
  configurations and controls from the endpoint to the cloud. Our
  vulnerability management platform is engineered to enable IT security teams
  to identify, assess and respond to critical change as it happens.
  
  Rapid7 Inc
  http://www.rapid7.com
  info@rapid7.com
  +1.866.7RAPID7 (Toll Free)
  +1.617.247.1717
  
  Do you want to continue?
  Yes [y, Enter], No [n]
  y
  
  Gathering system information....
  
  Security Console with local Scan Engine
  If you do not have a console installed yet, this option is recommended. The console manages scan engines and all administrative operations.
  
  Scan Engine only
  This distributed engine can start scanning after being paired with a Security Console.
  
  Select only the set of components you want to install:
  Security Console with local Scan Engine [1, Enter]
  Scan Engine only [2]
  1
  
  Where should Rapid7 Vulnerability Management be installed?
  [/opt/rapid7/nexpose]
  
  
  
  ********************************************************************************
  The installer is comparing your system settings to required settings
  ********************************************************************************
  
  Installation requirements
  [Pass] - 15,720 MB RAM was detected.
      See the list of supported versions.
      http://www.rapid7.com/products/nexpose/system-requirements
  
  [Pass] - SELinux is not active.
  [Pass] - Software is not running.
  Ports and connectivity
  Not checked.
  [Pass] - Port 3780 is available.
  [Warn] - The update server could not be accessed.
  
  Minimum requirements met. Select "Yes" to continue, "No" to cancel installation.
  Yes [y, Enter], No [n]
  y
  Database port
  Enter the number for the port that the database will listen on:
  [5432]
  
  
  The port number is valid.
  
  
  ********************************************************************************
  User Details: This information will be used for generating SSL certificates, and it will be included in requests to Technical Support. Only alphanumeric characters and spaces are allowed in the name fields.
  ********************************************************************************
  
  First name:
  []
  seokjun
  Last name:
  []
  choi
  Company:
  []
  innogrid
  
  
  ********************************************************************************
  Credentials: Choose secure credentials and remember them. You will need them to perform configuration steps after completing the installation.
  ********************************************************************************
  
  Credentials: Choose secure credentials and remember them. You will need them
  to perform configuration steps after completing the installation.
  User name:
  []
  paasta
  
  Password:
  
  
  Confirm the password:
  
  
  Require password reset upon login?
  Yes [y], No [n, Enter]
  n
  Password match confirmed.
  
  
  ********************************************************************************
  Confirm or change your installation selections
  ********************************************************************************
  
  
  
  ********************************************************************************
  Additional Tasks Selection
  ********************************************************************************
  
  You have selected the following installation location:
  /opt/rapid7/nexpose
  
  You have selected the following component(s) to install:
  Security Console, Scan Engine
  
  You have entered the following contact information:
  seokjun choi,  innogrid
  
  You have created the following user name:
  paasta
  
  Select any additional installation tasks.
  Initialize and start after installation?
  Yes [y], No [n, Enter]
  y
  
  
  
  ********************************************************************************
  Extracting files...
  ********************************************************************************
  
  Extracting files...
                                                                             
  
  
  ********************************************************************************
  Installation is complete!
  ********************************************************************************
  
  Installation is complete!
  
  If you chose to start the Security Console as part of the installation, then it will be started upon installer completion.
  
  Using the credentials you created during installation, log onto Nexpose at https://localhost:3780.
  
  
  To start the service run: sudo systemctl start nexposeconsole.service
  
  
  To start the service run: sudo systemctl start nexposeconsole.service
  The Security Console is configured to automatically run at startup. See the
  installation guide if you wish to modify start modes.
  
  [Enter]
  
  Finishing installation...
  ```

<br />

## 6. 로그인 및 활성화

- https://<console_address>:3780 접속
  + 초기화 완료될 때까지 10~30분 소요
  + 계정 정보
    - username :: paasta
    - password :: PaaS-TA@2023
  + 제품키 입력 필요 
    - 무료 평가판 신청 :: https://www.rapid7.com/products/insightvm/
    - 인증키 :: 회사 메일에서 확인

<br />

## 7. 설정 변경

- 한글설정 :: 우측 상단 username 클릭 > User Preferences > user Configuration > GENERAL
  + E-mail address 입력
  + Display user interface in :: Korean (South Korea)
  + Run the report :: Korean (South Korea)
- 버전 확인 :: 좌측 관리 탭(점 세 개 목록 아이콘) > 글로벌 및 콘솔 설정의 콘솔 부분의 관리 > 패널에서 > 일반 > 버전 정보

<br />

## 8. 삭제

- rapid7 실행 확인

  ```
  # ps -ef | grep rapid
  root      1157     1  0 Dec08 ?        00:00:01 SCREEN -d -m -S nexposeconsole /opt/rapid7/nexpose/nsc/nsc.sh
  root      1165  1157  0 Dec08 pts/0    00:00:00 /bin/sh /opt/rapid7/nexpose/nsc/nsc.sh
  root      4263   453  0 06:35 pts/1    00:00:00 grep --color=auto rapid
  root      9761  1165  1 Dec17 pts/0    00:50:45 ./.DLLCACHE/nexserv -className=com/rapid7/nexpose/nsc/NSC
  
  # netstat -alnp | grep LIST
  sudo netstat -alnp |grep 3780
  tcp6       0      0 :::3780                 :::*                    LISTEN      9761/./.DLLCACHE/ne 
  ```

- rapid7 종료

  ```shell
  $ kill {process 번호}
  ```

- 삭제 스크립트 실행

  ```
  # /opt/rapid7/nexpose/.install4j/uninstall -c
  
  WARNING!
  Uninstalling will completely remove all Nexpose components and delete sites,
  configurations, reports, and any scan data on assets, nodes, and
  vulnerabilites discovered.
  Make sure that all data is backed up before proceeding.
  Uninstall cannot be cancelled once started, only continue to the next screen
  if you are certain that Nexpose data is backed up or no longer required.
  
  Do you want to continue?
  Yes [y], No [n, Enter]
  y
  
  
  ********************************************************************************
  (Nexpose Uninstall
  ********************************************************************************
  
  Uninstalling Rapid7 Vulnerability Management 6.6.114...
  Nexpose was successfully removed from your computer.
  Finishing uninstallation...
  ```

  - 삭제 스크립트 실행 오류

    + 이슈 :: INSTALL4J_JAVA_HOME을 찾을 수 없음

      ```shell
      No suitable Java Virtual Machine could be found on your system.
      The version of the JVM must be at least 1.8.
      Please define INSTALL4J_JAVA_HOME to point to a suitable JVM.
      ```

    + 해결방안(택1)

      1. INSTALL4J_JAVA_HOME 경로 지정

         ```
         # export INSTALL4J_JAVA_HOME=/home/ubuntu/workspace/joy/rapid7/Rapid7Setup-Linux64.bin.2794.dir/jre
         
         # /opt/rapid7/nexpose/.install4j/uninstall -c
         ```

      2. jdk 설치

         ```
         # apt install openjdk-8-jdk
         
         # /opt/rapid7/nexpose/.install4j/uninstall -c
         ```