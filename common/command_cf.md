# app 상태
` cf app APP_NAME `

# app binding 상태
` cf env APP_NAME`

# 배포된 서비스 보기 
` cf services `

# ssh터널링을 통한 내부 접속
`bosh -d SERVICE_NAME ssh SERVICE_NAME

# bosh release  보기
`bosh rs`

# bosh cloud-config 보기
`bosh cc` 

# bosh log확인
bosh logs <APP_NAME>

# 설치 수행 확인
bosh tasks

