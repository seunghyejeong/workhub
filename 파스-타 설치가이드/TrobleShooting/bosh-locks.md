# bosh locks 해결하기

```shell
Cancel-Task¶
bosh [GLOBAL-CLI-OPTIONS] cancel-task ID (Alias: ct)

Cancel task at its next checkpoint. Does not wait until task is cancelled.

bosh ct <task-num>
```

# roboot로 해결 
1. bosh jumpbox login

```shell
ubuntu@paasta-ta-bami-inception-1:~/workspace/paasta-deployment/bosh$ ssh jumpbox@10.160.61.6 -i jumpbox.key
Unauthorized use is strictly prohibited. All access and activity
is subject to logging and monitoring.
Welcome to Ubuntu 22.04.1 LTS (GNU/Linux 5.15.0-52-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage
```

2. bosh/0:/var/vcap/store/director/tasks

```shell
bosh/0:~$ sudo su
```

3. task num으로 오류 확인

```shell
bosh/0:/var/vcap/store/director/tasks
```

4. sudo reboot now

```shell
bosh/0:/var/vcap/store/director/tasks/568# sudo reboot now
Connection to 10.160.61.6 closed by remote host.
Connection to 10.160.61.6 closed.
```

5. ping boshnum으로 올라오는지 확인

```shell
ping 10.160.61.6
```

6. 다시 실행되면 bosh env로 실행된 것 확인

7. lock걸린 service 지우기 

```shell
bosh -d mysql deld --force
```

# bosh가 disk를 생성하다 recive 대기상태에서 lock이 걸렸다

```shell
Task 625 | 01:02:06 | Deleting instances: mysql/031c3787-a786-4819-8ee8-ecf1ce8e8d4c (0) (00:00:45)
                    L Error: Timed out sending 'run_script' to instance: 'mysql/031c3787-a786-4819-8ee8-ecf1ce8e8d4c', agent-id: 'ff48452e-9f11-4d9a-a1d1-974d1ef620b0' after 45 seconds
Task 625 | 01:02:06 | Deleting instances: mysql/91684f42-f3b3-445f-b603-e15943a4820d (1) (00:00:45)
                    L Error: Timed out sending 'run_script' to instance: 'mysql/91684f42-f3b3-445f-b603-e15943a4820d', agent-id: 'e33bbb06-0b80-42c3-b6c1-7f158ffa8e76' after 45 seconds
Task 625 | 01:02:06 | Deleting instances: mysql/91f47ac1-0c35-4957-a10c-82f8453a9c93 (2) (00:00:45)
                    L Error: Timed out sending 'run_script' to instance: 'mysql/91f47ac1-0c35-4957-a10c-82f8453a9c93', agent-id: '0831fcfc-4b56-4670-8452-753a3d6d9779' after 45 seconds
Task 625 | 01:02:06 | Error: Timed out sending 'run_script' to instance: 'mysql/031c3787-a786-4819-8ee8-ecf1ce8e8d4c', agent-id: 'ff48452e-9f11-4d9a-a1d1-974d1ef620b0' after 45 seconds

```

# 해결

    ```shell
    ubuntu@paasta-ta-bami-inception-1:~$ bosh -d mysql deld --force
    ```
    
    - 그러나 --force로 해결이 안될 때는 생성하다 cancel된 disk를 지워야 하므로 그 때는 또 다른 방법을 써야한다.
