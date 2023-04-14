gju - openlab 기술지원

openlab-01.kr



	서버접속 인증키 / 비밀번호

공유드라이브 -> 각대학별 pem key


	공인 ip 

175.45.209.219

	비밀번호

D8T+L6BJ9N?d

	user

ncloud



curl https://releases.rancher.com/install-docker/20.10.sh | sh

sys.openlab-01.kr
apps.sys.openlab-01.kr


sys.openlab-06.kr
apps.sys.openlab-06.kr
 210.90.173.188

sys.openlab-07.kr
apps.sys.openlab-07.kr
 210.90.173.190




	 사이트 -> 도메인 등록 -> 네임서버 설정

https://domain.whois.co.kr/ns_service/service.php


	root로 진행	

export SYSTEM_DOMAIN=sys.openlab-01.kr
export APPS_DOMAIN=apps.sys.openlab-01.kr
echo $SYSTEM_DOMAIN
echo $APPS_DOMAIN
echo "*.$SYSTEM_DOMAIN"
echo "*.$APPS_DOMAIN"


mkdir -p system-cert apps-ce

	SYS:

docker run -it --rm \
-v '/root/system-cert:/etc/letsencrypt' \
-v '/root/system-cert:/var/lib/letsencrypt' certbot/certbot certonly -d "*.$SYSTEM_DOMAIN" --manual --preferred-challenges dns --key-type rsa --server https://acme-v02.api.letsencrypt.org/directory --register-unsafely-without-email

Yse-> 도메인 홈페이지-> txt nameserver 등록 -> enter

	text record 등록

_acme-challenge.sys.openlab-01.kr
tqx0vn7zbbp7jviWTwkItXeSbVUA17mQQ4ck3uvH9do


	APPS:SYS

docker run -it --rm \
 -v '/root/system-cert:/etc/letsencrypt' \
 -v '/root/system-cert:/var/lib/letsencrypt' \
 certbot/certbot certonly -d "*.$APPS_DOMAIN" --manual --preferred-challenges dns --key-type rsa --server https://acme-v02.api.letsencrypt.org/directory --register-unsafely-without-email


 text record 등록
_acme-challenge.apps.sys.openlab-01.kr.
v0IQhO9ILSpsK7uAFyUueEAQn6x7Wx1UAdsX6-1FXDc


export DOMAIN=sys.openlab-01.kr
echo $DOMAIN

	인증서 등록

cd ~/system-cert/archive/sys.openlab-01.kr
curl -O https://kr.object.gov-ncloudstorage.com/vpaasta/traefik.me/isrgrootx1.pem


	ncp console 등록

cat isrgrootx1.pem ~/system-cert/archive/${DOMAIN}/fullchain1.pem
	

- fullchain
  -----BEGIN CERTIFICATE-----
  MIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw
  TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
  cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4
  WhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu
  ZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY
  MTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc
  h77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+
  0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U
  A5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW
  T8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH
  B5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC
  B5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv
  KBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn
  OlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn
  jh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw
  qHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI
  rU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV
  HRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq
  hkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL
  ubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ
  3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK
  NFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5
  ORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur
  TkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC
  jNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc
  oyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq
  4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA
  mRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d
  emyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=
  -----END CERTIFICATE-----

-----BEGIN CERTIFICATE-----
MIIFKzCCBBOgAwIBAgISBI6e1AeU1VRSaGTf9IWkap67MA0GCSqGSIb3DQEBCwUA
MDIxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQD
EwJSMzAeFw0yMzA0MDUwMTIwNTBaFw0yMzA3MDQwMTIwNDlaMB4xHDAaBgNVBAMM
Eyouc3lzLm9wZW5sYWItMDEua3IwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK
AoIBAQDwnkkYlJ7RQRERcbMpKvazOiQnWTh0bA14NxwzVPdSakzRkBeJhK3Eaw5W
eBzXF6A9NwTjYJDGZtyYpe6VawU6dNwr2aBvIImgpnHDrcB1cRj6/yKIY3cBU97+
m1V1ZgVH/u/Ve8Y+eIxT9API0go7i2CGLFz3VDXiZT1d7phlgfLZIH0gQXPCPe6r
LRTMOjoyw6KiV+eE5TaqK76Y4U2AkZYdJRtnKGHk0THD/VTbQY4j8BMb7Jdwq/dK
e8zALlR+jJQtCUAhP0SS4PwpNw1UGJUfSBrNPn5cARNxwXUhUuBYkpFpHnjhc/sC
GSo/7P9sl+P75yarCBWAoM0wNj1bAgMBAAGjggJNMIICSTAOBgNVHQ8BAf8EBAMC
BaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQCMAAw
HQYDVR0OBBYEFPVw6GhkWwptVX5XUIx50W+53FWbMB8GA1UdIwQYMBaAFBQusxe3
WFbLrlAJQOYfr52LFMLGMFUGCCsGAQUFBwEBBEkwRzAhBggrBgEFBQcwAYYVaHR0
cDovL3IzLm8ubGVuY3Iub3JnMCIGCCsGAQUFBzAChhZodHRwOi8vcjMuaS5sZW5j
ci5vcmcvMB4GA1UdEQQXMBWCEyouc3lzLm9wZW5sYWItMDEua3IwTAYDVR0gBEUw
QzAIBgZngQwBAgEwNwYLKwYBBAGC3xMBAQEwKDAmBggrBgEFBQcCARYaaHR0cDov
L2Nwcy5sZXRzZW5jcnlwdC5vcmcwggEDBgorBgEEAdZ5AgQCBIH0BIHxAO8AdgB6
MoxU2LcttiDqOOBSHumEFnAyE4VNO9IrwTpXo1LrUgAAAYdPN4MWAAAEAwBHMEUC
IDAWuPLGp+3Psk0s/WRo9UTXnX8CvG70stCf9qyjiOcAAiEA7EFLlfDOiWV2MxrQ
aN1et8riXFa3gzE0k+wnmB/LjOQAdQDoPtDaPvUGNTLnVyi8iWvJA9PL0RFr7Otp
4Xd9bQa9bgAAAYdPN4MUAAAEAwBGMEQCIBTXYj6p8arKQYASbCsRW3uBXXrFP9eF
K2r+yo39dOIMAiBRwgrjF2bYhAY+qACGK3QYm2Zsa8oDY44RLe9L8N71tzANBgkq
hkiG9w0BAQsFAAOCAQEATL3eFapfZqYaSoohE32stYQXRBcMSlhOrqk3XxCeDBrG
i1YNf7unFyp0S2YPBMrcX65htB/Y3dRwIkePWcWY+Oen4QXH8dQygJWVJ6MfH5DU
wO87UCzC79y2llEURoY8knbUgIcx3tEKmjUJUQdfCG2MDf8yLUuPJ3QsHOm8EQmE
ZwQJB1PciGP1bfqUgUxIxfvBKOgfbsMoSEYqDoi7g5tUG+JmfpJkZFXIfG+XwLoe
A+HjvqoDLGUNhwHjUhnJY49VneLJRGvS3CSFUyVw7Z4GAevm1cEyHWLsnmmczBwe
+muyvx5tDChA0n3JOOj7R4rMI6Ib/qkTutqj/PGZfw==
-----END CERTIFICATE-----
-----BEGIN CERTIFICATE-----
MIIFFjCCAv6gAwIBAgIRAJErCErPDBinU/bWLiWnX1owDQYJKoZIhvcNAQELBQAw
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMjAwOTA0MDAwMDAw
WhcNMjUwOTE1MTYwMDAwWjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3Mg
RW5jcnlwdDELMAkGA1UEAxMCUjMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK
AoIBAQC7AhUozPaglNMPEuyNVZLD+ILxmaZ6QoinXSaqtSu5xUyxr45r+XXIo9cP
R5QUVTVXjJ6oojkZ9YI8QqlObvU7wy7bjcCwXPNZOOftz2nwWgsbvsCUJCWH+jdx
sxPnHKzhm+/b5DtFUkWWqcFTzjTIUu61ru2P3mBw4qVUq7ZtDpelQDRrK9O8Zutm
NHz6a4uPVymZ+DAXXbpyb/uBxa3Shlg9F8fnCbvxK/eG3MHacV3URuPMrSXBiLxg
Z3Vms/EY96Jc5lP/Ooi2R6X/ExjqmAl3P51T+c8B5fWmcBcUr2Ok/5mzk53cU6cG
/kiFHaFpriV1uxPMUgP17VGhi9sVAgMBAAGjggEIMIIBBDAOBgNVHQ8BAf8EBAMC
AYYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMBIGA1UdEwEB/wQIMAYB
Af8CAQAwHQYDVR0OBBYEFBQusxe3WFbLrlAJQOYfr52LFMLGMB8GA1UdIwQYMBaA
FHm0WeZ7tuXkAXOACIjIGlj26ZtuMDIGCCsGAQUFBwEBBCYwJDAiBggrBgEFBQcw
AoYWaHR0cDovL3gxLmkubGVuY3Iub3JnLzAnBgNVHR8EIDAeMBygGqAYhhZodHRw
Oi8veDEuYy5sZW5jci5vcmcvMCIGA1UdIAQbMBkwCAYGZ4EMAQIBMA0GCysGAQQB
gt8TAQEBMA0GCSqGSIb3DQEBCwUAA4ICAQCFyk5HPqP3hUSFvNVneLKYY611TR6W
PTNlclQtgaDqw+34IL9fzLdwALduO/ZelN7kIJ+m74uyA+eitRY8kc607TkC53wl
ikfmZW4/RvTZ8M6UK+5UzhK8jCdLuMGYL6KvzXGRSgi3yLgjewQtCPkIVz6D2QQz
CkcheAmCJ8MqyJu5zlzyZMjAvnnAT45tRAxekrsu94sQ4egdRCnbWSDtY7kh+BIm
lJNXoB1lBMEKIq4QDUOXoRgffuDghje1WrG9ML+Hbisq/yFOGwXD9RiX8F6sw6W4
avAuvDszue5L3sz85K+EC4Y/wFVDNvZo4TYXao6Z0f+lQKc0t8DQYzk1OXVu8rp2
yJMC6alLbBfODALZvYH7n7do1AZls4I9d1P4jnkDrQoxB3UqQ9hVl3LEKQ73xF1O
yK5GhDDX8oVfGKF5u+decIsH4YaTw7mP3GFxJSqv3+0lUFJoi5Lc5da149p90Ids
hCExroL1+7mryIkXPeFM5TgO9r0rvZaBFOvV2z0gp35Z0+L4WPlbuEjN/lxPFin+
HlUjr8gRsI3qfJOQFy/9rKIJR0Y/8Omwt/8oTWgy1mdeHmmjk7j1nYsvC9JSQ6Zv
MldlTTKB3zhThV1+XWYp6rjd5JW1zbVWEkLNxE7GJThEUG3szgBVGP7pSWTUTsqX
nLRbwHOoq7hHwg==
-----END CERTIFICATE-----
-----BEGIN CERTIFICATE-----
MIIFYDCCBEigAwIBAgIQQAF3ITfU6UK47naqPGQKtzANBgkqhkiG9w0BAQsFADA/
MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT
DkRTVCBSb290IENBIFgzMB4XDTIxMDEyMDE5MTQwM1oXDTI0MDkzMDE4MTQwM1ow
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwggIiMA0GCSqGSIb3DQEB
AQUAA4ICDwAwggIKAoICAQCt6CRz9BQ385ueK1coHIe+3LffOJCMbjzmV6B493XC
ov71am72AE8o295ohmxEk7axY/0UEmu/H9LqMZshftEzPLpI9d1537O4/xLxIZpL
wYqGcWlKZmZsj348cL+tKSIG8+TA5oCu4kuPt5l+lAOf00eXfJlII1PoOK5PCm+D
LtFJV4yAdLbaL9A4jXsDcCEbdfIwPPqPrt3aY6vrFk/CjhFLfs8L6P+1dy70sntK
4EwSJQxwjQMpoOFTJOwT2e4ZvxCzSow/iaNhUd6shweU9GNx7C7ib1uYgeGJXDR5
bHbvO5BieebbpJovJsXQEOEO3tkQjhb7t/eo98flAgeYjzYIlefiN5YNNnWe+w5y
sR2bvAP5SQXYgd0FtCrWQemsAXaVCg/Y39W9Eh81LygXbNKYwagJZHduRze6zqxZ
Xmidf3LWicUGQSk+WT7dJvUkyRGnWqNMQB9GoZm1pzpRboY7nn1ypxIFeFntPlF4
FQsDj43QLwWyPntKHEtzBRL8xurgUBN8Q5N0s8p0544fAQjQMNRbcTa0B7rBMDBc
SLeCO5imfWCKoqMpgsy6vYMEG6KDA0Gh1gXxG8K28Kh8hjtGqEgqiNx2mna/H2ql
PRmP6zjzZN7IKw0KKP/32+IVQtQi0Cdd4Xn+GOdwiK1O5tmLOsbdJ1Fu/7xk9TND
TwIDAQABo4IBRjCCAUIwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYw
SwYIKwYBBQUHAQEEPzA9MDsGCCsGAQUFBzAChi9odHRwOi8vYXBwcy5pZGVudHJ1
c3QuY29tL3Jvb3RzL2RzdHJvb3RjYXgzLnA3YzAfBgNVHSMEGDAWgBTEp7Gkeyxx
+tvhS5B1/8QVYIWJEDBUBgNVHSAETTBLMAgGBmeBDAECATA/BgsrBgEEAYLfEwEB
ATAwMC4GCCsGAQUFBwIBFiJodHRwOi8vY3BzLnJvb3QteDEubGV0c2VuY3J5cHQu
b3JnMDwGA1UdHwQ1MDMwMaAvoC2GK2h0dHA6Ly9jcmwuaWRlbnRydXN0LmNvbS9E
U1RST09UQ0FYM0NSTC5jcmwwHQYDVR0OBBYEFHm0WeZ7tuXkAXOACIjIGlj26Ztu
MA0GCSqGSIb3DQEBCwUAA4IBAQAKcwBslm7/DlLQrt2M51oGrS+o44+/yQoDFVDC
5WxCu2+b9LRPwkSICHXM6webFGJueN7sJ7o5XPWioW5WlHAQU7G75K/QosMrAdSW
9MUgNTP52GE24HGNtLi1qoJFlcDyqSMo59ahy2cI2qBDLKobkx/J3vWraV0T9VuG
WCLKTVXkcGdtwlfFRjlBz4pYg1htmf5X6DYO8A4jqv2Il9DjXA6USbW1FzXSLr9O
he8Y4IWS6wY7bCkjCWDcRQJMEhg76fsO3txE+FiYruq9RUWhiF1myv4Q6W+CyBFC
Dfvp7OOGAN6dEOM4+qR9sdjoSYKEBpsr6GtPAQw4dy753ec5
-----END CERTIFICATE-----


cat ~/system-cert/archive/${DOMAIN}/cert1.pem
-----BEGIN CERTIFICATE-----
MIIFKzCCBBOgAwIBAgISBI6e1AeU1VRSaGTf9IWkap67MA0GCSqGSIb3DQEBCwUA
MDIxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQD
EwJSMzAeFw0yMzA0MDUwMTIwNTBaFw0yMzA3MDQwMTIwNDlaMB4xHDAaBgNVBAMM
Eyouc3lzLm9wZW5sYWItMDEua3IwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK
AoIBAQDwnkkYlJ7RQRERcbMpKvazOiQnWTh0bA14NxwzVPdSakzRkBeJhK3Eaw5W
eBzXF6A9NwTjYJDGZtyYpe6VawU6dNwr2aBvIImgpnHDrcB1cRj6/yKIY3cBU97+
m1V1ZgVH/u/Ve8Y+eIxT9API0go7i2CGLFz3VDXiZT1d7phlgfLZIH0gQXPCPe6r
LRTMOjoyw6KiV+eE5TaqK76Y4U2AkZYdJRtnKGHk0THD/VTbQY4j8BMb7Jdwq/dK
e8zALlR+jJQtCUAhP0SS4PwpNw1UGJUfSBrNPn5cARNxwXUhUuBYkpFpHnjhc/sC
GSo/7P9sl+P75yarCBWAoM0wNj1bAgMBAAGjggJNMIICSTAOBgNVHQ8BAf8EBAMC
BaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQCMAAw
HQYDVR0OBBYEFPVw6GhkWwptVX5XUIx50W+53FWbMB8GA1UdIwQYMBaAFBQusxe3
WFbLrlAJQOYfr52LFMLGMFUGCCsGAQUFBwEBBEkwRzAhBggrBgEFBQcwAYYVaHR0
cDovL3IzLm8ubGVuY3Iub3JnMCIGCCsGAQUFBzAChhZodHRwOi8vcjMuaS5sZW5j
ci5vcmcvMB4GA1UdEQQXMBWCEyouc3lzLm9wZW5sYWItMDEua3IwTAYDVR0gBEUw
QzAIBgZngQwBAgEwNwYLKwYBBAGC3xMBAQEwKDAmBggrBgEFBQcCARYaaHR0cDov
L2Nwcy5sZXRzZW5jcnlwdC5vcmcwggEDBgorBgEEAdZ5AgQCBIH0BIHxAO8AdgB6
MoxU2LcttiDqOOBSHumEFnAyE4VNO9IrwTpXo1LrUgAAAYdPN4MWAAAEAwBHMEUC
IDAWuPLGp+3Psk0s/WRo9UTXnX8CvG70stCf9qyjiOcAAiEA7EFLlfDOiWV2MxrQ
aN1et8riXFa3gzE0k+wnmB/LjOQAdQDoPtDaPvUGNTLnVyi8iWvJA9PL0RFr7Otp
4Xd9bQa9bgAAAYdPN4MUAAAEAwBGMEQCIBTXYj6p8arKQYASbCsRW3uBXXrFP9eF
K2r+yo39dOIMAiBRwgrjF2bYhAY+qACGK3QYm2Zsa8oDY44RLe9L8N71tzANBgkq
hkiG9w0BAQsFAAOCAQEATL3eFapfZqYaSoohE32stYQXRBcMSlhOrqk3XxCeDBrG
i1YNf7unFyp0S2YPBMrcX65htB/Y3dRwIkePWcWY+Oen4QXH8dQygJWVJ6MfH5DU
wO87UCzC79y2llEURoY8knbUgIcx3tEKmjUJUQdfCG2MDf8yLUuPJ3QsHOm8EQmE
ZwQJB1PciGP1bfqUgUxIxfvBKOgfbsMoSEYqDoi7g5tUG+JmfpJkZFXIfG+XwLoe
A+HjvqoDLGUNhwHjUhnJY49VneLJRGvS3CSFUyVw7Z4GAevm1cEyHWLsnmmczBwe
+muyvx5tDChA0n3JOOj7R4rMI6Ib/qkTutqj/PGZfw==
-----END CERTIFICATE-----


cat ~/system-cert/archive/${DOMAIN}/privkey1.pem
-----BEGIN RSA PRIVATE KEY-----
MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDwnkkYlJ7RQRER
cbMpKvazOiQnWTh0bA14NxwzVPdSakzRkBeJhK3Eaw5WeBzXF6A9NwTjYJDGZtyY
pe6VawU6dNwr2aBvIImgpnHDrcB1cRj6/yKIY3cBU97+m1V1ZgVH/u/Ve8Y+eIxT
9API0go7i2CGLFz3VDXiZT1d7phlgfLZIH0gQXPCPe6rLRTMOjoyw6KiV+eE5Taq
K76Y4U2AkZYdJRtnKGHk0THD/VTbQY4j8BMb7Jdwq/dKe8zALlR+jJQtCUAhP0SS
4PwpNw1UGJUfSBrNPn5cARNxwXUhUuBYkpFpHnjhc/sCGSo/7P9sl+P75yarCBWA
oM0wNj1bAgMBAAECggEAYY/8mW/heX1Fe2o7t8yI3SEM+btubZ5GU+EcmR+xbIRX
g31GXecjQgoadSscnoYg3dFKNRu5eAjLF0XomEMdipdJkqoaKBTsWOxEIpB4AeaK
t3LtwJl77ljgiSHYo9Jq/8c53bjjJ/9e/sdpJWss5PndeGnShCJr+tEL8eNFfZpK
5Zoa56WEV486xjnpOQGEJZMNM2nym+2B2bzG8QzX4wzw7BbJuj8Sl2a66VyDT+jP
cwB2eTEa5zuH7im0MrF0KjkyXkhFexkP5nsBZZLbSr8//qXX4DExx7tH5aS55+DB
aCQ+PEz8gjG7yhtHJd3H8jNnUlVz2aoh0mbFdtZKgQKBgQD5CPeqylJ549VuWJ8H
me9Qr9B3bEbIMyBQ6PpYliiYScCfBCp9+n3jiDiP0h9Fr8b57JXI0AKalCisoenX
c9yq0LdeN0Yu6KxZajtiK1X+tV6togHrQBG0mKUBLuKYdAmceqlsrzW8oojzR3IP
TLPIXyDxhr0uDxB8JRJaSt5lmwKBgQD3WQ5pDzoIjM3YlVR+or3GYrt/O6mnmdRB
hjVj1cWoBsOruN1tGc6pHLOVxsB8jGJp8drjRBX0pmyfI/uvpEKRZS47PBeshkjr
zIxcr5762u61tli50OP50UK85/giy7kGR1jJUKq72VsXVmSK6cX8i+VRJD3ryRUm
puqPKlLjQQKBgQCkwbgI+D3EgmUL0fm0b7USbp6+w5W4kYqJvPkTbpSA3BphmcH7
AqQSymaRT/R8iCRd6JXX+zYdynN2ctBgSDjvkZIe9ParxidwKIYNFpqYHqn6uboH
R6XvEyXnOHuVXP+W1NgOHYSxvd5ZBSWC1b4DjFwhLoEuXa0MVQJlVi74jQKBgQDi
ISXegJAyvt918Ve1Cn1rAFqgAGCjHCyD1vm6kvZIHh8HQFQW6TAZRWcdN/5EH2fd
qcHOYjGcYAYQG6p+sjI1kIYKm26dSIskH/X2yowSlNMFh3ZoMoQpoNldsMkWrp8y
EUonijr8z8LKfC4hHYXm09LLMtH1XPmcQ59PUYdDwQKBgQDfDvqqAVqxxxahK7Jj
z7HI73Ox0eg35oYRYvm+11SIAQAyAnKGCopDZl1zk3FvEGFqVkcYJD2mXTtJ6ttY
COU1uOOyMRCxLLGyFpyT2M7XaN0E+N2kTf824uiERlLYPbis8rQ3/NmAO2ndtdRa
dxJEf9KN10H4B/BIHDDqcn1Jqg==
-----END PRIVATE KEY-----






cd ~/system-cert/archive/apps.sys.openlab-01.kr
curl -O https://kr.object.gov-ncloudstorage.com/vpaasta/traefik.me/isrgrootx1.pem


export DOMAIN=app.sys.openlab-01.kr
cat isrgrootx1.pem ~/system-cert/archive/${DOMAIN}/fullchain1.pem

-----BEGIN CERTIFICATE-----
MIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4
WhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu
ZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY
MTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc
h77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+
0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U
A5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW
T8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH
B5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC
B5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv
KBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn
OlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn
jh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw
qHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI
rU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV
HRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq
hkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL
ubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ
3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK
NFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5
ORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur
TkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC
jNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc
oyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq
4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA
mRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d
emyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=
-----END CERTIFICATE-----

-----BEGIN CERTIFICATE-----
MIIFNTCCBB2gAwIBAgISA3PzV3XxkfLZipYT1Jne/ze/MA0GCSqGSIb3DQEBCwUA
MDIxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQD
EwJSMzAeFw0yMzA0MDUwMTIzMzNaFw0yMzA3MDQwMTIzMzJaMCMxITAfBgNVBAMM
GCouYXBwcy5zeXMub3BlbmxhYi0wMS5rcjCCASIwDQYJKoZIhvcNAQEBBQADggEP
ADCCAQoCggEBALqWRI9lHtZglr4c4kB9cqYwoI541tukHtAC3v8Wo7S5V7y/Mcf1
y+Vg7zoDAlspr20gEEo7/0kfPO5tFPZCAufUVygsH3g6Vfkm3GMyyen3NckJ/sjI
vPbSpgpf+DmCOZH2H7rvOVxbSkcznKra2yHdx5m/juJpO/ukedEEJQdSXxiX0x1R
dtVhjH/HoQb/cBf4vhVWqnxTq3jDXvFfJF2u7bvfDi/WvFz4WR4bJifVwzm4b8Yg
spyFwKuOIBT0oV5+Wa2o16momcEhO3FJ51gPchA+q91EjB40wdiItIYPME2ycswr
B2L6jlxXUhvCtEbITuKiFMtbobzMK351FwECAwEAAaOCAlIwggJOMA4GA1UdDwEB
/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwDAYDVR0TAQH/
BAIwADAdBgNVHQ4EFgQUmTb1hBdK+uQNojGzyGkc9cTV5swwHwYDVR0jBBgwFoAU
FC6zF7dYVsuuUAlA5h+vnYsUwsYwVQYIKwYBBQUHAQEESTBHMCEGCCsGAQUFBzAB
hhVodHRwOi8vcjMuby5sZW5jci5vcmcwIgYIKwYBBQUHMAKGFmh0dHA6Ly9yMy5p
LmxlbmNyLm9yZy8wIwYDVR0RBBwwGoIYKi5hcHBzLnN5cy5vcGVubGFiLTAxLmty
MEwGA1UdIARFMEMwCAYGZ4EMAQIBMDcGCysGAQQBgt8TAQEBMCgwJgYIKwYBBQUH
AgEWGmh0dHA6Ly9jcHMubGV0c2VuY3J5cHQub3JnMIIBAwYKKwYBBAHWeQIEAgSB
9ASB8QDvAHYAejKMVNi3LbYg6jjgUh7phBZwMhOFTTvSK8E6V6NS61IAAAGHTzn+
UAAABAMARzBFAiEAgo2L5N3YfxyEMdjn1HZ7jnA1DwAmM0eW/VBu777oXEwCICfT
2ERFr2DtpiNxFmgxiMp8curLpJkLoFCKCXmBA+1pAHUAtz77JN+cTbp18jnFulj0
bF38Qs96nzXEnh0JgSXttJkAAAGHTzn+XwAABAMARjBEAiA+l2E7Za0hy6GjC+A8
FKyct6poGoikw3dJ2Cyvwz3XSgIgPHVe2muDEMXdkqd+8LUFTIdVeoF5meWIGa23
mNxPav0wDQYJKoZIhvcNAQELBQADggEBAGOcQAgMcIu4Qm+1QOdKg5GPFC4gvrYH
S98TvaUjSAnYcw+N1hjGM4qotjXm1Vfh59SLKxLtrmosz4wkoHk8BnCyn2tynPeI
h1Pz5HoxPpezHkhk4ntLvuwPjbWVIC4mWA9nztC7Z4ia7U16bltRCGxySRdYx0bt
T8R7p0uWNsYvbMV2CqM8HuMzZ4VEOO/ci9xvudJWKYfM0VFhNE3Lfp7pvdylLoSF
oMtTJNBiR7j1zFGocwsQpMc+CH8IcOkkObQjr1CLG5TB4VcpzfAJhhLYOBvcgUbP
xYHG4T0yhakgCapUu1+6oExj8Mp1ghOpVClEKN7uU0ffcUDvken0k/c=
-----END CERTIFICATE-----
-----BEGIN CERTIFICATE-----
MIIFFjCCAv6gAwIBAgIRAJErCErPDBinU/bWLiWnX1owDQYJKoZIhvcNAQELBQAw
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMjAwOTA0MDAwMDAw
WhcNMjUwOTE1MTYwMDAwWjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3Mg
RW5jcnlwdDELMAkGA1UEAxMCUjMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK
AoIBAQC7AhUozPaglNMPEuyNVZLD+ILxmaZ6QoinXSaqtSu5xUyxr45r+XXIo9cP
R5QUVTVXjJ6oojkZ9YI8QqlObvU7wy7bjcCwXPNZOOftz2nwWgsbvsCUJCWH+jdx
sxPnHKzhm+/b5DtFUkWWqcFTzjTIUu61ru2P3mBw4qVUq7ZtDpelQDRrK9O8Zutm
NHz6a4uPVymZ+DAXXbpyb/uBxa3Shlg9F8fnCbvxK/eG3MHacV3URuPMrSXBiLxg
Z3Vms/EY96Jc5lP/Ooi2R6X/ExjqmAl3P51T+c8B5fWmcBcUr2Ok/5mzk53cU6cG
/kiFHaFpriV1uxPMUgP17VGhi9sVAgMBAAGjggEIMIIBBDAOBgNVHQ8BAf8EBAMC
AYYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMBIGA1UdEwEB/wQIMAYB
Af8CAQAwHQYDVR0OBBYEFBQusxe3WFbLrlAJQOYfr52LFMLGMB8GA1UdIwQYMBaA
FHm0WeZ7tuXkAXOACIjIGlj26ZtuMDIGCCsGAQUFBwEBBCYwJDAiBggrBgEFBQcw
AoYWaHR0cDovL3gxLmkubGVuY3Iub3JnLzAnBgNVHR8EIDAeMBygGqAYhhZodHRw
Oi8veDEuYy5sZW5jci5vcmcvMCIGA1UdIAQbMBkwCAYGZ4EMAQIBMA0GCysGAQQB
gt8TAQEBMA0GCSqGSIb3DQEBCwUAA4ICAQCFyk5HPqP3hUSFvNVneLKYY611TR6W
PTNlclQtgaDqw+34IL9fzLdwALduO/ZelN7kIJ+m74uyA+eitRY8kc607TkC53wl
ikfmZW4/RvTZ8M6UK+5UzhK8jCdLuMGYL6KvzXGRSgi3yLgjewQtCPkIVz6D2QQz
CkcheAmCJ8MqyJu5zlzyZMjAvnnAT45tRAxekrsu94sQ4egdRCnbWSDtY7kh+BIm
lJNXoB1lBMEKIq4QDUOXoRgffuDghje1WrG9ML+Hbisq/yFOGwXD9RiX8F6sw6W4
avAuvDszue5L3sz85K+EC4Y/wFVDNvZo4TYXao6Z0f+lQKc0t8DQYzk1OXVu8rp2
yJMC6alLbBfODALZvYH7n7do1AZls4I9d1P4jnkDrQoxB3UqQ9hVl3LEKQ73xF1O
yK5GhDDX8oVfGKF5u+decIsH4YaTw7mP3GFxJSqv3+0lUFJoi5Lc5da149p90Ids
hCExroL1+7mryIkXPeFM5TgO9r0rvZaBFOvV2z0gp35Z0+L4WPlbuEjN/lxPFin+
HlUjr8gRsI3qfJOQFy/9rKIJR0Y/8Omwt/8oTWgy1mdeHmmjk7j1nYsvC9JSQ6Zv
MldlTTKB3zhThV1+XWYp6rjd5JW1zbVWEkLNxE7GJThEUG3szgBVGP7pSWTUTsqX
nLRbwHOoq7hHwg==
-----END CERTIFICATE-----
-----BEGIN CERTIFICATE-----
MIIFYDCCBEigAwIBAgIQQAF3ITfU6UK47naqPGQKtzANBgkqhkiG9w0BAQsFADA/
MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT
DkRTVCBSb290IENBIFgzMB4XDTIxMDEyMDE5MTQwM1oXDTI0MDkzMDE4MTQwM1ow
TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwggIiMA0GCSqGSIb3DQEB
AQUAA4ICDwAwggIKAoICAQCt6CRz9BQ385ueK1coHIe+3LffOJCMbjzmV6B493XC
ov71am72AE8o295ohmxEk7axY/0UEmu/H9LqMZshftEzPLpI9d1537O4/xLxIZpL
wYqGcWlKZmZsj348cL+tKSIG8+TA5oCu4kuPt5l+lAOf00eXfJlII1PoOK5PCm+D
LtFJV4yAdLbaL9A4jXsDcCEbdfIwPPqPrt3aY6vrFk/CjhFLfs8L6P+1dy70sntK
4EwSJQxwjQMpoOFTJOwT2e4ZvxCzSow/iaNhUd6shweU9GNx7C7ib1uYgeGJXDR5
bHbvO5BieebbpJovJsXQEOEO3tkQjhb7t/eo98flAgeYjzYIlefiN5YNNnWe+w5y
sR2bvAP5SQXYgd0FtCrWQemsAXaVCg/Y39W9Eh81LygXbNKYwagJZHduRze6zqxZ
Xmidf3LWicUGQSk+WT7dJvUkyRGnWqNMQB9GoZm1pzpRboY7nn1ypxIFeFntPlF4
FQsDj43QLwWyPntKHEtzBRL8xurgUBN8Q5N0s8p0544fAQjQMNRbcTa0B7rBMDBc
SLeCO5imfWCKoqMpgsy6vYMEG6KDA0Gh1gXxG8K28Kh8hjtGqEgqiNx2mna/H2ql
PRmP6zjzZN7IKw0KKP/32+IVQtQi0Cdd4Xn+GOdwiK1O5tmLOsbdJ1Fu/7xk9TND
TwIDAQABo4IBRjCCAUIwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYw
SwYIKwYBBQUHAQEEPzA9MDsGCCsGAQUFBzAChi9odHRwOi8vYXBwcy5pZGVudHJ1
c3QuY29tL3Jvb3RzL2RzdHJvb3RjYXgzLnA3YzAfBgNVHSMEGDAWgBTEp7Gkeyxx
+tvhS5B1/8QVYIWJEDBUBgNVHSAETTBLMAgGBmeBDAECATA/BgsrBgEEAYLfEwEB
ATAwMC4GCCsGAQUFBwIBFiJodHRwOi8vY3BzLnJvb3QteDEubGV0c2VuY3J5cHQu
b3JnMDwGA1UdHwQ1MDMwMaAvoC2GK2h0dHA6Ly9jcmwuaWRlbnRydXN0LmNvbS9E
U1RST09UQ0FYM0NSTC5jcmwwHQYDVR0OBBYEFHm0WeZ7tuXkAXOACIjIGlj26Ztu
MA0GCSqGSIb3DQEBCwUAA4IBAQAKcwBslm7/DlLQrt2M51oGrS+o44+/yQoDFVDC
5WxCu2+b9LRPwkSICHXM6webFGJueN7sJ7o5XPWioW5WlHAQU7G75K/QosMrAdSW
9MUgNTP52GE24HGNtLi1qoJFlcDyqSMo59ahy2cI2qBDLKobkx/J3vWraV0T9VuG
WCLKTVXkcGdtwlfFRjlBz4pYg1htmf5X6DYO8A4jqv2Il9DjXA6USbW1FzXSLr9O
he8Y4IWS6wY7bCkjCWDcRQJMEhg76fsO3txE+FiYruq9RUWhiF1myv4Q6W+CyBFC
Dfvp7OOGAN6dEOM4+qR9sdjoSYKEBpsr6GtPAQw4dy753ec5
-----END CERTIFICATE-----




cat ~/system-cert/archive/${DOMAIN}/cert1.pem

-----BEGIN CERTIFICATE-----
MIIFNTCCBB2gAwIBAgISA3PzV3XxkfLZipYT1Jne/ze/MA0GCSqGSIb3DQEBCwUA
MDIxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQD
EwJSMzAeFw0yMzA0MDUwMTIzMzNaFw0yMzA3MDQwMTIzMzJaMCMxITAfBgNVBAMM
GCouYXBwcy5zeXMub3BlbmxhYi0wMS5rcjCCASIwDQYJKoZIhvcNAQEBBQADggEP
ADCCAQoCggEBALqWRI9lHtZglr4c4kB9cqYwoI541tukHtAC3v8Wo7S5V7y/Mcf1
y+Vg7zoDAlspr20gEEo7/0kfPO5tFPZCAufUVygsH3g6Vfkm3GMyyen3NckJ/sjI
vPbSpgpf+DmCOZH2H7rvOVxbSkcznKra2yHdx5m/juJpO/ukedEEJQdSXxiX0x1R
dtVhjH/HoQb/cBf4vhVWqnxTq3jDXvFfJF2u7bvfDi/WvFz4WR4bJifVwzm4b8Yg
spyFwKuOIBT0oV5+Wa2o16momcEhO3FJ51gPchA+q91EjB40wdiItIYPME2ycswr
B2L6jlxXUhvCtEbITuKiFMtbobzMK351FwECAwEAAaOCAlIwggJOMA4GA1UdDwEB
/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwDAYDVR0TAQH/
BAIwADAdBgNVHQ4EFgQUmTb1hBdK+uQNojGzyGkc9cTV5swwHwYDVR0jBBgwFoAU
FC6zF7dYVsuuUAlA5h+vnYsUwsYwVQYIKwYBBQUHAQEESTBHMCEGCCsGAQUFBzAB
hhVodHRwOi8vcjMuby5sZW5jci5vcmcwIgYIKwYBBQUHMAKGFmh0dHA6Ly9yMy5p
LmxlbmNyLm9yZy8wIwYDVR0RBBwwGoIYKi5hcHBzLnN5cy5vcGVubGFiLTAxLmty
MEwGA1UdIARFMEMwCAYGZ4EMAQIBMDcGCysGAQQBgt8TAQEBMCgwJgYIKwYBBQUH
AgEWGmh0dHA6Ly9jcHMubGV0c2VuY3J5cHQub3JnMIIBAwYKKwYBBAHWeQIEAgSB
9ASB8QDvAHYAejKMVNi3LbYg6jjgUh7phBZwMhOFTTvSK8E6V6NS61IAAAGHTzn+
UAAABAMARzBFAiEAgo2L5N3YfxyEMdjn1HZ7jnA1DwAmM0eW/VBu777oXEwCICfT
2ERFr2DtpiNxFmgxiMp8curLpJkLoFCKCXmBA+1pAHUAtz77JN+cTbp18jnFulj0
bF38Qs96nzXEnh0JgSXttJkAAAGHTzn+XwAABAMARjBEAiA+l2E7Za0hy6GjC+A8
FKyct6poGoikw3dJ2Cyvwz3XSgIgPHVe2muDEMXdkqd+8LUFTIdVeoF5meWIGa23
mNxPav0wDQYJKoZIhvcNAQELBQADggEBAGOcQAgMcIu4Qm+1QOdKg5GPFC4gvrYH
S98TvaUjSAnYcw+N1hjGM4qotjXm1Vfh59SLKxLtrmosz4wkoHk8BnCyn2tynPeI
h1Pz5HoxPpezHkhk4ntLvuwPjbWVIC4mWA9nztC7Z4ia7U16bltRCGxySRdYx0bt
T8R7p0uWNsYvbMV2CqM8HuMzZ4VEOO/ci9xvudJWKYfM0VFhNE3Lfp7pvdylLoSF
oMtTJNBiR7j1zFGocwsQpMc+CH8IcOkkObQjr1CLG5TB4VcpzfAJhhLYOBvcgUbP
xYHG4T0yhakgCapUu1+6oExj8Mp1ghOpVClEKN7uU0ffcUDvken0k/c=
-----END CERTIFICATE-----


cat ~/system-cert/archive/${DOMAIN}/privkey1.pem


-----BEGIN RSA PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6lkSPZR7WYJa+
HOJAfXKmMKCOeNbbpB7QAt7/FqO0uVe8vzHH9cvlYO86AwJbKa9tIBBKO/9JHzzu
bRT2QgLn1FcoLB94OlX5JtxjMsnp9zXJCf7IyLz20qYKX/g5gjmR9h+67zlcW0pH
M5yq2tsh3ceZv47iaTv7pHnRBCUHUl8Yl9MdUXbVYYx/x6EG/3AX+L4VVqp8U6t4
w17xXyRdru273w4v1rxc+FkeGyYn1cM5uG/GILKchcCrjiAU9KFeflmtqNepqJnB
ITtxSedYD3IQPqvdRIweNMHYiLSGDzBNsnLMKwdi+o5cV1IbwrRGyE7iohTLW6G8
zCt+dRcBAgMBAAECggEALeQ9sqMlZYbWLKZAA6qXI0b8hm40Mkt4hKLgJA92bkHT
0YmY/dx3rNAdpsAMuU1UZ1umhguhq2NyghX0POlZe5mfYjMP/msisCgdtoxMWy5B
XVQHBW1SBMckrZKTI9nivQKzJTAnhI6zgzWvh9pSYU8CA8+4nflSFn+Kspz5WoY8
V3IjW8R9PfpCmX9TNLiSWLSUnBGmpwXYiX+ARuw+67ci7UpTxnIccggEO/I6JS11
N5EWpiWQYQO0RhShSysCnoDuJfkRUCP5JOR4+u3xiDCx2c2WCL3i5Sy0aedx5Tem
iu+PUhztQcmfy84AJvRdnZwAP+qJMz96fGis9IdiUQKBgQD8oo0Z/WeXSgbW4yNJ
8Us/zND5WjgWZ704+fy7P00tEcoioun7RNIpHbufHNok3G4tOXVyFtO93+10De6k
m5WGYdbGFCf7geBUNMpN1aRwP7n9jbGlltQlFYZ8fsTxYHDZUMXh1hpl6vGYvSDH
hdvqB7BHOuXfJSWHgEV70N0JLQKBgQC9EoCmMej/42lPw+c1JPxa1jPNS/1jxd6C
pEGuD0nbWE/8X0c61TKul+X9ziOCzgoVQxqJUIOhA54LP9N9UdIgF72H5W5ig0X0
0p96hYIa1cj+wPnBItlbDtlpoY2TlgmkPneLZFAi8Ud2FWjCsmrKiGECZk4ldAOJ
73sF7ekBpQKBgQDNtrs434wbuC6rihbxHedNU0US1jD3pY+CNqVh/6CjQ2oaHBh0
/0w2KLzfSkojW87nSnNk7EuMwCuFpdad+Qaff0m4zy7jcJUvXDEjggS9VlbnrRpr
oM6sR8PT2hIaDpK1kj0fsQyb0tzZp59qeIhjH9GdnssRkhjmJj/uG1vxHQKBgFps
ceiJ8CQJL8F9MKNjB+mtjeK+V/YSmMMkKWFEYI8tojtRmfBSLEYB0XXb03HodjwK
9rL7NaiY4UoCAXRq4DKHCBbpLCyjGZ7n6GuhVELfQZxM0GWE9CyvYl05bUtLM1tA
oM2icQz1FbcssAiFabtpE/Qg13jkDhk21kBUst4dAoGAbpWzO4nHFLQW3O/qGP/I
jJKLlTmqXaXbpRLo6r+A4hNzEboNkCWCXwuZ06BC2/oJjP3JfGbUoP1xg4gLlt0P
y7kA2ZAJaqj7Jt5X7ICAyOivvH1ZNn8xb7rYySTfnqmwBGYlCzrq9QTzmX9gm3Nf
zNTHLwHpwQ9InWTgpZJqfbE=
-----END RSA PRIVATE KEY-----



	도메인 네임서버 변경

nameserver
ns1-1.ns-ncloud.com
ns1-2.ns-ncloud.com



	db server이름

paasta-admin-db

	db server type

standard

	db service 이름

paasta-admin-db

	host

%

	username

paasta2023

	user암호

paasta2023!

	기본db명

paasta-admin

	backup주기 

7일


	root 계정:

chim@gwangju.ac.kr
광주1234%


	API access key

JswkqxqkfzrTiLP7e4qn
h5FFjQtlmmgXgUXl70AW3a5tp9PpV1RRbKG16Y8G


​	
https://uaa.sys.openlab-01.kr/passcode
T45GTP0zyB

	cf login role 설정

cf login -a api.sys.openlab-01.kr --sso




(root 계정으로 로그인 후 진행)
cf set-space-role 45329a60-d510-11ec-88d1-005056a7aca7 system system-space SpaceDeveloper
cf space-users system system-space

	sampleapp 배포

cf create-service nks 10 nks-sample -c '{
    "name": "cluster-sample",
    "k8sVersion": "1.24.10-nks.1",
    "subnetNoList": "15889",
    "publicNetwork": true,
    "subnetLbNo": 15891,
    "loginKeyName": "ncp-paas-ta-admin-key",
    "defaultNodePool.name": "dnp-sample",
    "defaultNodePool.nodeCount": 1,
    "defaultNodePool.productCode": "SVR.VSVR.STAND.C002.M008.NET.SSD.B050.G002",
    "log.audit": true,
    "nodePool": "[{\"name\":\"npsample\",\"productCode\":\"SVR.VSVR.STAND.C002.M008.NET.SSD.B050.G002\",\"nodeCount\":2}]"

}'


 loginKeyName   : login pem key name
                    ncp-paas-ta-admin-key
 k8sVersion     : ncp console 
                    1.24.10-nks.1
 subnetNoList[] : ncp console
                    15889
 subnetLbNo     : ncp console
                    15891



	ncp-iam설치

curl -o ncp-iam-authenticator -L https://github.com/NaverCloudPlatform/ncp-iam-authenticator/releases/latest/download/ncp-iam-authenticator_linux_amd64

chmod +x ./ncp-iam-authenticator


mkdir -p /root/bin && cp ./ncp-iam-authenticator /root/bin/ncp-iam-authenticator && export PATH=$PATH:/root/bin
echo 'export PATH=$PATH:$HOME/bin' >> ~/.bash_profile
ncp-iam-authenticator help


	ncp-iam-config

export NCLOUD_ACCESS_KEY=JswkqxqkfzrTiLP7e4qn
export NCLOUD_SECRET_KEY=h5FFjQtlmmgXgUXl70AW3a5tp9PpV1RRbKG16Y8G
export NCLOUD_API_GW=https://ncloud.apigw.gov-ntruss.com

	config 생성 

ncp-iam-authenticator create-kubeconfig --region KR --clusterUuid c76f7fad-6009-4d75-8b10-f9f18e89d87a --output kubeconfig.yaml
ㄷ턋

	kubectl 설치

curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"

sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl


kubectl --kubeconfig kubeconfig.yaml get nodes

	mysql-db 생성

cf create-service cdbmysql HA mysql-sample -c '{
"cloudMysqlServiceName": "cdb-mysql",
"cloudMysqlServerNamePrefix": "cdb-mysql",
"cloudMysqlUserName": "paasta2023",
"cloudMysqlUserPassword":"paasta2023!",
"hostIp":"%",
"cloudMysqlDatabaseName":"cdb-name-paasta"
}'



	test-node-app

git clone https://github.com/cloudfoundry/cf-for-k8s.git
cd ~/cf-for-k8s/tests/smoke/assets/test-node-app
cf push test-node-app

	create 된 후

cf apps 로 확인


cf bind-service test-node-app mysql-sample

	체크

cf service mysql-sample
cf env test-node-app

	app env 변경 

cf set-env test-node-app BOUND_AT $(date +%s)
cf restage test-node-app


파스-타 테스트 부탁드립니다. 

- org 생성			
  : 확인

- space 생성
  : 확인

- sample app 배포 	
  : 확인
  test-node-app        started           web:1/1     test-node-app.apps.sys.openlab-01.kr

- sample app scale 변경  
  : 
  cf scale test-node-app -i 2
  :Scaling app test-node-app in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...
  cf apps
  test-node-app        started           web:2/2     test-node-app.apps.sys.openlab-01.kr
  : 확인

- domain 생성 
  : cf create-domain ORG DOMAIN
  : cf create-domain system test.com\
  Creating domain test.com for org system as 45329a60-d510-11ec-88d1-005056a7aca7...
  OK
  : 확인

- domain : map-route 연결 (sample App)	
  : cf map-route test-node-app test.com --hostname user
  Creating route user.test.com for org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...
  OK

  Mapping route user.test.com to app test-node-app in org system / space system-space as 45329a60-d510-11ec-88d1-005056a7aca7...
  OK
  : 확인

- app log
  : 	cf logs
  :	test-node-app "Hello world" 확인





- 위의 내용 역순으로 삭제 





- org 생성
  : cf create-org test 
- space 생성
  :  cf create-space tester
- sample app 배포 
  : cf push test-node-app
- sample app scale 변경 
  : cf scale test-node-app -i 2
- domain 생성 
  : cf create-domain system test.com
- domain : map-route 연결 (sample App)
  : cf map-route test-node-app test.com --hostname user





# user quota 생성

cf create-quota trainee -m 4GB -i -1 -s -1 -r -1 --reserved-route-ports -1 --allow-paid-service-plans


vim create-user.sh
---

#!/bin/bash

ID=gju_trainee${1}
PASSWORD=trainee${1}
ORG_NAME=trainee${1}
SPACE_NAME=dev

# create-user

cf create-user ${ID} ${PASSWORD}

# create-org

cf create-org ${ORG_NAME}

# create-space

cf create-space ${SPACE_NAME} -o ${ORG_NAME}

# set-org-role

cf set-org-role ${ID} ${ORG_NAME} OrgManager

# set-space-role

cf set-space-role ${ID} ${ORG_NAME} ${SPACE_NAME} SpaceManager
cf set-space-role ${ID} ${ORG_NAME} ${SPACE_NAME} SpaceDeveloper

cf set-quota ${ORG_NAME} trainee
---

source create-user.sh 01
source create-user.sh 02
source create-user.sh 03
source create-user.sh 04
source create-user.sh 05
source create-user.sh 06
source create-user.sh 07
source create-user.sh 08
source create-user.sh 09
source create-user.sh 10
source create-user.sh 11
source create-user.sh 12
source create-user.sh 13
source create-user.sh 14
source create-user.sh 15
source create-user.sh 16
source create-user.sh 17
source create-user.sh 18
source create-user.sh 19
source create-user.sh 20

test2023
testTEST2023!



1. root login
   - 권한 부여 할 id UUID: ${UUID}


2. 권한부여 
   cf login -a api.sys.openlab-01.kr --sso
   cf set-space-role ${UUID} system system-space SpaceManager
   cf set-space-role ${UUID} system system-space SpaceDeveloper
   cf set-org-role ${UUID} system OrgManager


3. 권한 부여한 id로 로그인 및 확인 
   cf org-users system
   cf space-users system system-space