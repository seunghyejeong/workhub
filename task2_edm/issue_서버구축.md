공모전 서버 구축

[Server]

- floating IP 
- Tomcat 설치
- home 경로에 파일은 project 파일
  -mysql client 설치
  mysql -h 10.0.30.130 -u edmadmin -p

[DB]
-DB 설치


create user 'edmadmin'@'localhost' identified by 'master77!!';
create user 'edmadmin'@'%' identified by 'master77!!';
create database paasta_edm default character set utf8;


grant all privileges on *.* to 'edmadmin'@'localhost' identified by 'master77!!';
grant all privileges on *.* to 'edmadmin'@'%' identified by 'master77!!';
flush privileges;

curl -l 45.248.73.44:8080


dump-paasta_edm-202302091650.sql

---

issue

curl: (7) Failed to connect to 45.248.73.44 port 8080: No route to host
    : 방화벽 문제 
        systemctl stop firewalld

netstat -nltp | grep 3306
tcp        0      0 127.0.0.1:33060         0.0.0.0:*               LISTEN      2740/mysqld         
tcp        0      0 127.0.0.1:3306          0.0.0.0:*               LISTEN      2740/mysqld 

    : bind port가 모두에게 열린 상태가 아님
    
        /etc/mysql/mysql.conf.d/mysqld.conf
        : bindaddress : 0.0.0.0 수정

tcp        0      0 127.0.0.1:33060         0.0.0.0:*               
LISTEN      4393/mysqld         
tcp        0      0 0.0.0.0:3306            0.0.0.0:*               LISTEN      4393/mysqld    


iptables -A INPUT -p tcp --dport 8080 -j ACCEPT
firewall-cmd --zone=public --add-port=8080/tcp --permanent
firewall-cmd --reload


---

issue
i push command sudo systemctl restart 


$ systemctl restart mysql
mysql return error.
and 
$systemctl status mysql.service 
return
● mysql.service - MySQL Community Server
     Loaded: loaded (/lib/systemd/system/mysql.service; enabled; vendor preset: enabled)
     Active: activating (start) since Fri 2023-03-24 04:32:58 UTC; 512ms ago
    Process: 9858 ExecStartPre=/usr/share/mysql/mysql-systemd-start pre (code=exited, status=0/SUCCESS)
   Main PID: 9866 (mysqld)
     Status: "Server startup in progress"
      Tasks: 13 (limit: 4677)
     Memory: 305.7M
     CGroup: /system.slice/mysql.service
             └─9866 /usr/sbin/mysqld

sudo tail -n 50 /var/log/mysql/error.log
    Can't start server: Bind on TCP/IP port: Cannot assign requested address
    Do you already have another mysqld server running on port: 3306 ?

--bind-address 다시 잡고싶을떄-
ps -ef | grep mysqld
mysql      13548       1 34 04:36 ?        00:00:01 /usr/sbin/mysqld
root       13590    8423  0 04:36 pts/1    00:00:00 grep --color=auto mysqld

kill pid
	sudo kill -9 12615

sudo tail -n 50 /var/log/mysql/error.log
	another error Can't start server: Bind on TCP/IP port: Cannot assign requested address

ip addr show
     inet 10.0.30.130/24 brd 10.0.30.255 scope global dynamic ens3
      valid_lft 27254sec preferred_lft 27254sec

vim /etc/mysql/mysql.conf.d/mysqld.cnf
bind-address: 10.0.30.130
systemctl restart mysql
systemctl status mysql.service



Q: i checked mysql -h <database-vm-ip-address> -u <username> -p command and it wordks on serverVM , and receive database server-ip and open port on databaseVM. but  it didn't work on Web site yet... TT ...  what extra thing cheked I am ?


A : root@paasta-edm-db:/var/run/mysqld# sudo ufw status
Status: inactive
root@paasta-edm-db:/var/run/mysqld# sudo ufw allow 3306/tcp
Rules updated
Rules updated (v6)


Q :is that variation you said "You should update the URL in your website to use the private IP address of the database VM instead of the public IP address of the server VM." :: String dbURL = "jdbc:mysql://10.0.30.130:3306/paasta_edm?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf8";
10.0.30.130 is serverVM's privateIP.

A : To check this, you can log in to the MySQL server on the database VM and run the following command:

sql
Copy code
SELECT user, host FROM mysql.user WHERE user = '<username>'; "idid"

A : 
GRANT ALL PRIVILEGES ON <database>.* TO '<username>'@'<web-server-ip>' IDENTIFIED BY '<password>';

GRANT ALL PRIVILEGES ON paasta_edm.* TO 'edmadmin'@'10.0.30.32';

GRANT ALL PRIVILEGES ON paasta_edm TO 'edmadmin'@'localhost' IDENTIFIED BY '<password>';

GRANT CREATE USER ON *.* TO 'edmadmin'@'localhost';

ALTER USER 'edmadmin'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'new_password';

create user 'edmadmin'@'localhost' IDENTIFIED WITH caching_sha2_password identified by 'master77!!';


GRANT create user edmadmin@localhost identified by 'master77!!';

source sql source 하기
login mysql
chwon mysql:mysql
name paasta_edm.sql;
source /var/lib/mysql/paasta_edm.sql

의존성 파일을 WEB=INF에 넣어야함.
그런데 ip주소가 바뀌었으므로 compile을 다시 해야한다고 ..
java 설치 후 compile 한 후 WEB-INF > classes에 다시 넣어준다.

javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DTO NOTICE.java
javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DTO QNA.java\


javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DAO NOTICE.java
javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DAO QNA.java


mysql -h 10.0.30.130 -u edmadmin -p


--- 결국 해주어야 했던 것 -> javac로 클래스 파일 변환

web에서 읽는 것은 *.class 이다
.java를 수정했으면 class로 컴파일이 필요하고 
해당 파일이 있는 곳은 ~/paasta_edm/WEB-INF/classes/DTO 와 ~/paasta_edm/web/WEB-INF/classes/DTO 이다




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