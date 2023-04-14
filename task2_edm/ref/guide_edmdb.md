# 파스-타 공모전 포털 가이드

## 깃허브 링크
- https://github.com/PaaS-TA/psta-contest-portal.git

## DB VM 
- mysql 설치
```
$ sudo apt update 
$ sudo apt install mysql-server

$ mysql --version 
mysql  Ver 14.14 Distrib 5.7.39, for Linux (x86_64) using  EditLine wrapper
```

- mysql 계정 생성 및 Database 생성
```
$ sudo mysql -u root -p
Enter password: [엔터 ]

mysql> create user edmadmin@localhost identified by 'master77!!';
Query OK, 0 rows affected (0.02 sec)

mysql> create user 'edmadmin'@'%' identified by 'master77!!';
Query OK, 0 rows affected (0.00 sec)

mysql> create database paasta_edm default character set utf8;
Query OK, 1 row affected (0.00 sec)
```

- 사용자 권한 부여
```
mysql> grant all privileges on *.* to edmadmin@localhost identified by 'master77!!';
Query OK, 0 rows affected, 1 warning (0.00 sec)

mysql> grant all privileges on *.* to 'edmadmin'@'%' identified by 'master77!!';
Query OK, 0 rows affected, 1 warning (0.00 sec)

mysql> flush privileges;
```

- mysql 외부접속 허용
```
$ sudo vi /etc/mysql/mysql.conf.d/mysqld.cnf
[mysqld]
#
# * Basic Settings
#
user            = mysql
pid-file        = /var/run/mysqld/mysqld.pid
socket          = /var/run/mysqld/mysqld.sock
port            = 3306
basedir         = /usr
datadir         = /var/lib/mysql
tmpdir          = /tmp
lc-messages-dir = /usr/share/mysql
skip-external-locking
#
# Instead of skip-networking the default is now to listen only on
# localhost which is more compatible and is not less secure.

=======================================================================
#bind-address           = 127.0.0.1  #주석 처리
=======================================================================

$ sudo systemctl restart mysql 
```

## WAS VM
- 코드 디렉터리 생성
```
$ mkdir ~/workspace
$ cd ~/workspace
$ git clone https://github.com/PaaS-TA/psta-contest-portal.git
```

- tomcat 설치
```
$ sudo apt update 
$ sudo apt install tomcat8
```

- tomcat 기본 경로 추가
```
$ sudo vim /etc/tomcat8/server.xml

<Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->
        ## 해당 내용 추가
        ###########################################################
        <Context path="" docBase="/home/ubuntu/workspace/psta-contest-portal/web/" debug="0" reloadable="true" crossContext="true"> </Context>
        ###########################################################
        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html
             Note: The pattern used is equivalent to using pattern="common" -->
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />

```
- 의존성 파일 저장
    + 파일 경로 :: \\192.168.0.24\PaaS-TA\파트폴더\기술지원팀\03.포털\03.공모전 포털\04. 컴파일 관련 파일
```
## 라이브러리 관련 파일
$ cd ~/workspace/psta-contest-portal/web/WEB-INF/lib
$ unzip lib.zip

## DAO, DTO 컴파일된 파일
$ cd ~/workspace/psta-contest-portal/web/WEB-INF/classes
$ unzip classes.zip
```

- tomcat 재시작
```
$ sudo systemctl restart tomcat8
```
- 공모전 포털 접속 확인 :: {vm IP}:8080

- 공모전 포털 80포트로 적용
  + tomcat 보안상 1024이하의 포트 사용불가
  + iptable을 이용해 80포트로 접근 시 80으로 redirect
  + port없이 vm IP로 확인 (default port: 80)
```
$ sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
$ sudo iptables -t nat -L

Chain PREROUTING (policy ACCEPT)
target     prot opt source               destination         
REDIRECT   tcp  --  anywhere             anywhere             tcp dpt:http redir ports 8080

Chain INPUT (policy ACCEPT)
target     prot opt source               destination         

Chain OUTPUT (policy ACCEPT)
target     prot opt source               destination         

Chain POSTROUTING (policy ACCEPT)
target     prot opt source               destination   
```
