install mysql 

user생성

show databases;
use mysql
select host, user, password from user;
update user set password=1234 where user='root';



window

- 먼저 경로나 컴퓨터 이름이 영어로만 되어있어야함.
- window에 server설치 후 command 창 이용하는게 쉬움


1. mysql server 설치 
   - mysql installer 이용 
   - server만 체크 후 설치 
     ----- 설치 완료 후 진행 --------
2. set "Edit the system environment variables".
3. "System Variables" section and find the "Path" variable. Click on "Edit".
4. click on "New" and add the directory where the MySQL executable is located  
5. C:\Program Files\MySQL\MySQL Server 8.0\bin\
6. computer restart
7. open windowsPowershell
8. `cd C:\Program Files\MySQL\MySQL Server 8.0\bin\`
9. `mysql -u root -p`


useage

- mysql -u root -p ; 1234 ;
- use paasta_edm
- use paasta_edm;
- show databases;
- DROP DATABASE testdb;
- GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
  -flush privileges;


-use mysql
-select host, user from user;


복구;;

1. git bash 이용 log 파일을  sql 파일로 
   jeongbami@jeonbami MINGW64 /c/ProgramData/MySQL/MySQL Server 8.0/Data
   $ mysqlbinlog /c/ProgramData/MySQL/MySQL Server 8.0/Data/JEONBAMI-bin.000001 > JEONBAMI-bin.000001.sql
   mysqlbinlog: File 'C:/ProgramData/MySQL/MySQL' not found (OS errno 2 - No such file or directory)
   ERROR: Could not open log file

DATA경로
C:\ProgramData\MySQL\MySQL Server 8.0\Data

실제 경로
C:\Program Files\MySQL\MySQL Server 8.0\bin

(redirection) >
윈도우에서는 실행이 안됨 

Get-Content JEONBAMI-bin.000001.sql | mysql.exe  -u root -p  1234
cmd /c 'mysql.exe  -u my_service -p  my_service < mysql-dump.sql'

걍 안됨 재설치 하는게 속편함


===================================================== command

MariaDB 또한 Server와 Client로 구성되어 있다. 
select host, user, password from user;
show grants for 'root'@'localhost';


update user set password=password('1234') where user='bami';
set password for 'user'@'%' = password('1234');
set password for 'bami'@'%' = password('tmdgP0425!');

sudo systemctl stop mysql

sudo mysqld_safe --skip-grant-tables &

mysql -u root


revoke all on db_name.table_name FROM 'root'@'localhost';
drop user 'root'@'localhost';

ALTER USER 'root'@'localhost' IDENTIFIED BY 'tmdgP0425!';
	안되면 UPDATE mysql.user SET authentication_string = PASSWORD('tmdgP0425!') WHERE User = 'root' AND Host = 'localhost';

 FLUSH PRIVILEGES;
===================================================== 삭제 및 재설치

sudo apt-get purge mariadb-server
sudo apt autoremove
sudo apt-get purge mysql-common
sudo rm -rf /var/log/mysql
sudo rm -rf /var/log/mysql.*
sudo rm -rf /var/lib/mysql
sudo rm -rf /var/etc/mysql
sudo ln -sf /usr/share/zoneinfo/Asia/Seoul/etc/localtime  //타임존 설정파일 대체
sudo timedatectl set-timezone 'Asia/Seoul' //명령어로 설정

dpkg -l | grep mysql
 sudo apt-get remove --purge {list}


sudo apt-get remove --purge mariadb*
재설치
-- server설치
apt update
apt install mariadb-server


	"E: Could not open lock file /var/lib/dpkg/lock-frontend - open (13: Permission denied)
	E: Unable to acquire the dpkg frontend lock (/var/lib/dpkg/lock-frontend), are you root?
	" 
	 sudo rm /var/lib/apt/lists/lock
	 sudo rm /var/cache/apt/archives/lock
	 sudo rm /var/lib/dpkg/lock*

-- client 설치 

sudo apt-get install mariadb-client
sudo mysql_secure_installation

--- 외부 접속 허용
vim /etc/mysql/mariadb.conf.d/50-server.cnf
bind-address 주석처리
sudo systemctl stop mariadb
sudo systemctl start mariadb

---

issue1
 mariadb.service - MariaDB 10.3.38 database server
     Loaded: loaded (/lib/systemd/system/mariadb.service; enabled; vendor preset: enabled)
     Active: failed (Result: exit-code) since Thu 2023-03-16 11:06:25 KST; 45s ago
       Docs: man:mysqld(8)
             https://mariadb.com/kb/en/library/systemd/
    Process: 180282 ExecStartPre=/usr/bin/install -m 755 -o mysql -g root -d /var/run/mysqld (code=exited, status=0/SUCCESS)
    Process: 180290 ExecStartPre=/bin/sh -c systemctl unset-environment _WSREP_START_POSITION (code=exited, status=0/SUCCESS)
    Process: 180304 ExecStartPre=/bin/sh -c [ ! -e /usr/bin/galera_recovery ] && VAR= ||   VAR=`cd /usr/bin/..; /usr/bin/galera_recovery`; [ $? -eq 0 ]   && systemctl set-envi>
    Process: 180312 ExecStart=/usr/sbin/mysqld $MYSQLD_OPTS $_WSREP_NEW_CLUSTER $_WSREP_START_POSITION (code=exited, status=0/SUCCESS)
    Process: 180343 ExecStartPost=/bin/sh -c systemctl unset-environment _WSREP_START_POSITION (code=exited, status=0/SUCCESS)
    Process: 180345 ExecStartPost=/etc/mysql/debian-start (code=exited, status=203/EXEC)
   Main PID: 180312 (code=exited, status=0/SUCCESS)
     Status: "MariaDB server is down"


ls /etc/mysql/debian-start :: 존재하지 않았음

sudo apt-get remove --purge mariadb* 재설치 
sudo apt-get install mariadb-server

ls /etc/mysql/debian-start 존재함
sudo chmod +x /etc/mysql/debian-start
sudo systemctl restart mariadb.service
systemctl status mariadb.service

 mariadb.service - MariaDB 10.3.38 database server
     Loaded: loaded (/lib/systemd/system/mariadb.service; enabled; vendor preset: enabled)
     Active: active (running) since Thu 2023-03-16 11:09:47 KST; 1min 26s ago
       Docs: man:mysqld(8)
             https://mariadb.com/kb/en/library/systemd/
    Process: 187008 ExecStartPre=/usr/bin/install -m 755 -o mysql -g root -d /var/run/mysqld (code=exited, status=0/SUCCESS)
    Process: 187009 ExecStartPre=/bin/sh -c systemctl unset-environment _WSREP_START_POSITION (code=exited, status=0/SUCCESS)
    Process: 187011 ExecStartPre=/bin/sh -c [ ! -e /usr/bin/galera_recovery ] && VAR= ||   VAR=`cd /usr/bin/..; /usr/bin/galera_recovery`; [ $? -eq 0 ]   && systemctl set-envi>
    Process: 187156 ExecStartPost=/bin/sh -c systemctl unset-environment _WSREP_START_POSITION (code=exited, status=0/SUCCESS)
    Process: 187163 ExecStartPost=/etc/mysql/debian-start (code=exited, status=0/SUCCESS)
   Main PID: 187058 (mysqld)
     Status: "Taking your SQL requests now..."
      Tasks: 31 (limit: 9507)
     Memory: 65.1M
     CGroup: /system.slice/mariadb.service
             └─187058 /usr/sbin/mysqld


---

select host, user, password from user;
sudo mariadb -u root -p
CREATE USER 'bami'@'localhost' IDENTIFIED BY 'tmdgP0425!';
GRANT ALL PRIVILEGES ON *.* TO 'bami'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
mysql -u newuser -p


---

[mariadb dump]

mariadb -u root -p 

show databases;

<dump 뜰 파일 데이터베이스 선택>

확인 후 exit

sudo mysqldump -u root -p ${database_name} > ${dumpFilename}.sql

sudo mysqldump -u root -p mysql > mysql.sql

30 8 * * 1

create user 'edmadmin'@'localhost' identified by 'PaaS-TA@admin';

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

ps -ef | grep mysqld
mysql      13548       1 34 04:36 ?        00:00:01 /usr/sbin/mysqld
root       13590    8423  0 04:36 pts/1    00:00:00 grep --color=auto mysqld



ip addr show
     inet 10.0.30.130/24 brd 10.0.30.255 scope global dynamic ens3
      valid_lft 27254sec preferred_lft 27254sec

vim /etc/mysql/mysql.conf.d/mysqld.cnf
bind-address: 10.0.30.130
systemctl restart mysql
systemctl status mysql.service

