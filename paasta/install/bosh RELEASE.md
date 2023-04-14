# release 구성 

1. release 
    - jobs
        release를 구성하는 job으로 이루어져 있다. 
        - spec:
            - templates
                job을 구성하는 파일들의 경로를 작성한다.
            - properties:
                runtime-config에 선언된 값을 properties로 가져와 사용할 수 있으며 default값을 명시해준다. default=''이면 defaulte값을 사용하겠다는 뜻이다. 
        - scripts:
            deploy에 필요한 파일이 있다면 생성해준다.
            생성 후 spec > templates에 추가한다.
        
2. runtime-config
    runtime-config는 상황에 따라 다르게 작용하는데 addon에 어떤 형태로 되어있는지에 따라 실행 시간이 다르다. runtime-config의  jobs에 변수로 사용되는 spec값을 입력 할 수 있다. 예를들어 ftp server에 필요한 key 라던지 user id 혹은 ip 정보를 입력해둔다.

    ```yml
    addons
    - include:
       stemcell:
       - os: ubuntu-trusty
       - os: ubuntu-xenial
       - os: ubuntu-bionic
      name: cce-check
        jobs:
          - name: cce-common
          release: cce-check
       properties:
         ftp_use: true
         ftp_ip: "10.160.63.134"
         ftp_key: |
        -----BEGIN RSA PRIVATE KEY-----
        MIIEpAIBAAKCAQEA92+s6M6GdO/+P/xVKPjAuXusoLEBiNTGRWU1hOdsQoLx3w4H
        RjCsmaB2PvFje4lWqTQyno4a+ivPziMkU7qys16Zuy5nUqm6qeYCtijfarhC6vO4
        OgUExAdXHIkKchj70jFFnDsc1E7Qk2hLLnNONLcfCnkI3g6n6q6XnGMDLOzrW1VD
        Hql2XdtAd2O/mEV+Y6llCSXfIP6dQ2C3Nn9I27SZ1OdWKH7D1NEwvLBUa9lAGuLG
        jSRyeiN9Z/aZq0dErfoxECbhqAyFXPPWaycPubmxLvEPEk7rw6dOWt3VN66KFVCV
        XmC18sQsB0P3DXiopVIk5E+a1lbGfNdDJi0/ywIDAQABAoIBAQDYFJ5bcH/a/vp5
        xlJYRWOIl4hZjDpa0/WSBKCIP9E7BD9VG1se4MLaJlcdAJBwAa+8c+kArfjBICGJ
        UwcWV6RXH/YAkLWKas6oMEWN7oOtHtzRcaUgheFzjzVzpfmJSmgfVwgDN1wly+45
        zIpbNjbFsmVZL/mecg4R1AZye8T6JPSMcdUE7dqXMXwKWrdSCjKf7HLCZjLkZQfB
        r62jYId+MQ1jWFRrumggz7G48DjCwNxSEp6Log7QbZ+V4lq87Z2HaKt4GacMM3ZV
        f8ahReWY6FMKFCZbu2gTqQXTtmoSmwpTiBp8mLIdL/YAf3ce4I66FFMwLFndCm5V
        ppj84Y2BAoGBAP1SDDDeKRA5rCmFqjYP0aWNqF/+A0CCtvirrin+S6IgHL4meOhh
        QoXMdrlScekmDXuXxmZ6A2Dtw9Wce9K8CTLI5EKmmd9CHF5Rt6v6SOy4D0DnJB9t
        NuStqgQueDV7ZJrCG2R0Q58xnqlNeYRFEUGA8wcP2XnNaEwPnoPz6zM1AoGBAPoN
        sbKbPMTgEYR2YdvHkauPOh6G6I3S6zGfrgu9HMW6REk7nDKlB+iZ9cIqUxg9c3TX
        7JCbGzmLPpc/Mmsv5yOfEGhcgKHBNlmH84qNcGRIPdDjElr/eg8tYykfmmE3Srxf
        76Kv6hEAsKARFGp9AhKk0rYtx+MDwmmt5bvRKgb/AoGAfp+flmHGVuTtKMg2n9Vi
        bwWTL4ByMbHlMgHnCfih58VzeJ0UfNv4+5aLlfEfHXK29JuWQa0luTLAXCtfPaRe
        WLNQXfyXF6EdOwxP6jgucZ1Diqa/SLI36TDhtjtgPSOT00PJm3n+Pzn3XBts7W/0
        wzBoypPiml7RjouUKdMoMy0CgYBYyhoKCLNoWQP/XCXda5zfJUmG43KaQYXAJiog
        AvO/hiePNXp+RTlsr5ajbnH5DoD7wVw/8MOXCEmeXRNjGixh6q5h0E7goQHSgsLI
        tPZutXQ1wxuWclb+IqiuRomD8aU5BHvL8dkUVoFhJQ9xzJe61Qn5JIJwqroS2mvT
        6C6bMwKBgQD2C58H0WSFwocETv+k+slG7WkT0k7NTNb3NiaOEJkmOI3d69rPIIC6
        A9AvblXHeL0f55B4zi4c999gRglP2dWfSEZtx8YRx5Vdff90QPZPO9gljSBf3Z2C
        GLMRipssbbJRLvjSkjhi491SUFxtZiWNPoJl8kODAjr19gcs3yxcdA==
        -----END RSA PRIVATE KEY-----
        ```

# release 수정 

1. release가 vm에 사용 중일 때
    - runtime-config의 버전을  임시로 바꾸어 vm을 재배포 한다.
    - 그럼 vm이 쓰는 version이 달라지므로 release를 삭제 할 수 있다.
    - delete-release를 한 후 필요에 따른 스크립트를 수정하고 create-release를 한다. 이 때 수정된 버전을 원래 버전으로 고친 후 재배포 한다.

2. deploy되면서 에러난 경우
    - 해당 vm에 접속한다.
    - sudo su -> /var/vcap/jobs/"에러난 해당 job"/bin/post-deploy.sh
    - ./post-deploy.sh를 해준 후 어디서 에러나는지 체크한다.
    - 수정 후 재배포를 진행하며 스크립트를 잡아준다.
    - 알맞게 수정이 되었다면 위와 같은 과정으로 script를 수정 하고 재배포 한다.

