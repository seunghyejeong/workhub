kube CLI

* get 
  object, resources의 목록 조회
* scale, diff, edit 
  배포된 app을 관리

kubectl apply -f 어쩌구 -n bami

1. kubectl get pods

2. kubectl get namespaces

3. kubectl create (-f .yml)

4. kubectl get pods -n ${namespace} 
   namespace를 지정 안했을 경우 default에 있는 pods를 가지고 온다.

5. kubectl api-resources
   쿠버네티스 cluster에서 사용할 수 있는 obejct 목록 조회

6. kubectl explain <type>
   k8s의 object 설명과 1레벨 속성들의 설명
   	- apiVersion, kind, metadata, status

7. kubectl explain <type>.<fieldName> 

8. kubectl scale -f <object-file-name> --replicas=#
   replicas의 배포 개수(pod갯수) 조정

9. kubectl diff -f <object-file-name>
   이전 배포와 다른점 추적

10. kubectl edit <type>/<name>
    object 의 spec을 변경

11. kubectl port-foward <type>/<name> <local-port>:<container-port>
    kubectl port-foward pod/nginx-deployment 8080:80
    실제 개발중 test용으로 portfoward 로 확인할때
    응답 : curl localhost:8080/version

12. kubectl attach <type>/<name> -c <containter-name>
    현재 실행중인 container의 proccess에 접속하여 log 확인

    1번창)kubectl port-forward pod/nginx-deployment-77fb5497c6-7bb4r 8080:80
    2번창)kubectl attach deployment/nginx-deployment -c nginx
    3번창)curl localhost:8080/version

13. kubectl logs <type>/<name> -c <container-name> -f
    현재 실행중인 container proccess의 모든 로그 출력 
    (-f :watch 모드)

14. kubectl apply -f 06_deployment.yaml
    .yaml파일을 통해 deployment 

15. kubectl get pod -o wide
    pod 실행 및 확인( output wide = 상세정보 확인 )

16. kubectl delete pod <pod-name>
    pod 종료

17. kubectl exec <pod-name> [-c <container-name>] --ifconfig eth0
    컨테이너 ip 확인

18. kubectl exec <pod-name> --env
    컨테이너 환경변수 확인

19. kubectl port-foward <pod-name> <host-port>:<container-port>
    포트포워딩 

20. kubectl get pod -o wide
    kubectl get pod/hello-app -o json
    Pod 실행 및 IP 확인

21. kubectl exec hello-app -- env
    컨테이너에 설정된 환경변수 확인 (env)

22. kubectl exec hello-app -- cat /etc/hosts
    컨테이너 IP 확인

23. kubectl exec hello-app -- netstat -an
    컨테이너가 리스닝하고 있는 포트 확인

24. kubectl get pod red-app -n bami -o jsonpath="{.status.podIP}" == kubectl get pod red-app -o wide
    pod ip 조회
    ----- kubectl 이하 k----

25. k get rs <replicasetName> -o wide
    replicaset 생성 결과 조회

26. k get pod -o wide

27. k describe rs <replicasetName> 
    rs의 pod 생성 과정 확인

28. k get evnets 
    pod 생성 이후의 replicaset의 모든 생성 과정 확인

29. kubectl exec blue-green-app -n bami -c blue-app -- crul -vs localhost:8081/tree
    ( 내부 통신 )

30. kubectl exec red-app -n bami -c red-app -- curl -vs $BLUE_GREEN_POD_IP:8080/rose
    ( 외부 통신 )

31. kubectl delete rs blue-replicaset 
    rs 삭제 (pod도 함꼐 삭제됨)

32. kubectl delete rs blue-replicaset --cascade=odrphan
    pod만 남겨두고 rs만 삭제되는 개념

33. 위의 방법 외에도 
    k scale rs/<replicaSetname> --replicas 0 :: replicas를 0개로 만들어준후
    k delete rs/<replicaSetname> 을 해주면 좀더 안전한 삭제가 된다.

34. pod의 replicaset 정보 알아보기
    k get pod <podname> -o jsonpath="{.metadata.ownerReferences[0].name"
    k get pod -o yaml <podname>

35. 레이블 조회
    kubectl get pod --show-labels

36. k scale rs <replicaSetname> --replicas=3
    replicas 개수 조정

37. k set image rs/<rsName> <이전pod의Container>:<newContainername>
    <rsName>에서 실행중인 <이전pod의Container>를 <newContainername>로 변경한다. 
    실행중인 Rs는 변경되지 않고 속한 pod의 template만 바꿈

38. k get pod -L <label_NAME>
    label name을 포함한 출력

39. k status rollout deployment/<D-Name>
    rollout되는 상태 deployment 배포 진행/완료 상태 확인

40. k scale deployment <deployument-name> --replicas=<number-of-pod>
    Deployment의 pod replicas 변경

41. k get rs- w
    replicaset의 상태 변화 확인 

42. k delete all -l <lagbel-key>=<label-value>
    label로 배포한 resource 삭제 

43. k get endpoints 
    enpoint resource 확인

44. k get all -n namespace
    namespace에 선언 및 생성된 모든  Resource 확인

45. k get svc(service 약자) <servicename>

46. k get svc -o jsonpath="{.spec.clusterIP}"
    clusterIP 조회 
    == k get svc <pod name> -o wide -n <namespace>

47. k get svc order -o jsonpath -n bami
    json형태로 정보 보기 

48. k exec order-79445bdb56-2jkb4 -n bami -- env | grep PAYMENT
    횐경변수 처리 된 ip 찾기 

49. k get ingress podname -n namespace

50. kube exec -it {podname} -c {containername} -n {namespace} -- sh
    배포된 pod 안으로 접속

kubectl get sc paasta-cp-storageclass -o yaml

51. k약어


source <(kubectl completion bash)
echo "source <(kubectl completion bash)"
alias k=kubectl
complete -o default -F __start_kubectl k


echo "alias ll='ls --color=auto -alF'" >> ~/.bashrc
source ~/.bashrc