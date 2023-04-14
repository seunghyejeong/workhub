test 환경 검증

k get pods -A
	cert-manager,cp-portal,harbor,ingress,keycloak,knative,kube-system,kubeflow
	vault 
	: paasta cp 환경

	mariadb, allsplus-5fdd98b4dd-2snx7만 pod로 배포 된 것


 k get all -n default -o wide 
 	NAME                         			IP     			NODE     
 	pod/allsplus-5fdd98b4dd-2snx7          10.233.106.155   master01

 	NAME                    TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)
 	service/allsp-service   LoadBalancer   10.233.47.171   <pending>     8080:32488/TCP

 	: 
 	1. 
 	CSP를 통해서 했으면 external-ip가 부여 됐을텐데 pending인건 openstack과 같은 자체 구축 클라우드를 이용했을 가능성이 높다. (동적할당 불가,nodeport로 사용)
 	2. 
 	containter port는 8080으로 선언되어 있고 nodeport가 32488로 Random 부여받았다.

ifconfig
	공인ip:28080으로 띄워진 webapp은 :28080으로 port-fowarding 되어 있는것이며 이는 
	28080으로 들어오는 모든 수신을 32488로 응답한다.

	:ifconfig를 통해 해당 master vm의 interface 이름 확인 
	ens33: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.16.50.11  netmask 255.255.0.0  broadcast 172.16.255.255
        inet6 fe80::20c:29ff:fea0:7eb4  prefixlen 64  scopeid 0x20<link>
        ether 00:0c:29:a0:7e:b4  txqueuelen 1000  (Ethernet)
        RX packets 149888161  bytes 71855187719 (71.8 GB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 159434880  bytes 101982267581 (101.9 GB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

tcpdump -i ens33 tcp port {port}
	패킷덤프를 이용해 로그 확인
	{port}로 통신하는 패킷을 보여줌
	: sudo tcpdump -i ens33 tcp port 332488




---
http://220.118.0.108:28080/의 port "28080" 대해 조사한 결과 업체 내부적으로 port-forwarding이 설정된 것으로 추측되어
service로 띄워진 allsp-service는 nodePort "32488"로 연결되어 있음을 확인하고 `tcpdump`를 이용해 로그를 출력해 보았습니다. web이 동작할 떄마다 log가 출력됩니다.


---
$k get all -n default -o wide
NAME                    TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE   SELECTOR
service/allsp-service   LoadBalancer   10.233.47.171   <pending>     8080:32488/TCP   12d   app=allsplus

$sudo tcpdump -i ens33 tcp port 32488
listening on ens33, link-type EN10MB (Ethernet), capture size 262144 bytes
06:41:49.905383 IP 219.240.83.118.59659 > master01.cluster.local.32488: Flags [S], seq 919430147, win 64240, options [mss 1460], length 0
06:41:49.905470 IP master01.cluster.local.32488 > 219.240.83.118.59659: Flags [S.], seq 3889258316, ack 919430148, win 65330, options [mss 1390], length 0
06:41:49.905507 IP 219.240.83.118.59659 > master01.cluster.local.32488: Flags [.], ack 1, win 64240, length 0
06:41:49.905559 IP 219.240.83.118.59659 > master01.cluster.local.32488: Flags [P.], seq 1:542, ack 1, win 64240, length 541
06:41:49.905599 IP master01.cluster.local.32488 > 219.240.83.118.59659: Flags [.], ack 542, win 64789, length 0
.
.
.




