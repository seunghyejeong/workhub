# edu-msa-master로 CICD 구축 (2) argocd

### enviroment 
---
Ubuntu 22.04
CI: Jenkins 
CD: ArgoCD & GitOps
code analylist: SonarQube
---


### reference

[jenkins-docs](https://www.jenkins.io/doc/book/installing/kubernetes/) for more information.
[install-docker-in-k8s](https://devbksheen.tistory.com/entry/Jenkins%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Docker-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%9E%90%EB%8F%99-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0Blue-Ocean-NCP) for more information.
[overall-flow-of-msa-deployment](https://jenakim47.tistory.com/74) for more information.
[image](./msa-project-workflow.png)


## install argocd
__Step 1__ create namespace & deployment .yaml
`kubectl create namespace argocd`
`kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml`

__Step 2__ install argocd CLI
`curl -sSL -o argocd-linux-amd64 https://github.com/argoproj/argo-cd/releases/latest/download/argocd-linux-amd64`
`sudo install -m 555 argocd-linux-amd64 /usr/local/bin/argocd`
`rm argocd-linux-amd64`

__Step 3__ 외부 노출을 위한 type 변경
`kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'`

__Step 4__ argocd login
`argocd admin initial-password -n argocd`
```console
PASSWORD
```

__Step 5__ argocd ui login
- 초기 id/pw는 admin/admin
`export ARGOCD_OPTS='--port-forward-namespace argocd'`
`argocd login ${NODE_IP}:${NODE_PORT}`
argocd login 10.101.0.237:31874 

## create app
- argocd로 관리하는 repository를 app이라 말함
- argocd가 형상관리 할 git repository를 기반으로 app을 생성한다.
- argocd는 각 디렉토리의 ./kuberentes/*.yaml를 참조한다.

`argocd app create edu-msa-board --repo https://github.com/seunghyejeong/gitops-repository.git --path edu-msa-board-master/kubernetes --dest-server https://kubernetes.default.svc --dest-namespace default`


