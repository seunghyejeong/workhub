
# 보안점검 zip파일 한번에 해제하기

```shell
	for i in *.zip;
	do 
		unzip $i -d /path/to/put;
	done
```

# 파일명와 같은 파일 폴더를 만들며 unzip 
- 보안점검때는 vm 번호만 출력되므로 구분 필요

```shell
	for file in `ls *.zip`;
	do
	       unzip "${file}" -d "${file:0:-4}";
	done
```

# SCP
- inception -> vm
    > bosh -e micro-bosh -d paasta  scp ./cce-scripts.zip api/0887902e-70cd-44f9-a2fe-4f631d02b453:/tmp/
- vm -> inception
    > bosh -e micro-bosh -d paasta scp diego-api/7cd8ac48-5903-44d8-ac92-4bc739889447:/tmp/scripts/*.txt ~/workspace/user/bami/linuxinfo/

# service 배포 정리 
1. 최소 배포로 진행한다 
    mongdb, pipeline 제외 
    instance 갯수 : 1
2. 내부 ip를 사용한다
    haproxy_public_network_name vip -> default
	public_networks_name을 vip→ default로 바꾼다.
3. 내부 ip사용시 cloud-config
    해당 zone의 static config는 범위만큼 내가 사용하겠다는 의미임
    그래서 사용되지 않은 영역대가 있으면 그 영역대로 좁힌 ip영역대를 이용한다.
4.  service 목록
    - redis
    - rabbitmq
    - pipeline-service
        Task 1791 | 01:58:16 | 
        Deprecation: Global 'properties' are deprecated. Please define 'properties' at the job level.
        Task 1791 | 01:58:17 | 
        Error: 
        Instance group 'ci_server' has 1 instances but was allocated 2 static IPs in network 'default'
        :: ci server는 instance 2개 
    - pinpoint
    - mysql
    - mongodb
        Evaluating manifest: Error 'operation [3] in operations/cce-check.yml failed': Expected to find exactly one matching array item for path '/instance_groups/name=mongodb_slave3'  but found 0
        :: mongdb는 cce-check.yml파일이 변경되어야함.
        (default instance가 3개인데 2개로 바뀜 slaves3/master3 삭제 후 배포해야함 )
    - logging-service
        Error: Link 'portal-registration' in job 'paas-ta-portal-log-api' from instance group 'log-api' consumes from deployment 'portal-api', but the deployment does not exist 
	    :: portal api service 필요함.
        influxdb d-02번은 수동점검 필요 
    - lifecycle-service
    - glusterfs
    - gateway-service

# 수동점검시
- username이나 pw / port num은 해당 vm의 vars.yml에서 확인한다 