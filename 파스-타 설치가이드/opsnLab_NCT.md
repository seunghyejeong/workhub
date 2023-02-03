ncloud domain : paas-ta-dev09.kr(175.45.210.61)
스마일서브 도메인 정보 
https://www.iwinv.kr/account/domain.html
paasta / paasta0812!

paas-ta-dev09.kr 

$ export SYSTEM_DOMAIN=sys.paas-ta-dev09.kr
$ export APPS_DOMAIN=apps.sys.paas-ta-dev09.kr
$ echo $SYSTEM_DOMAIN
$ echo $APPS_DOMAIN
$ echo "*.$SYSTEM_DOMAIN"
$ echo "*.$APPS_DOMAIN"



https://www.iwinv.kr/account/domain.html
CONSOLE
DOMAIN
DOMAIN&DNS 관리
DOMAIN 등록 paas-ta-dev09.kr
레코드 등록


SYS:
docker run -it --rm \
-v '/root/system-cert:/etc/letsencrypt' \
-v '/root/system-cert:/var/lib/letsencrypt' certbot/certbot certonly -d "*.$SYSTEM_DOMAIN" --manual --preferred-challenges dns --key-type rsa --server https://acme-v02.api.letsencrypt.org/directory --register-unsafely-without-email

APPS:SYS
docker run -it --rm \
 -v '/root/system-cert:/etc/letsencrypt' \
 -v '/root/system-cert:/var/lib/letsencrypt' \
 certbot/certbot certonly -d "*.$APPS_DOMAIN" --manual --preferred-challenges dns --key-type rsa --server https://acme-v02.api.letsencrypt.org/directory --register-unsafely-without-email

--rsa 추가

SYS 출력:
DNS TXT record name:
_acme-challenge.sys.paas-ta-dev09.kr
DNS TXT record value: bYjIIM9i4FYTFBqTT326YCVVzd-7Xbc2aoyHW5zKVJE

dig 확인 
https://toolbox.googleapps.com/apps/dig/#TXT/

APPS.SYS출력:
DNS TXT record name: _acme-challenge.apps.sys.paas-ta-dev09.kr
DNS TXT record value: nGgoOJN1weFT0OOGD7A7He_9K1mILyPwhW-nuGcoDtQ


export DOMAIN=sys.paas-ta-dev09.kr

curl -O https://kr.object.gov-ncloudstorage.com/vpaasta/
traefik.me/isrgrootx1.pem


cat isrgrootx1.pem ~/system-cert/archive/${DOMAIN}/fullchain1.pem
cat ~/system-cert/archive/sys.paas-ta-dev09.kr/cert1.pem
cat ~/system-cert/archive/${DOMAIN}/privkey1.pem

export DOMAIN=apps.sys.paas-ta-dev09.kr
cat isrgrootx1.pem ~/${DOMAIN}/archive/${DOMAIN}/fullchain1.pem
cat ~/system-cert/archive/${DOMAIN}/cert1.pem
cat ~/system-cert/archive/${DOMAIN}/privkey1.pem


root 계정:
chim@gwangju.ac.kr
rhdkwn1234%


API 인증키 관리:
CUSTOMER
- Access Key ID: JswkqxqkfzrTiLP7e4qn
- Secret Key: h5FFjQtlmmgXgUXl70AW3a5tp9PpV1RRbKG16Y8G

Domain:
paas-ta-dev09.kr
subDomain:
0
DB INFO:
DB Service Name: paasta-db-2023-lqv
DB Server Name: paasta-db-2023
User Name: paasta2023
Password: paasta2023!
DB Name: paasta2023


https://uaa.sys.paas-ta-dev09.kr/passcode
2fS1bVJiza 

cf login -a api.sys.paas-ta-dev09.kr --sso


cf create-service nks 10 nks-sample -c '{
    "name": "cluster-sample",
    "k8sVersion": "1.23.9-nks.1",
    "subnetNoList": "13633",
    "publicNetwork": true,
    "subnetLbNo": 13635,
    "loginKeyName": "ncp-paasta-c3-gju-03-key",
    "defaultNodePool.name": "dnp-sample",
    "defaultNodePool.nodeCount": 1,
    "defaultNodePool.productCode": "SVR.VSVR.STAND.C002.M008.NET.SSD.B050.G002",
    "log.audit": true,
    "nodePool": "[{\"name\":\"npsample\",\"productCode\":\"SVR.VSVR.STAND.C002.M008.NET.SSD.B050.G002\",\"nodeCount\":2}]"

}'

nodePool 은 string으로 받아야함 "" 가 없으면 배열로 전송되기때문에 ""를 붙여줘야함.

 loginKeyName   : login pem key name
                    ncp-paasta-c3-gju-03-key
 k8sVersion     : ncp console 
                    1.23.9-nks.1
 subnetNoList[] : ncp console
                    13633
 subnetLbNo     : ncp console
                    13635


export NCLOUD_ACCESS_KEY=JswkqxqkfzrTiLP7e4qn
export NCLOUD_SECRET_KEY=h5FFjQtlmmgXgUXl70AW3a5tp9PpV1RRbKG16Y8G
export NCLOUD_API_GW=https://ncloud.apigw.gov-ntruss.com



경로가 모두 root로 잡혀서 %PATH를  /home1이 아닌 root로 바꾸어서 실행.

mkdir -p /root/bin && cp ./ncp-iam-authenticator /root/bin/ncp-iam-authenticator && export PATH=$PATH:/root/bin

echo 'export PATH=$PATH:$HOME/bin' >> ~/.bash_profile

ncp-iam-authenticator:
SHA256(ncp-iam-authenticator)= 1d2d829879d9ea6be684f7a45035604a3143d3a6eb8e5b8047ea42bc9a01e84b

ncp-iam-authenticator create-kubeconfig --region KR --clusterUuid c035650b-85c5-4cef-be11-fb27d918727e --output kubeconfig.yaml


kubectl --kubeconfig kubeconfig.yaml get nodes



cf create-service cdbmysql HA mysql-sample -c '{
"cloudMysqlServiceName": "cdb-mysql",
"cloudMysqlServerNamePrefix": "cdb-mysql",
"cloudMysqlUserName": "paasta2023",
"cloudMysqlUserPassword":"paasta2023!",
"hostIp":"%",
"cloudMysqlDatabaseName":"cdb-name-paasta"
}'


cf bind-service test-node-app mysql-sample
