bamikube


로드밸런스



-----------------------------------------------------------------------------

|	클러스터(cluster)
|		::노드의 집합
|									  
| 
|	|------------------------------------------------------------------------
| 	|	> 노드(node)
|	|	::컴퓨터 과학에 쓰이는 기초적인 단위.
|	|	다양한 분야에서 쓰이지만 공통적인 의미는 무언가를 연결하는 'point'를 뜻함.
|	|	하나의 노드는 다른 노드와 연결되어 있음
|	|		::(node)----link----(node)
|	|	k8s에서 노드는 컨테이너화된 app을 실행하는 요소.
|	|
|	|	---[Kubernetes Master(Control Plane)]--------------
|	|	| 마스터노드 :: 각 워커 노드들의 배치, 모니터링, 관리 |
|	|	---------------------------------------------------
|	|	---[Node]--------------------------------------------------------
|	|	| 워커노드	:: 각기 다른 목적과 기능으로 세분화된 컨테이너			|
|	|	| 	(마스터 노드도 공통적으로 포함되어 있음)						|	
|	|	|																|
|	|   |																|
|	|	| 	---------------------------------							|
|   |   |	| > 파드(pod)					|							|
|	|	|	|	-------------------------   |							|
|	|	|	| 	| > 컨테이너(container)	|   |							|   
|	|	|	|	 -----------------------	|							|
|   |  	|	---------------------------------							| 
|   |	-----------------------------------------------------------------

| ------------------------------------------------------------------------ |
| ------------------------------------------------------------ |

- k8s는 모두 API로 통신한다.

- k8s object(object == resource )
  : .yml로 표기
  : app의 동작 상태 , app의 사용가능한 리소스, 재구동 정책, 업그레이드 등에 대한 정책 포함
  : "record of intent"

  > (object) spec
  > : ex ) pod API reference , pod 및 pod의 desire state를 기술
  > : 파드의 각 컨테이너에 대한 컨테이너 이미지.
  > : obejct를 생성할 때 원하는 resource를 설명, 제공
  > (object) status 
  > : 오브젝트에 대한 상태 (What state you desire for the object)
  > : 시스템 , 컴포넌트에 의해 제공, 업데이트 된 오브젝트의 현재 상태를 설명.

  + 이를 실제 상태를 object가 desire한 상태와 일치시키기 위해 능동적인 관리를 하는 것이 Control Plane (Kubersates Master Node)이다.	

  deployment.yml > spec read > update 
  if status change > respons to the diffrent spec & status 
  				 > stating a replacement instance
  				 > and making correction 

- object 관리
  kubectl이라는 CLI를 통한 구성 
  	: 명령형 command
  		- 클러스터 내 활성 오브젝트를 대상으로 직접 동작
  		- 일회석 작업을 개시시키거나 동작시키기 위한 방법.
  		- kubectl create -f nginx.yaml
  		- 활성 오브젝트를 상대로 직접 영향을 끼치기 때문에 이전 구성에 대한 이력을 제공하지 않음.
  		- kubectl create deployment nginx --image nginx

  	: 명령형 오브젝트 구성
  		- JSON / YAML file을 통해 (contain full definitions of the object) 
  		- command로 선택적인 작접 생성, 교체 가능
  		- 하나의 파일 시스템으로 이해하면 좋다 (git에서 source관리 가)
  		- kubectl delete -f nginx.yaml -f redis.yaml
  		- kubectl replace -f nginx.yaml 
  	: 선언형 command
  		- local에 보관한 오브젝트 구성 파일을 대상으로 작동
  		- 사용자는 파일에 동작을 정의하지 않고 kubectl이 자동으로 감지하여 생성, 업데이트, 삭제 작업이 이루어짐
  		- this enables working on derectories, where diffrent operations might be nedded for diffrent object.

- node

  > kubelet 
  > container runtime
  > kube-proxy

  - 이름으로 식별되어 중복될 수 없다. 만약 같은 이름을 가지는 경우 k8s는 동일한 객체라고 인식한다. 
  - 이에 노드를 교체하거나 업데이트 하는 경우에는 기존 노드 오브젝으를 제거 후 업데이트 하고 다시 추가해야한다.
  - 노드 관리
    : --register-node=false로 설정 후 수동 생성 가능 
    : 보통 --register-node가 기본값으로 제공되어 kubelet이 API 서버에 자동으로 등록한다.
  - 노드 상태
    : kubectl describe node <insert-node-name-here>
    : Address, Conditions, Capacity and Allocatable(용량과 할당가능), Info를 포함한다.
  - Heartbeats
    : k8s의 노드가 보내는 hearbeats로 클러스터가 개별 노드가 가용한지 판단 할 수 있도록 도움을 주고 장애 조치가 가능.
  - NodeController
    : CIDR 블럭을 할당
    2. Controller 내부의 node list를 사용 가능한 머신 리스트 정보를 근거로 최신 상태로 유지한다. 
    3. Node의 동작상태를 모니터링한다.
       : 불가능 상태일때는 .status filed를  ready -> unkown으로 update
  - Resource 용량 추적
    : 사용가능한 메모리의 양과 CPU 수를 추척하고 용량을 보고한다.
  - Node의 네트워크
    : k8s의 node는 어느것도 원격 서비스에 노출되도록 설계되지 않았다.
    : 반드시 HTTPS 포트 (443port) 에서 수신되도록 설계되어 있다.
    : 익명의 요청이나 service account token이 허용되는 경우에는 하나 이상의 인증 형식이 사용되어야 한다.

    1. API server with kubelet
       실행중인 pods를 kubelet과 연결한다.
    2. API server with ssh 
       현재는 지원하지 않고 Konnectivity 서비스를 사용해야

- Controller(replica set)
  : k8s Controller는 cluster의 생태를 관찰 한 다음, 필요한 변경사항을 요청하거나 생성한다. 이는 현재 클러스터 상태를 desire한 status와 가깝게 유지한다.

  [pattern]

  > API server를 통한 controller
  > ex. Job Controller가 cluster API와 상호작용하며 상태를 관리한다. job controller는 pod나 container를 스스로 실행시키는 것이 아닌 API 에 명령하여 실행시키도록 한다. 
  > direct control
  > ex. Controller loop는 API server를 통해 상태를 접수한 다음 그것이 외부로 부터 필요하다 판단되면 외부 상태와 상호 작용하여 직접 통신한다.  
  > Controllers that interact with external state find their desired state from the API server,then communicate directly with an external system to bring the current state closer in line.
  > The important point here is that the controller makes some changes to bring about your desired state, and then reports the current state back to your cluster's API server. Other control loops can observe that reported data and take their own actions.
  > Design 원리에 따라 클러스터 각각을 관리하는 많은 컨트롤러를 사용 할 수 있다. 대게 controller loop는 control 하고있는 해당 resource만 관리한다. 
  > controller loop 가 job을 관리하기 위한 job controller를 생성하면 job에대한 pod/container는 job controller가 수행하지만 controller loop는 
  > 디플로이먼트 컨트롤러와 잡 컨트롤러는 쿠버네티스의 자체("내장" 컨트롤러)로 제공되는 컨트롤러 예시이다. 쿠버네티스를 사용하면 복원력이 뛰어난 컨트롤 플레인을 실행할 수 있으므로, 어떤 내장 컨트롤러가 실패하더라도 다른 컨트롤 플레인의 일부가 작업을 이어서 수행한다.

- Owner references 
  Controler plain에게 object간의 종속성을 알려주는 개념이다. 
  많은 object는 owner references를 통해 서로 연결되어 있다.
  name space의 소유자는 종속 object와 동일한 name space에 존재하며 그렇지 않다면 소유자 참조는 없는것으로 간주된다. 이러한 종속 오브젝트는 소유자가 없는 것으로 확인되어 k8s의 garbage 로 인해 삭제된다.

- Containers

  > image
  >
  > - binary data that encapsulates an application and all its software depenencies. 
  > - Each container that you run is repeatable; the standardization from having dependencies included means that you get the same behavior wherever you run it.
  > - create a containter image -> push it to registry -> referring in a pod
  > - cluster의 모든 node에는 container runtime이 존재하고 그것이 실행 되어야 kubelet이 pod와 container를 구동 할 수 있다. container runtime interface(CRI)는 kubelet과 container runtime 사이의 통시을 위한 프로토콜이다.
  > - puase, example/mycontainer/kube-apiserver와 같은 이름이 부여되며 registry hostname이 포함되기도 한다. 만약 registry hostname이 없다면 
  >   k8s는 Docker puglic registry로 판단한다.
  >   runtimeclass
  > - feature container runtime
  > - 서로 다른 pod가 제공하는 환경에 따라 runtimeClass를 제공하여 성능이나 보안의 균형을 유지한다. 
  >   ex. 높은 보안 인증 보증이 요구됨 -> contianer runtime을 VM으로 실행시키는 runtimeClass를 설정함
  >   ex. 같은 container runtime을 사용하나 설정이 다른 경우 -> runtimeClass로 지정하여 다른 설정의 pod를 함께 실행시킴 

  	1. node.k8s.io API > esource 생성 (name/handler)
  	2. pod의 .spec에 runtimeClassNmae을 명시하여 사용
  	- Scheduling을 통해 container를 schedule된 runtimeClass로 실행되게끔 조건을 걸 수 있다. 만약 scheduling이 설정되어 있지 않다면 runtimeClass는 모든 노드에서 지원되는 것으로 간주된다. 

  > Containter Environment
  >
  > - 하나의 이미지와 그 이상의 볼륨이 결합된 FILE SYSTEM
  > - Information about the Container ITSELF
  > - Information about other objects in the cluster

  	- 컨테이너의 호스트네임은 컨테이너가 동작 중인 파드의 이름과 같다.
  	hostname command로 알 수 있다.
  	- 컨테이너가 생성 될 때 함께 실행중이던 모든 서비스(resource?service?)목록은 해당 컨테이너에 환경 변수로 저장된다. This list is limited to services within the same namespace as the new Container's Pod and Kubernetes control plane services. (머선말이고?)

- workloads
  : k8s에서 구동되는 apps
  : 단일 혹은 다수의 component와 상관없이 pod내에서 실행된다.
  : pod는 cluster내에서 실행중인 container의 집합체이다

  k8s의 pod는 실행되고 나서 node에 오류가 발생하면 해당  node의 pod가 삭제된다. 이는 사용자가 향후 복구 되는 것과 상관 없이 pod를 새로 생성해야 한다.
  하지만 이런 workload를 관리 할 수 있도록 하는 workload resource를 이용 할 수 있다.

  - pod
    : k8s에서 생성하고 관리할 수 있는 가장 작은 단위. pod of whales와 같은 pod로 하나 이상의 컨테이너를 포함한 그룹이다.
    : 스토리지 네트워크 컨테이너 구동방식을 공유한다.
    : A Pod's contents are always co-located and co-scheduled, and run in a shared context.

    > single-pods : single container
    > single-pods : bultiple containers 
    > == 이러한 containers는 resourece를 함꼐 공유해야하는 밀접한 결합을 맺고있다. 함께 배치된 containers -> 하나의 결합된 service단위를 형성한다 
    > Rep`
    > workloadResource에 podTemplate을 사용하여 파드를 생성한다. 
    > 해당 yml 파일에서 pod를 생성하고 관리해주며 podTemplate를 수정하거나 새로 만들어도 이미 존재하는 파드에는 직접적인 영향을 끼치지 않는다.
    > workload의 podTemplate를 수정한경우에 해당 resource는 수정된 템플릿을 사용하는 대체 파드를 생성해야한다.
    > 파드의 갱신 및 교체는 자동으로 이루어진다.
    > 사용자가 가능 하지만 patch 및 replace와 같은 갱신 작업에는 제약이 따른다.
    > pod 내의 container는 각각 고유의 ip를 갖는다. 하지만 pod 내부에 있는 파드의 모든 contianer는 네트워크 네임스페이스를 공유하며 ip주소 / port 가 포함된다. 이에 속한 container는  localhost를 통해 네트워크가 가능하다. pod의 외부와 통신 할 때는 port와 같은 공유 네트워크를 사용해야한다.
    > 파드는 상대적으로 일시적이고 일회용 entity로 설계 되었다. 파드가 노드에 할당되면 파드는 중지되거나 종료될 떄까지 해당 노드에서 실행된다. 노드가 종료되면 해당 노드에 스케줄된 타임아웃 기간 후에 삭제된다. 노드가 실패하면 해당 파드도 삭제된다. volume과 같이 파드와 같은 lifetime을 가진 환경은 특정한 pod가 존재할 때만 존재함을 알 수 있다. 만약 pod가 삭제되고 동일한 대체 pod가 생성된다고 해도 이전의 pod과 관련된 모든 것들은 폐기되고 새로 생성된다. 
    > pod 상태값
    > pending
    > running
    > succeeded
    > failed
    > unknown

    	container 상태값 
    		waiting
    		Running
    		terminated

  - workloadResource

    > deployment
    > replicaset
    > replica pod의 집합 실행시간을 항상 유지하는 것.
    > 명시된 파드의 갯수에 대한 가용성을 보증 (??????????????????)
    > A ReplicaSet ensures that a specified number of pod replicas are running at any given time.
    > deployment.yml에 replicaset을 지정하면 이를 참조하여 생성 및 관리된다.


- services

  - 하나의 API
  - a method for exposing a network application that is running as one or more Pods in your cluster
  - 클러스터 내의 하나의 파드 집합이 다른 파드의 집합과 다를 수 있다. 이때 파드A가 파드B에 일부 기능을 제공하는 경우 파드B는 파드A과 어떻게 연결될것이며 연결 하기 위한 ip는 어떻게 찾아서 추적할 수 있는가 ? 그것을 제공 하는 것이 service이다. 
  - 각 서비스들은 pod의 endpoint를 정의하고 이에 접근 할 수 있는 policy를 가지고 있다. 
  - 서비스는 selector에 의해 결정된다. 

  > ingress
  > An API object that manages external access to the services in a cluster, typically HTTP.

  	=====================================
  	apiVersion: v1
  	kind: Pod
  	metadata:
  	  name: nginx
  	  labels:
  	    app.kubernetes.io/name: proxy
  	spec:
  	  containers:
  	  - name: nginx
  	    image: nginx:stable
  	    ports:
  	      - containerPort: 80
  	        name: http-web-svc
  	
  	---
  	apiVersion: v1
  	kind: Service
  	metadata:
  	  name: nginx-service
  	spec:
  	  selector:
  	    app.kubernetes.io/name: proxy
  	  ports:
  	  - name: name-of-service-port
  	    protocol: TCP
  	    port: 80
  	    targetPort: http-web-svc
  	=====================================
  	    :: targetPort는  port와  mapping 가능 
  	    :: service의 selector는 app.kubernetes.io/name이 같은 pod를 지속적으로 관찰한다
  	    :: "my-service"라는 endpoint를 가진다.
  	    :: service name== endpoint


  ​	
  <selector>가 선언하지 않은 경우
  ​	=====================================
  ​	apiVersion: v1
  ​	kind: Service
  ​	metadata:
  ​	  name: my-service
  ​	spec:
  ​	  ports:
  ​	    - protocol: TCP
  ​	      port: 80
  ​	      targetPort: 9376
  ​	

  	apiVersion: v1
  	=====================================
  	:: selector가 없으면 endpoint가 자동생성되지 않는다.
  	
  	---
  	apiVersion: discovery.k8s.io/v1
  	kind: EndpointSlice
  	metadata:
  	  name: my-service-1 # by convention, use the name of the Service as a prefix for the name of the EndpointSlice
  	  labels:
  	    # You should set the "kubernetes.io/service-name" label.
  	    # Set its value to match the name of the Service
  	    kubernetes.io/service-name: my-service
  	addressType: IPv4
  	ports:
  	  - name: '' # empty because port 9376 is not assigned as a well-known
  	             # port (by IANA)
  	    appProtocol: http
  	    protocol: TCP
  	    port: 9376
  	endpoints:
  	  - addresses:
  	      - "10.4.5.6" # the IP addresses in this list can appear in any order
  	      - "10.1.2.3"
  	=====================================
  	
  	> endpoint
  		- k8s API는 mapping되지 않은 pod의 endpoint의 porxying을 막는다. selector가 없는 서비스에 대해서는 kubectl proxy <service-name>과 같은 command가 실행되지 못하도록 되어있다. ( 위의 selector가 없을 때 endpoint를 생성하며 mapping시켜주는 단계가 꼭 필요하다는 것. )
  	> endpointslice
  		- endpoint들의 확장 개념 
  		- 네트워크의 endpoint를 분산 시킬 수 있다.
  		- Endpoints is a collection of endpoints that implement the actual service.
  		- The set of all endpoints is the union of all subsets.
  		- 
  		EndpointSubset is a group of addresses with a common set of ports. The expanded set of endpoints is the Cartesian product of Addresses x Ports. For example, given:
  	
  		{ Addresses: [{"ip": "10.10.1.1"}, {"ip": "10.10.2.2"}], Ports: [{"name": "a", "port": 8675}, {"name": "b", "port": 309}] }
  						--- endpoint ip---								  --endpoint hostname & port--
  		The resulting set of endpoints can be viewed as:
  	
  		a: [ 10.10.1.1:8675, 10.10.2.2:8675 ], b: [ 10.10.1.1:309, 10.10.2.2:309 ]*
  	
  	> port naming 
  		As with Kubernetes names in general, names for ports must only contain lowercase alphanumeric characters and -. Port names must also start and end with an alphanumeric character.
  		For example, the names 123-abc and web are valid, but 123_abc and -web are not.

  - service discovring
    Environment variables
    	환경 변수 설정 : pod가 nod에서 실행될 때 kubelet은 각 활성회된 서비스에 대해 환경 변수 세트를 추가한다.
    DNS
    	always should set up a DNS service using add-on
    	예를들어 namespace: my-ns / service: my-service 라면 DNS는 my-service.my-ns라는 recored를 만듦. 그러면 my-ns의 파드들은 이 이름을 추적하여 서비스를 찾는다. 다른 namespace에 있는 pod도 service를 이용하기 위해서 my-service.my-ns라는 이름을 써야 한다. 이 'my-service.my-ns'는 cluster ip가 할당되어 있다. 

- service publishing

  - service를 cluster 밖에 위치한 외부 ip로 노출 할 경우에 필요. ServiceType으로 원하는 서비스 종류를 지정할 수 있도록 해준다 (default==clusterIP)

  > Type
  > ClusterIP	: Cluster-internal IP노출.  클러스터 내에서만 서비스에 도달할 수 있다.
  > NodePort	: Expose the Service on each Node's IP at a static port(NodePort) / Nodeport 로 각 노드의 IP에 서비스 노출. 
  > 			  NodePort서비스가 라우팅 되는 CLusterIP서비스가 자동 생성되어 <nodeIP>:<nodePort>를 요청하면 클러스터'외부' 에서 서비스에 접속 할 수 있다.
  > 			   To make the node port available, Kubernetes sets up a cluster IP address, the same as if you had requested a Service of type: ClusterIP.
  > LoadBalaner	: cloud provider의 로드밸런서를 사용하여 서비스를 외부에 노출시킨다. 
  > ExternalName : 서비스의 externalName 필드에 CNAME 레코드 값을 맵핑한다. 어떠한 porxy도 설정되어 있지 않다.

  	> service publishing의 type을 Node로 했을 경우
  		1) --service-node-port-range로 지정된 범위 안에서 nodeport할당
  		2) 이 때 모든 node는 동일한 포트 번호를 사용
  		3) 각 노드는 이 port를 통해 service를 porxy
  		4) service는 할당된 nodeport를 .spec.port[*].nodePort 필드에 나타냄
  		5) 원하는 port값이 있다면 nodePort filed에 지정 가능 하지만 유효 범위 안에서 지정해야하고 이를 충족 못할 경우 API의 trx이 실패했다고 출력.
  		6) 원하는 ip 값이 있다면 ip block을 지정하여 flag를 입력하면 kube-proxy가 local node를 고려한  ip를 범위 안에서 할당한다.
  			For example, if you start kube-proxy with the --nodeport-addresses=127.0.0.0/8 flag, kube-proxy only selects the loopback interface for NodePort Services. The default for --nodeport-addresses is an empty list. This means that kube-proxy should consider all available network interfaces for NodePort. (That's also compatible with earlier Kubernetes releases.) ???????????????????????
  	> service publishing의 type을 LoadBalaner로 했을 경우
  		1)
  		--define
  		status:
  		  loadBalancer:
  		    ingress:
  		    - ip: 192.0.2.127
  		---
  		: 일부 cloud에서는 loadBalncerIP를 지정할 수 있도록 한다. 만약 해당  field가 비어있다면 임의로 loadBlanerIP를 생성한다. 
  		loadBlancerIP를 지정했지만 cloud에서 지원하지 않는 경우에는 이 요청은 무시된다.
  		: To implement a Service of type: LoadBalancer, Kubernetes typically starts off by making the changes that are equivalent to you requesting a Service of type: NodePort. The cloud-controller-manager component then configures the external load balancer to forward traffic to that assigned node port.
  	
  		2) with mixed protocol types
  			둘 이상의 port가 정해져 있을 때 모든 port는 같은 protocol을 가져야하며 이는 클라우드 공급자가 지원하는 protocol이어야 한다.
  		3) loadbaalncer에서는 nodeport 지정을 비활성화 할 수 있고 spec.allocateLoadBalancerNodePorts를 false로 지정하면 된다.
  			nodePort를 사용하지 않을때는 traffic을 직접  pods로 route하는 경우에만 사용 할 수 있다.
  			위의 값을 false로 지정했다고 해서 자동으로 해제 되는 것이 아니고 모든 service port에서 nodePorts항목을 제거해야한다.
  		4) Specifying class of load balncer
  			spec.loadbalancerclass를 설정하여 클라우드 제공자가 설정한 기본값 외의 로드 밸런서를 구현 할 수 있다.
  			spec.loadBalancerClass == nil 일경우 -> cloud-provide을 기본으로 구현하고 사용한다
  			spec.loadBalancerClass == "internal-vip" "example.com/internal-vip"으로 설정하면 지정class를 이용한다.
  	
  			단 한번 설정하면 변경 할 수 없고 위와 같은 접두사가 있는 레이블 스타일 식별자만 사용 가능하다.
  		5) 내부 load balancer를 사용 할 경우 cloud에서 지정한 protocol을 사용해야 한다. 
  	> service publishing의 type을 ExternalName 했을 경우
  		IP주소가 아닌 DNS 이름에 mapping된다.
  		ExternalName은 정식 canonical DNS 이름을 지정하여 구성된 이름을 허용한다. 

- 외부  ip
  service port에서  외부 ip를 사용하여 클러스터로 들어오는 traffic은 service의 endpoint중 하나로 라우팅 된다. 
  serviceType과 함께 지정될 수 있다. 

- namespace
  물리적인 클러스터는 하나이지만 논리적으로 (목적에따라) 나눠 지정하는 것을 namespace라 한다
  	장점 : 수많은 pod중 해당 namespace에 있는 정보만 모아서 정보를 볼 수 있다 -> 관리에 용이함.
  		   같은 API 다른 Pod
  		   여러 version을 분류하여 개발하는 경우 꼭 필요하다.
  	생성 : CLI / .yml 실행
  	namespace 종류 
  		기본적으로 4개의 namespace가 있다
  		Default		: namespace를 지정하는것 아닌 이상 default namespace에서 실행된다.
  		kube-public
  		kube-node-lease
  		kube-system




deployment, replicaset, pod, service namespace