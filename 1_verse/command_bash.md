1. 파일 값 찾기
   $ sha1sum "파일명"

2. command 문자열 치환
   :%s/foo/bar

3. 현재 파일 경로
   pwd

4. 이전의 파일 경로로 돌아가기
   cd -

5. stemcell

6. 현재 ip주소 보기
   curl ifconfig.me

7. unzip 파일경로
   unzip [압축 파일명.zip] -d [압축 풀 경로]

8. 파일명과 같은 파일폴더를 만들며 해당폴더에 unzip

   ```shell
   for file in `ls *.zip`;
   do
          unzip "${file}" -d "${file:0:-4}";
   done
   ```

9. 여러개 zip폴더 압출 해제하기  

   ```shell
   for i in *.zip;
   do 
   	unzip $i -d /path/to/put;
   done
   ```

10. 파티션 밀기 
    cat /dev/null > post-start.stderr.log

11. deployment restart stop .. 
    bosh -d mongodb cck

12. 명령어 한번에 실행하기 
    "&&"활용
    cd /tmp && unzip cce-scripts && cd scripts && source linux_info.sh 

13. 용량 확인 및 확보하기
    df -h
    df -i

    <파일 검색>
    for i in "경로/*"; do echo $i; find $i | wc -l; done

 	<경로에 있는 파일 검색> 
 	 sudo du -hsx /* | sort -rh | head -n 40
 	 sudo du -hsx /home/* | sort -rh | head -n 35
 	
 	<파티션밀기>
 	 cat /dev/null > post-start.stderr.log



 14.smile server 

 /etc/resolv.conf

# This file is managed by man:systemd-resolved(8). Do not edit.

#

# This is a dynamic resolv.conf file for connecting local clients to the

# internal DNS stub resolver of systemd-resolved. This file lists all

# configured search domains.

#

# Run "systemd-resolve --status" to see details about the uplink DNS servers

# currently in use.

#

# Third party programs must not access this file directly, but only through the

# symlink at /etc/resolv.conf. To manage man:resolv.conf(5) in a different way,

# replace this symlink by a static file or a different symlink.

#

# See man:systemd-resolved.service(8) for details about the supported modes of

# operation for /etc/resolv.conf.

nameserver 127.0.0.53
options edns0
search openstacklocal

nameserver 8.8.8.8
nameserver 8.8.4.4


15. se nu
    yml 파일 들여쓰기 보는 방법


16. smile server apt-get install 오류
    vi /etc/network/interfaces 를 열고
    	dns-nameservers 8.8.8.8 8.8.4.4 를 추가.
    vi /etc/resolv.conf 를 열고
    	nameserver 8.8.8.8
    	nameserver 8.8.4.4 