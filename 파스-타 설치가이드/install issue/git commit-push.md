
$ git branch bami

$ git switch bami
$ git remote add upstream https://github.com/PaaS-TA/cce-check.git
$ git remote add master https://github.com/PaaS-TA/cce-check.git

$ git remote -v
master  https://github.com/PaaS-TA/cce-check.git (fetch)
master  https://github.com/PaaS-TA/cce-check.git (push)
origin  https://seunghyejeong@github.com/PaaS-TA/cce-check.git (fetch)
origin  https://seunghyejeong@github.com/PaaS-TA/cce-check.git (push)
upstream        https://github.com/PaaS-TA/cce-check.git (fetch)
upstream        https://github.com/PaaS-TA/cce-check.git (push)

$ git add .
$ git config --global user.email "hyhyhye90@gamil.com"
$ git config --global user.name "seunghyejeong"
$ git commit -m 'cce result script 자동화 구현'
$ git push origin bami


