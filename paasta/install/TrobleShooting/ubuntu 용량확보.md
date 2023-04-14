
# 용량 확보


1. 파티션 용량 체크
    df -h      
2. 경로 용량 체크
    df -i   
3. 경로에 있는 파일로 용량 체크하는 명령어
     for i in "경로/*"; do echo $i; find $i | wc -l; done

4. 용량을 차지하고 있는 루트를 지정하여 하나씩 체크

    sudo du -hsx /* | sort -rh | head -n 40
    sudo du -hsx /home/* | sort -rh | head -n 35

5. 용량이 많은 파일을 nul 파티션에 밀어버리기
    cat /dev/null > post-start.stderr.log

> 로그를 함부러 지우면 앱 실행에 문제가 될 수 있으므로 지우면 안됨.