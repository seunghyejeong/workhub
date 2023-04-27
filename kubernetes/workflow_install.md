Kubernets install


==========================================================================================================[minikube]

이전에 Docker kuberctl 설치하기

install

curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

alias kubectl="minikube kubectl --"


	issue	
	
	1) unable conf 어쩌고저쩌고
		minikube start --driver=docker
		minikube config set driver docker
	2) 
	E0228 07:09:45.921494    8914 memcache.go:238] couldn't get current server API group list: Get "http://localhost:8080/api?timeout=32s": dial tcp 127.0.0.1:8080: connect: connection refused
	The connection to the server localhost:8080 was refused - did you specify the right host or port?
	
	docker 설치 후 /var/run/docker.sock의 permission denied 
		sudo chmod 666 /var/run/docker.sock	
		sudo chown root:docker /var/run/docker.sock

 minikube start
 	Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default

 kubectl get po -A
 	NAMESPACE     NAME                               READY   STATUS    RESTARTS      AGE
	kube-system   coredns-787d4945fb-hwwhq           1/1     Running   0             85s
	kube-system   etcd-minikube                      1/1     Running   0             99s
	kube-system   kube-apiserver-minikube            1/1     Running   0             101s
	kube-system   kube-controller-manager-minikube   1/1     Running   0             97s
	kube-system   kube-proxy-j2zvg                   1/1     Running   0             85s
	kube-system   kube-scheduler-minikube            1/1     Running   0             97s
	kube-system   storage-provisioner                1/1     Running   1 (53s ago)   94s

---------------------- sub command창에서 실행
minikube dashboard

---------------------- main command back

docker images
REPOSITORY                    TAG       IMAGE ID       CREATED       SIZE
gcr.io/k8s-minikube/kicbase   v0.0.37   01c0ce65fff7   4 weeks ago   1.15GB

http://192.168.49.2:32473


========================================================================================================[k8s 환경구성]

install tool
	kubeadm
	kubespary

CNI(Container Network Interface)
	Container간 통신을 지원하는 VxLAN. Pod Network라고도 부름


1. master 1개 node 2개 생성
   master bamikube-master
   node1  bamikube-node1
   node2  paasta-ta-bami

all inception에  ================================================================================== [docker install] 
	
https://docs.docker.com/desktop/install/linux-install/
	
sudo apt-get update
 	
sudo apt-get install -y \
ca-certificates \
curl \
gnupg \
lsb-release

sudo mkdir -m 0755 -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
"deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin


systemctl enable docker
systemctl start docker
systemctl start docker

sudo docker run hello-world
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
$ sudo apt-get update
$ sudo apt-get install docker-ce
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
================================================================================================[kubernetes install]

1) 환경 설정

swapoff -a && sed -i '/swap/s/^/#/' /etc/fstab
nc 127.0.0.1 6443
	

2) kubeadm, kubectl, kubelet 설치

apt-get update

apt-get install -y apt-transport-https ca-certificates curl

curl -fsSLo /etc/apt/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg

echo "deb [signed-by=/etc/apt/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list

apt-get update

apt-get install -y kubelet kubeadm kubectl

apt-mark hold kubelet kubeadm kubectl

3) control-plane 구성
4) worker node구성
5) 설치 확인 


================================================================================[kubeadm]


#Master, Worker
$ sudo apt install ntp

#Master
$ sudo service ntp reload
$ sudo ntpq -p

#Worker
$ sudo vi /etc/ntp.conf
...
#모든 pool과 server 주석처리
server 10

#Worker
$ sudo systemctl restart ntp
$ sudo ntpq -p

#Master, Worker
$ swapoff -a




#Master, Worker
$ cat > docker.sh
#!/usr/bin/env bash

## INFO: https://docs.docker.com/engine/install/ubuntu/

set -euf -o pipefail
DOCKER_USER=ubuntu

# Install dependencies

sudo apt-get update && sudo apt-get install -y \
apt-transport-https \
ca-certificates \
curl \
gnupg \
lsb-release

# Add Docker’s official GPG key

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --yes --dearmor -o /usr/share/keyrings/docker-archive-keyring.gp

# Set up the stable repository

echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/lin
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Install Docker CE

sudo apt-get update && sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# Use Docker without root

sudo usermod -aG docker $DOCKER_USER
##############################enter -> ctrl+c
$ chmod +x docker.sh
$./docker.sh





#Master, Worker
$ cat > docker-compose.sh
#!/usr/bin/env bash

## INFO: https://docs.docker.com/compose/install/

set -euf -o pipefail
DOCKER_COMPOSE_VERSION=v2.1.1

# Download and install

sudo curl -L "https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-$(uname -s)-$(uname -m)"
sudo chmod +x /usr/local/bin/docker-compose
##############################enter -> ctrl+c
$ chmod +x docker-compose.sh
$./docker-compose.sh




#Master, Worker
$ sudo mkdir /etc/docker
$ cat <<EOF | sudo tee /etc/docker/daemon.json
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"storage-driver": "overlay2",
"runtimes": {
"nvidia": {
"path": "nvidia-container-runtime",
"runtimeArgs": []
}
},
"default-runtime": "nvidia"
}
EOF


$ sudo systemctl daemon-reload

$ sudo systemctl restart docker


#Master, Worker (root계정)
$ curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
$ sudo cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
deb https://apt.kubernetes.io/ kubernetes-xenial main
EOF
$ sudo apt update




#Master, Worker
#/etc/containerd/config.toml 파일에서 disabled_plugins 항목에서 CRI 제거
$ sudo vim /etc/containerd/config.toml
...
disabled_plugins = [""]
...



$ sudo systemctl restart containerd



$ sudo kubeadm init --apiserver-advertise-address 10.100.11.209 --pod-network-cidr=192.168.10.0/24
--apiserver-advertise-address 옵션은 마스터 노드의 API Server 주소를 설정할 때 사용하는 옵션이다. 워커노드들은 이 API 주소로 Master Node와 통신을 한다.
만약 Master Node를 다중으로 구성하였다면 --control-plane-endpoint 옵션을 사용하여 엔드 포인트를 설정할 수 있다. 




#Master
$ mkdir -p $HOME/.kube
$ sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
$ sudo chown $(id -u):$(id -g) $HOME/.kube/config



# Worker

$ sudo kubeadm join 10.100.11.209:6443 --token s43nna.n2uzng4pl5r2x74j \
--discovery-token-ca-cert-hash sha256:780917540b1d105299641788d848b8edad00caca640ee842d82ac987097a9a1



masternode

kubeadm init

kubeadm reset

sudo kubeadm init --apiserver-advertise-address 10.170.70.225 --pod-network-cidr=192.168.10.0/24

netstat -nap

1. 특정포트 외부에서 접속할 수 있도록 열기 (외부에서 접속할 수 있도록 포트 OPEN)
   아래는 외부에서 들어오는(INBOUND) TCP포트 12345의 연결을 받아들인다는 규칙을 1번 방화벽 규칙으로 추가한다는 의미이다.

1.1 TCP PORT일 경우

# iptables -I INPUT 1 -p tcp --dport 12345 -j ACCEPT

-I: 새로운 규칙을 추가한다.
-p: 패킷의 프로토콜을 명시한다.
-j: 규칙에 해당되는 패킷을 어떻게 처리할지를 정한다. 
※ 내부에서 외부로 나갈 수 있도록 포트 열기

# iptables -I OUTPUT 1 -p tcp --dport 9002 -j ACCEPT

# iptables -I OUTPUT 1 -p udp --dport 9002 -j ACCEPT

1.2 UDP PORT일 경우

# iptables -I INPUT 1 -p udp --dport 12345 -j ACCEPT

update
sudo ufw allow 179
sudo ufw allow 3306/tcp


--- openstack
/etc/hosts
115.68.198.131 paasta-controller

vi /etc/network/interfaces
dns-nameservers 8.8.8.8 8.8.4.4

vi /etc/resolv.conf
nameserver 8.8.8.8
nameserver 8.8.4.4



--- calico
curl https://raw.githubusercontent.com/projectcalico/calico/v3.25.0/manifests/calico.yaml -O

sed -i -e 's?192.168.0.0/16?192.168.10.0/24?g' calico.yaml

kubectl apply -f calico.yaml


$ sudo firewall-cmd --add-port=179/tcp --permanent
$ sudo firewall-cmd --add-port=4789/udp --permanent
$ sudo firewall-cmd --add-port=5473/tcp --permanent
$ sudo firewall-cmd --add-port=443/tcp --permanent
$ sudo firewall-cmd --add-port=6443/tcp --permanent
$ sudo firewall-cmd --add-port=2379/tcp --permanent
$ sudo firewall-cmd --reload

sudo firewall-cmd --add-port=111/tcp --permanent --zone docker
sudo firewall-cmd --add-port=2049/tcp --permanent --zone docker
sudo firewall-cmd --add-port=2379-2380/tcp --permanent --zone docker
sudo firewall-cmd --add-port=6443/tcp --permanent --zone docker
sudo firewall-cmd --add-port=10250/tcp --permanent --zone docker
sudo firewall-cmd --add-port=10252/tcp --permanent --zone docker
sudo firewall-cmd --add-port=10255/tcp --permanent --zone docker
sudo firewall-cmd --add-port=4789/tcp --permanent --zone docker



sudo firewall-cmd --add-port=111/tcp --permanent
sudo firewall-cmd --add-port=2049/tcp --permanent
sudo firewall-cmd --add-port=10250/tcp --permanent
sudo firewall-cmd --add-port=10255/tcp --permanent
sudo firewall-cmd --add-port=30000-32767/tcp --permanent
sudo firewall-cmd --add-port=4789/udp --permanent
sudo firewall-cmd --reload

kubeadm reset

kubeadm token create --print-join-command


======================================================================================== kubespray (paasta-cp)
issue
TASK [download : download_file | Create dest directory on node] **********************************
ok: [paasta-ta-bami-master]
ok: [paasta-ta-bami-cluster-1]
ok: [paasta-ta-bami-cluster-2]
Wednesday 08 March 2023  01:38:12 +0000 (0:00:00.563)       0:08:48.337 ******* 
Wednesday 08 March 2023  01:38:12 +0000 (0:00:00.065)       0:08:48.402 ******* 
Wednesday 08 March 2023  01:38:12 +0000 (0:00:00.074)       0:08:48.477 ******* 
FAILED - RETRYING: [paasta-ta-bami-master]: download_file | Validate mirrors (4 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-1]: download_file | Validate mirrors (4 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-2]: download_file | Validate mirrors (4 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-1]: download_file | Validate mirrors (3 retries left).
FAILED - RETRYING: [paasta-ta-bami-master]: download_file | Validate mirrors (3 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-2]: download_file | Validate mirrors (3 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-1]: download_file | Validate mirrors (2 retries left).
FAILED - RETRYING: [paasta-ta-bami-master]: download_file | Validate mirrors (2 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-2]: download_file | Validate mirrors (2 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-1]: download_file | Validate mirrors (1 retries left).
FAILED - RETRYING: [paasta-ta-bami-master]: download_file | Validate mirrors (1 retries left).
FAILED - RETRYING: [paasta-ta-bami-cluster-2]: download_file | Validate mirrors (1 retries left).
failed: [paasta-ta-bami-cluster-1] (item=None) => {"attempts": 4, "censored": "the output has been hidden due to the fact that 'no_log: true' was specified for this result", "changed": false}
failed: [paasta-ta-bami-master] (item=None) => {"attempts": 4, "censored": "the output has been hidden due to the fact that 'no_log: true' was specified for this result", "changed": false}
failed: [paasta-ta-bami-cluster-2] (item=None) => {"attempts": 4, "censored": "the output has been hidden due to the fact that 'no_log: true' was specified for this result", "changed": false}

>>>>>>>>>>>>>>>>>> download link error

ASK [etcd : Get currently-deployed etcd version] ************************************************
fatal: [paasta-ta-bami-master]: FAILED! => {"changed": false, "cmd": "/usr/local/bin/etcd --version", "msg": "[Errno 2] No such file or directory: b'/usr/local/bin/etcd'", "rc": 2, "stderr": "", "stderr_lines": [], "stdout": "", "stdout_lines": []}


The connection to the server 127.0.0.1:6443 was refused - did you specify the right host or port?
	>> iaas 통신 문제.

pod/metrics-server-584974b8b6-rh9mv   0/1     Running   0   53m   10.233.102.195   paasta-ta-bami-master 
	>> 
	
$kubectl version --output=yaml
  clientVersion:
  buildDate: "2022-09-21T13:19:24Z"
  compiler: gc
  gitCommit: b39bf148cd654599a52e867485c02c4f9d28b312
  gitTreeState: clean
  gitVersion: v1.24.6
  goVersion: go1.18.6
  major: "1"
  minor: "24"
  platform: linux/amd64
  kustomizeVersion: v4.5.4
  serverVersion:
  buildDate: "2022-09-21T13:12:04Z"
  compiler: gc
  gitCommit: b39bf148cd654599a52e867485c02c4f9d28b312
  gitTreeState: clean
  gitVersion: v1.24.6
  goVersion: go1.18.6
  major: "1"
  minor: "24"
  platform: linux/amd64


 $kubectl describe pod/metrics-server-584974b8b6-rh9mv -n kube-system
  Events:
  Type     Reason     Age                  From     Message

----     ------     ----                 ----     -------

  Warning  Unhealthy  83s (x410 over 61m)  kubelet  Readiness probe failed: HTTP probe failed with statuscode: 500

$kubelet log

  Error from server (ServiceUnavailable): the server is currently unable to handle the request (get nodes.metrics.k8s.io)

$kubectl logs pod/metrics-server-584974b8b6-rh9mv -n kube-system
  Error from server: Get "https://10.170.70.131:10250/containerLogs/kube-system/metrics-server-584974b8b6-rh9mv/metrics-server": remote error: tls: internal error

$systemctl daemon-reload && systemctl restart kubelet && systemctl status kubelet
  재시작

$systemctl status kubelet

$kubectl edit metirics-server-deployment.yml
  		args:
        - --cert-dir=/tmp
        - --secure-port=4443
        command:
        - /metrics-server
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname

        args:
        - --cert-dir=/tmp
        - --secure-port=4443
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        - --kubelet-use-node-status-port
        - --kubelet-insecure-tls
        - --metric-resolution=30s
    
      containers:
      - name: metrics-server
        image: k8s.gcr.io/metrics-server-amd64:v0.3.1
        imagePullPolicy: Always
        command:
        - /metrics-server
        - --logtostderr
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        args:
        - --logtostderr
        - --cert-dir=/tmp
        - --secure-port=4443

$ kubectl edit deployment.apps/metrics-server -n kube-system
      dnsPolicy: ClusterFirst
      hostNetwork: true
      nodeSelector:

$ vim /etc/kubernetes/manifests/kube-apiserver.yaml
      .command
      –enable-aggregator-routing=true

      $kubectl get deploy,svc -n kube-system | egrep metrics-server
      $kubectl get --raw "/apis/metrics.k8s.io/v1beta1/nodes"


masternode

kubeadm init

kubeadm reset

sudo kubeadm init --apiserver-advertise-address 10.170.70.225 --pod-network-cidr=192.168.10.0/24

netstat -nap

--------------------------------------------------------------------/etc/exports

/home/share/nfs 10.170.70.215(rw,no_root_squash,async)
/home/share/nfs 10.170.70.201(rw,no_root_squash,async)
/home/share/nfs 10.170.70.246(rw,no_root_squash,async)

--------------------------------------------------------------------

nfs 10.170.70.176 115.68.198.67 

paasta-ta-bami-master		115.68.198.111
paasta-ta-bami-nfs			115.68.198.67
paasta-ta-bami-cluster	    115.68.198.117
paasta-ta-bami-cluster2	    115.68.198.74



[이 세영 전임님] [4:42 PM] vsphere 
10.77.1.0/16

cre-master1 :: 10.77.1.22
cre-worker01 :: 10.77.1.24
cre-worker01v :: 10.77.1.23
ta-cre-for-ba-nfs 10.77.1.25
ubuntu/paastata
ubuntu/paastata

-----------------------------------------------------------------cp-cluster-vars.sh
#!/bin/bash

export MASTER_NODE_HOSTNAME=paasta-ta-bami-master
export MASTER_NODE_PUBLIC_IP=61.252.53.231
export MASTER_NODE_PRIVATE_IP=10.77.1.22

## Worker Node Count Info

export WORKER_NODE_CNT=2

## Add Worker Node Info

export WORKER1_NODE_HOSTNAME=worker01
export WORKER1_NODE_PRIVATE_IP=10.77.1.24
export WORKER2_NODE_HOSTNAME=worker01v
export WORKER2_NODE_PRIVATE_IP=10.77.1.23


## Storage Type Info (eg. nfs, rook-ceph)

export STORAGE_TYPE=nfs
export NFS_SERVER_PRIVATE_IP=10.77.1.25
~                                 

-----------------------------------------------------------------cp-cluster-vars.sh
#!/bin/bash

export MASTER_NODE_HOSTNAME=paasta-ta-bami-master
export MASTER_NODE_PUBLIC_IP=115.68.198.111
export MASTER_NODE_PRIVATE_IP=10.170.70.215

## Worker Node Count Info

export WORKER_NODE_CNT=2

## Add Worker Node Info

export WORKER1_NODE_HOSTNAME=paasta-ta-bami-cluster
export WORKER1_NODE_PRIVATE_IP=10.170.70.201
export WORKER2_NODE_HOSTNAME=paasta-ta-bami-cluster2
export WORKER2_NODE_PRIVATE_IP=10.170.70.246


## Storage Type Info (eg. nfs, rook-ceph)

export STORAGE_TYPE=nfs
export NFS_SERVER_PRIVATE_IP=10.170.70.174

~                                 
---------------------------------------------------------------------------





----------------------------------------------------------------------------------------------[metric-server]
kubectl get pods -n kube-system | grep metrics-server
kubectl edit deploy -n kube-system metrics-server
kubectl get all -n kube-system -o wide
kubectl logs pod/metrics-server-7fb89cdf69-csn8b -n kube-system
kubectl describe deployment.apps/metrics-server -n kube-system
kubectl describe pod/metrics-server-7fb89cdf69-csn8b -n kube-system
 kubectl describe node paasta-ta-bami-master

 etcd --listen-client-urls=http://$IP1:2379,http://$IP2:2379,http://$IP3:2379,http://$IP4:2379,http://$IP5:2379 --advertise-client-urls=http://$IP1:2379,http://$IP2:2379,http://$IP3:2379,http://$IP4:2379,http://$IP5:2379


 kubectl taint nodes bamikube-cluster2 node-role.kubernetes.io/control-plane:NoSchedule-





source <(kubectl completion bash)
echo "source <(kubectl completion bash)"
alias k=kubectl
complete -o default -F __start_kubectl k


------------------------------------------------------------------------------------------------ [nfs server] 


[master, cluster-1,cluster-2]
mkdir ./share/nfs-master
mkdir -p share/nfs-1
mkdir -p share/nfs-2


[nfs VM]
exportfs -v	:	볼륨 확인
showmount -e ${nfs-serverIP}	:	서버 공유가능한 정보 확인
rpcinfo -p [NFS_Server_IP]	:	마운트 준비

mount -t nfs 10.170.70.174:/home/share/nfs /home/share/nfs-1
mount -t nfs 10.170.70.174:/home/share/nfs /home/share/nfs-2
mount -t nfs 10.170.70.174:/home/share/nfs /home/share/nfs-master	:	마운트 연결
df -h 	:	filesystem info

[git clone]
git clone https://github.com/kubernetes-sigs/nfs-subdir-external-provisioner

[set authorization] 구성설치
k apply -f rbac.yaml 

[provisioner install]
k apply -f deployment.yaml

[update storageClass]
k apply -f class.yaml

k get pod
result: nfs-client-provisioner-6c9ffc85c-wcgg6   1/1     Running   0             48m

k get pvc
NAME          STATUS   VOLUME                                     CAPACITY   ACCESS MODES   STORAGECLASS   AGE
mariadb-pvc   Bound    pvc-1c9c4e8d-317d-4c35-8d39-61ed6e28d401   1Mi        RWX            nfs-client     88m

k get sc
result: 
NAME                               PROVISIONER                                   RECLAIMPOLICY   VOLUMEBINDINGMODE   ALLOWVOLUMEEXPANSION   AGE
nfs-client                         k8s-sigs.io/nfs-subdir-external-provisioner   Delete          Immediate           false                  102m
paasta-cp-storageclass (default)   paasta-cp-nfs-provisioner                     Delete          Immediate           false                  24h


[처음에 bound가 안되었음]

NAME          STATUS    VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
mariadb-pvc   Pending                                      nfs-client     31m

$k describe pvc mariadb-pvc -n default

Name:          mariadb-pvc
Namespace:     default
StorageClass:  nfs-client
Status:        Pending
Volume:        
Labels:        <none>
Annotations:   volume.beta.kubernetes.io/storage-provisioner: k8s-sigs.io/nfs-subdir-external-provisioner
               volume.kubernetes.io/storage-provisioner: k8s-sigs.io/nfs-subdir-external-provisioner
Finalizers:    [kubernetes.io/pvc-protection]
Capacity:      
Access Modes:  
VolumeMode:    Filesystem
Used By:       <none>
Events:
  Type    Reason                Age                    From                         Message

----    ------                ----                   ----                         -------

  Normal  ExternalProvisioning  2m32s (x122 over 32m)  persistentvolume-controller  waiting for a volume to be created, either by external provisioner "k8s-sigs.io/nfs-subdir-external-provisioner" or manually created by system administrator

> 시간 지나고 저절로 바운딩 됨.


[test1]

apiVersion: v1
kind: Pod
spec:
  volumes:

  - name: nfs-pvc
    persistentVolumeClaim:
      claimName: mariadb-pvc
      containers:

    - name: nfs-pvc-test
      image: nginx:alpine
      ports:

      - containerPort: 80

      volumeMounts:

        - name: nfs-pvc
          mountPath: /tmp
          [test2]

kind: Pod
apiVersion: v1
metadata:
  name: mariadb-pod-test
spec:
  containers:

  - name: mariadb-pod-test
    image: busybox:stable
    command:
      - "/bin/sh"
        args:
      - "-c"
      - "touch /mnt/SUCCESS && exit 0 || exit 1"
        volumeMounts:
      - name: nfs-pvc
        mountPath: "/mnt"
          restartPolicy: "Never"
          volumes:
    - name: nfs-pvc
      persistentVolumeClaim:
        claimName: mariadb-pvc


k get pods
NAME                                     READY   STATUS      RESTARTS      AGE
mariadb-pod-test                         0/1     Completed   0             22m
nfs-client-provisioner-6c9ffc85c-wcgg6   1/1     Running     0             164m
nfs-pod-provisioner-8cf58bb66-b7qg4      1/1     Running     4 (26h ago)   26h
test-pod                                 1/1     Running     0             5m35s


[test1]
kubectl exec -it test-pod -- sh
/ # cd /tmp/
/tmp # echo "this is a test" > test.txt
/tmp # cat test.txt
this is a test
/tmp # exit

[생성 확인]
cd /home/share/nfs-master/

- 루트가 어디인지 몰라 헤맸는데 처음 마운트 할 때 공유 한 폴더에 있음
  mount -t nfs 10.170.70.176:/home/share/nfs /home/share/nfs-master 로 mount했음
  그래서 각 공유 폴더로 이동하면 파일 생성 및 테스트가 완료됨


----------------------------------------------------------------------------------------------[configMap]

[configmap 생성 후 pod에게 전달 방법]

1. 환경변수 
2. args
3. volume mount


$ kubectl create configmap CONFIG_NAME --from-file={SOURCE} 	
	:	파일 key=filename/ value=filecontent
$ kubectl create configmap CONFIG_NAME --from-literal={KEY1}={VALUE1}	
	:	key-value 형태
$ kubectl create configmap CONFIG_NAME --from-file=NAME=FILENAME	
	:	key=NAME/value=FILENAME
$ kubectl create configmap CONFIG_NAME --from-file=/configmap.dir/	
	:	dir 내에 있는 file(갯수제한없음) 모두가 key
		file content는 value가됨.


-----------------------------------------------------------------------------------[service/deployment/작성]



kubectl exec -it pod/mariadb-69d9f74d79-rrmg7 -- bash


2023-03-14 06:35:46+00:00 [Note] [Entrypoint]: Entrypoint script for MariaDB Server 1:10.7.8+maria~ubu2004 started.
2023-03-14 06:35:46+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2023-03-14 06:35:46+00:00 [Note] [Entrypoint]: Entrypoint script for MariaDB Server 1:10.7.8+maria~ubu2004 started.
2023-03-14 06:35:46+00:00 [ERROR] [Entrypoint]: Database is uninitialized and password option is not specified
	You need to specify one of MARIADB_ROOT_PASSWORD, MARIADB_ROOT_PASSWORD_HASH, MARIADB_ALLOW_EMPTY_ROOT_PASSWORD and MARIADB_RANDOM_ROOT_PASSWORD

      env:
          - name: MARIADB_ROOT_PASSWORD
            valueFrom:
              secretKeyRef:
                name: mariadb-secret
                key: password





------------------------------------------------------------------------------------[mariadb shell command]

select host, user, password from user;

update user set password=password('1234') where user='bami';

set password for 'user'@'%' = password('1234');

set password for 'bami'@'%' = password('tmdgP0425!');



sudo systemctl stop mysql

sudo mysqld_safe --skip-grant-tables &

mysql -u root


ALTER USER 'root'@'localhost' IDENTIFIED BY 'tmdgP0425!';
	안되면 UPDATE mysql.user SET authentication_string = PASSWORD('tmdgP0425!') WHERE User = 'root' AND Host = 'localhost';

 FLUSH PRIVILEGES;

mysqladmin -u root -p shutdown


sudo /etc/init.d/mysql start


sudo systemctl start 
sudo systemctl start mariadb

systemctl status mariadb.service

select host, user, password from user;

update user set password=password('1234') where user='bami';

set password for 'user'@'%' = password('1234');

set password for 'bami'@'%' = password('tmdgP0425!');

sudo systemctl stop mysql

sudo mysqld_safe --skip-grant-tables &

mysql -u root


ALTER USER 'root'@'localhost' IDENTIFIED BY 'tmdgP0425!';
	안되면 UPDATE mysql.user SET authentication_string = PASSWORD('tmdgP0425!') WHERE User = 'root' AND Host = 'localhost';

 FLUSH PRIVILEGES;

mysqladmin -u root -p shutdown

sudo /etc/init.d/mysql start


sudo systemctl start 
sudo systemctl start mariadb

---

systemctl status mariadb.service
sudo apt-get purge mariadb-server
sudo apt autoremove
sudo apt-get purge mysql-common
sudo ln -sf /usr/share/zoneinfo/Asia/Seoul/etc/localtime  //타임존 설정파일 대체
sudo timedatectl set-timezone 'Asia/Seoul' //명령어로 설정
sudo apt-get install -y mariadb-server

data:
  init.sql: |
    CREATE USER 'psta_common'@'%' IDENTIFIED BY 'paastaadmin';
    GRANT all privileges on *.* to psta_common@localhost identified by 'paastaadmin';
    FLUSH PRIVILEGES;
    CREATE DATABASE IF NOT EXISTS `pstadb_common` DEFAULT CHARACTER SET utf8;
    USE pstadb_common;
    CREATE TABLE pop_up (no INT(11) NOT NULL, title VARCHAR(255) NOT NULL, file_no INT(11), link_url VARCHAR(255) NOT NULL);
    INSERT INTO pop_up (no, title, file_no, link_url) values (1, 'test', 123, 'test_url');


kubectl -n kube-system delete pods --grace-period=0 --force coredns-74d6c5659f-xljmf

kubectl get apiservice | grep metrics
False (MissingEndpoints)

kubectl get svc metrics-server -n kube-system
curl -k 10.233.37.193
curl: (7) Failed to connect to 10.233.37.193 port 80: Connection refused








---------------------------------------------------------------------------------------------------

issue

pod/coredns-74d6c5659f-6brz7                        0/1     Pending            0                 20h    <none>           <none>                     <none>           <none>
pod/coredns-74d6c5659f-wdsxc                        0/1     Running            1                 7d1h   10.233.102.227   paasta-ta-bami-master      <none>           <none>
pod/metrics-server-584974b8b6-k56xm                 0/1     CrashLoopBackOff   683 (4m10s ago)   7d1h   10.233.102.243   paasta-ta-bami-master      <none>           <none>

daemonset.apps/kube-proxy     3         3         1       3            1           kubernetes.io/os=linux   7d1h   kube-proxy    registry.k8s.io/kube-proxy:v1.24.6              k8s-app=kube-proxy

deployment.apps/coredns          0/2     2            0           7d1h   coredns          registry.k8s.io/coredns/coredns:v1.8.6                            k8s-app=kube-dns
deployment.apps/dns-autoscaler   1/1     1            1           7d1h   autoscaler       registry.k8s.io/cpa/cluster-proportional-autoscaler-amd64:1.8.5   k8s-app=dns-autoscaler
deployment.apps/metrics-server   0/1     1            0           7d1h   metrics-server   registry.k8s.io/metrics-server/metrics-server:v0.6.1              app.kubernetes.io/name=metrics-server,version=v0.6.1


NAME                       STATUS     ROLES           AGE    VERSION   INTERNAL-IP     EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION      CONTAINER-RUNTIME
paasta-ta-bami-cluster-1   NotReady   <none>          7d1h   v1.24.6   10.170.70.33    <none>        Ubuntu 20.04.4 LTS   5.4.0-107-generic   cri-o://1.24.4
paasta-ta-bami-cluster-2   NotReady   <none>          7d1h   v1.24.6   10.170.70.159   <none>        Ubuntu 20.04.4 LTS   5.4.0-107-generic   cri-o://1.24.4
paasta-ta-bami-master      Ready      control-plane   7d1h   v1.24.6   10.170.70.131   <none>        Ubuntu 20.04.6 LTS   5.4.0-144-generic   cri-o://1.24.4


-> 클러스터 재구성 함 
















kubectl create secret generic mariadb-password --from-literal='password=paastabami' --namespace="default"
k create secret generic mysql-credential --from-file=./username --from-file=./password


kubectl create secret generic mysql-password --from-literal='password=tmdgP0425!' --namespace="default"


kubectl create secret generic mariadb- --from-literal='password=tmdgP0425!' --namespace="default"


watch kubectl get pod









30 8 * * 1











---

issue

Error from server: error dialing backend: remote error: tls: internal error		== 인증서가 수락되지 않아서 ( certificate 'pending')

kubectl get csr  --sort-by=.metadata.creationTimestamp	인증 목록 확인
kubectl exec --v=8 -it mariadb-6c5c8d5dcb-tqscs  -- sh  오류 목록 자세히 

kubectl get csr  --sort-by=.metadata.creationTimestamp 
kubectl certificate  approve  <csr-id>
kubectl get csr | grep Pending | awk '{print $1}' | xargs -L 1 kubectl certificate approve

k exec -it  mariadb-6c5c8d5dcb-tqscs -n default -- bash

---









---

issue

 pod/mariadb-pod-test                                    Failed to pull image "busybox:stable": rpc error: code = Unknown desc = reading manifest stable in docker.io/library/busybox: toomanyrequests: You have reached your pull rate limit. You may increase the limit by authenticating and upgrading: https://www.docker.com/increase-rate-limit


pod가 계속 띄워지지 않고 imagepullbackOFF만 뜸..
아무리 찾아봐도 iaas문제이거나 혹은 yaml파일 문제래 근데 이제껏 성공해온 yaml으로 배포해줌..
그러다 testpod 띄우는게 있어서 test해봄 (mariadb 다 지움)
똑같이 imagepullbackoff가 떴고 그런데 log가 다른 버전이 나옴.

그래서 찾아봤더니.. 
비인증 계정은 100번이 limit인것
https://docs.docker.com/docker-hub/download-rate-limit/#how-can-i-check-my-current-rate
https://smelting.tistory.com/31

ubuntu@paasta-ta-bami-master:~/workspace/mariadb$ TOKEN=$(curl "https://auth.docker.io/token?service=registry.docker.io&scope=repository:ratelimitpreview/test:pull" | jq -r .token)
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  4415    0  4415    0     0   5741      0 --:--:-- --:--:-- --:--:--  5733
ubuntu@paasta-ta-bami-master:~/workspace/mariadb$ TOKEN=$(curl "https://auth.docker.io/token?service=registry.docker.io&scope=repository:ratelimitpreview/test:pull" | jq -r .token)
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  4415    0  4415    0     0   7019      0 --:--:-- --:--:-- --:--:--  7019
ubuntu@paasta-ta-bami-master:~/workspace/mariadb$ ^C
ubuntu@paasta-ta-bami-master:~/workspace/mariadb$ curl --head -H "Authorization: Bearer $TOKEN" https://registry-1.docker.io/v2/ratelimitpreview/test/manifests/latest
HTTP/1.1 200 OK
content-length: 2782
content-type: application/vnd.docker.distribution.manifest.v1+prettyjws
docker-content-digest: sha256:767a3815c34823b355bed31760d5fa3daca0aec2ce15b217c9cd83229e0e2020
docker-distribution-api-version: registry/2.0
etag: "sha256:767a3815c34823b355bed31760d5fa3daca0aec2ce15b217c9cd83229e0e2020"
date: Mon, 20 Mar 2023 08:15:41 GMT
strict-transport-security: max-age=31536000
ratelimit-limit: 100;w=21600
ratelimit-remaining: 100;w=21600
docker-ratelimit-source: 115.68.198.111



ratelimit-remaining: 앞에숫자 <- 이게 내가 시도한 숫자..ㅋㅋㅋ


login을 해주자^^
docker login -u seunghyejeong
tlqkfak0315!



apiVersion: v1  
kind: Pod  
metadata:  
  name: hello-world  
spec:  
  containers:  

  # specification of the pod’s containers  

  # ...  

  securityContext:  
    readOnlyRootFilesystem: true


---


volumeMounts:

- name: mariadb-pvc
  mountPath: {path}
- name: mariadb-config-volume
  mountPath: {path}


volume:

- name: mariadb-pvc
  persistentMolumeClaim:
    claimName: mariadb-pvc
- name: mariadb-config-volume
  configMap:
    name: {configMap name}



---

issue


Installation of system tables failed!  Examine the logs in /var/lib/mysql/ for more information.

The problem could be conflicting information in an external my.cnf files. You can ignore these by doing:

    shell> /usr/bin/mariadb-install-db --defaults-file=~/.my.cnf

You can also try to start the mariadbd daemon with:

    shell> /usr/sbin/mariadbd --skip-grant-tables --general-log &

and use the command line tool /usr/bin/mariadb to connect to the mysql database and look at the grant tables:

    shell> /usr/bin/mariadb -u root mysql
    MariaDB> show tables;

Try '/usr/sbin/mariadbd --help' if you have problems with paths.  Using --general-log gives you a log in /var/lib/mysql/ that may be helpful.

	1)
	
	  initContainers:
	  - name: take-data-dir-ownership
	    image: alpine:3
	    # Give `mysql` user permissions a mounted volume
	    # https://stackoverflow.com/a/51195446/4360433
	    command:
	    - chown
	    - -R
	    - 999:999
	    - /var/lib/mysql
	    volumeMounts:
	    - name: data
	      mountPath: /var/lib/mysql


/usr/tmpDSK가 손상되었을 수 있습니다. 다음을 사용하여 MySQL 및 기타 응용 프로그램과 같이 /tmp에 파일이 열려 있는 모든 프로세스를 중지해야 합니다.

/usr/sbin/lsof /tmp

다음 명령을 해당 순서로 실행합니다.

/bin/umount -l /tmp
/bin/umount -l /var/tmp
/bin/rm -fv /usr/tmpDSK

/scripts/securetmp


---

error: error parsing version2-statefulset.yaml: error converting YAML to JSON: yaml: line 49: mapping values are not allowed in this context

yaml 파일 시작전에 --- 하니까 됨

---

The StatefulSet "mariadb" is invalid: spec: Forbidden: updates to statefulset spec for fields other than 'replicas', 'template', 'updateStrategy', 'persistentVolumeClaimRetentionPolicy' and 'minReadySeconds' are forbidden

배포된거 삭제하고 배포해라

---

  Warning  FailedScheduling  103s                default-scheduler  0/3 nodes are available: 3 pod has unbound immediate PersistentVolumeClaims. preemption: 0/3 nodes are available: 3 Preemption is not helpful for scheduling.

volumes중에 안맞는 volume이 정의되어있음

----

---

pod 및 접속 성공
configmaps로 password 전달


https://loft.sh/blog/kubernetes-statefulset-examples-and-best-practices/

apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mariadb-set
spec:
  selector:
    matchLabels:
      app: mysql
  serviceName: "mysql"
  replicas: 3
  template:
    metadata:
      labels:
        app: mysql
    spec:
      terminationGracePeriodSeconds: 10
      containers:
      - name: mysql
        image: mariadb
        ports:
        - containerPort: 3306
        volumeMounts:
        - name: mysql-store
          mountPath: /var/lib/mysql
        env:
          - name: MYSQL_ROOT_PASSWORD
            valueFrom:
              secretKeyRef:
                name: mariadb-password
                key: password
  volumeClaimTemplates:

  - metadata:
    name: mysql-store
    spec:
      accessModes: ["ReadWriteOnce"]
      storageClassName: "bami-sc"
      resources:
        requests:
          storage: 1Gi


mysql -u root -p -h mariadb-set-0.default.svc.cluster.local

mariadb-set-0.mysql.default.svc.cluster.local

k taint nodes paasta-ta-bami-master node-role.kubernetes.io=master:NoSchedule

---

issue

zone?
sudo firewall-cmd --get-active-zones
sudo firewall-cmd --zone=public --change-interface=docker0 --permanent

sudo firewall-cmd --reload
---




---

issue
docker info
WARNING: No swap limit support

journalctl -u docker.service

firewall-cmd --remove-interface=docker0 --permanent
firewall-cmd --zone=trusted --remove-interface=docker0 --permanent
systemctl start docker




---

info

If you create a table in Pod A, the table will be available in the shared data path (/var/lib/mysql), and the other pods B and C will be able to access it as well. Similarly, any other data written to the shared data path will be accessible by all pods in the StatefulSet.

However, note that MySQL is not designed to run in a shared filesystem environment. Running multiple MySQL instances on the same data directory can result in database corruption, as the instances may not have exclusive access to the data. Therefore, it is recommended to use a clustered database solution like MySQL Cluster or Percona XtraDB Cluster if you need a high availability solution for MySQL on Kubernetes.

:: mysql의 db는 공유 될 수 없다.
---

apiVersion: v1
kind: Service
metadata:
  name: mysql-cluster
spec:
  selector:
    app: mysql-cluster
  clusterIP: None
  ports:

  - name: mysql
    port: 3306
    targetPort: 3306

---

apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mysql-cluster
spec:
  selector:
    matchLabels:
      app: mysql-cluster
  serviceName: mysql-cluster
  replicas: 3
  template:
    metadata:
      labels:
        app: mysql-cluster
    spec:
      terminationGracePeriodSeconds: 10
      containers:
      - name: mysql
        image: percona/percona-xtradb-cluster:latest
        ports:
        - containerPort: 3306
        volumeMounts:
        - name: mysql-data
          mountPath: /var/lib/mysql
        env:
          - name: MYSQL_ROOT_PASSWORD
            valueFrom:
              secretKeyRef:
                name: mysql-password
                key: password
          - name: XTRABACKUP_PASSWORD
            valueFrom:
              secretKeyRef:
                name: xtrabackup-password
                key: password
        command:
        - /bin/bash
        - -c
        - |
          # configure node
          if [ $(hostname -s) = "mysql-cluster-0" ]; then
            mysql -uroot -p$MYSQL_ROOT_PASSWORD -e "CREATE USER 'cluster'@'%' IDENTIFIED BY 'cluster'; GRANT ALL PRIVILEGES ON *.* TO 'cluster'@'%' WITH GRANT OPTION;"
            service mysql bootstrap-pxc
          else
            service mysql start
          fi
      volumes:
      - name: mysql-data
        persistentVolumeClaim:
          claimName: mysql-data
  volumeClaimTemplates:

  - metadata:
    name: mysql-data
    spec:
      accessModes: ["ReadWriteOnce"]
      storageClassName: "bami-sc"
      resources:
        requests:
          storage: 1Gi


---

info
volumeClaimTemplates
스테이트풀셋의 특별함


스테이트풀셋의 목적은 상태를 가지고 있는 것이다.
헤드리스 서비스를 통해서 각각의 도메인을 가지므로 독립적인 도메인을 가지고 독립적으로 파드를 접근할 수 있다.
볼륨의 관점에서 보면, 각각의 고유의 상태값을 가지므로 volumeClaimTemplates으로 클레임하면 각각의 PV를 가지게 된다.

-----


kubectl create secret generic mariadb-password --from-literal='password=root'

---

docker-entrypoint-initdb.d

컨테이너 시작시 초기화 스크립트를 실행하기 위해 사용하는 이미지.
---



---

info 
docker user로 실행하기
sudo groupadd docker : 도커 그룹이 없다면 생성
sudo usermod -aG docker {USERNAME}
리부트

newgrp docker
---


---

Note that the metadata section includes a name field for the StatefulSet, as well as labels that match those used in the selector field. The serviceName field specifies the name of the Kubernetes Service to use with this StatefulSet.


```right version
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mariadb-set
  labels:
    app: mariadb
spec:
  selector:
    matchLabels:
      app: mariadb
  serviceName: mariadb
  replicas: 1
  template:
    metadata:
      labels:
        app: mariadb
```

```틀림
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mariadb-set
spec:
  selector:
    matchLabels:
      app: mariadb
  serviceName: mariadb
  replicas: 1
  template:
    metadata:
      labels:
        app: mariad
```


``` general 순서 
Secret
ConfigMap
PersistentVolumeClaim
StatefulSet
```


---

info

- initcontainer로 폴더 만들어주기
- 만든 폴더로 mount  하기

```yaml
      initContainers:
      - name: create-mariadb-backup-dir
        image: busybox
        command: ['sh', '-c', 'mkdir -p /backup/mariadb']
        volumeMounts:
        - name: backup-script
          mountPath: /backup
```

image : mariadb -> busybox 

Q: why pulled busybox in initContainers? 
A: 
busybox isinitContainer sectioninitContainer isbusybox imageinitContainer toinitContainer and the main container.

The busybox image is a lightweight and widely-used image that includes many common Linux utilities, including cp, which is used to copy the SQL script file from the ConfigMap to the shared volume. By using busybox, we avoid the need to install additional software or dependencies in the initContainer.

Q: busybox is correct then mariadb in that case?





---

info : mount!!!!!!!!!

Q: 
If the host and the pad have the same directory, but the files are different, for example, if the file exists only on the host and the file does not exist on the pad, or if the file in the directory itself is different, which file is replaced by the host? Or a pad

A:
If the host and the pod have the same directory with different files, the file in the directory will depend on which file was mounted when the pod was created. If the host file was mounted to the pod, then the file in the pod's directory will be the same as the file on the host. If the pod file was mounted to the host, then the file in the host's directory will be the same as the file on the pod.

However, if the files have different content, the mounted file will overwrite the original file. This means that if the host file is mounted to the pod and has different content than the pod file, the content of the host file will replace the content of the pod file in the directory. Similarly, if the pod file is mounted to the host and has different content than the host file, the content of the pod file will replace the content of the host file in the directory.


Q:
When you mount a host's folder as stateful, are the folders and data generated in the pods host?

A: 
When you mount a host's folder in a pod as a stateful volume, the folders and data are not generated in the host. Instead, the contents of the host's folder are made available to the pod as a volume. Any changes made to the contents of the volume from within the pod will be reflected in the host folder, and vice versa. So, any folders or data that are created or modified within the volume from within the pod will be visible on the host, and any changes made to the host's folder will be visible within the volume in the pod.


kubectl create configmap mariadb-backup-script --from-file=mariadb-backup.sh

securityContext.runAsNonRoot=true



---

mariadb conclusion

1. cronjob이 pod로 생성된다는 점을 간과함.
   : cronjob의 pod가 mariadb pod로 접속해야함
     : mariadb pod의 고유주소 필요
     -> mariadb service endpoints 
2. nfs  server를 master에만 설치 
   : mount 오류
3. volumemount 
   : statefulset으로 생성된 volume의 calimName 이름을 정확히 입력.

* headless service
  statefulset으로 배포될때는 headless service를 이용한다
  1.pod 각각의 고유한 ip를 가져야 할 때 
  2.headless service는 pod 각각의 ip를 endpoint로 가지고 있다. 
    : 이를 통해 pod접속 가능함


test2 table
data
pod 삭제 후 다시 재생성 되면 data호가인