### Server VM
- was 설치 
- web resource 구성
    javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DTO NOTICE.java
    javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DTO QNA.java\


    javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DAO NOTICE.java
    javac -cp /home/ubuntu/paasta_edm/src:/home/ubuntu/paasta_edm/src/DAO QNA.java
- 공인 ip 할당
- mysql-client 설치
    mysql -h 10.0.30.130 -u edmadmin -p
- server.xml
port 80 / redirect 443 



### DB VM
- db설치
- insert db file
- private ip 사용
- 외부 접속 허용
- donot iptables chain

 sudo iptables -t nat -F PREROUTING 