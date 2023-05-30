scp

# copy file from this machine to machines a deployment

bosh -e vbox -d cf scp ~/Downloads/script.sh :/tmp/script.sh
bosh -e vbox -d cf scp ~/Downloads/script.sh diego-cell:/tmp/script.sh
bosh -e vbox -d cf scp ~/Downloads/script.sh diego-cell/209c42e5-3c1a-432a-8445-ab8d7c9f69b0:/tmp/script.sh
bosh -e vbox -d cf scp ~/Downloads/script.ps1 windows_diego_cell:c:/temp/script/script.ps1

# copy file from remote machines in a deployment to this machine

bosh -e vbox -d cf scp :/tmp/script.sh ~/Downloads/script.sh
bosh -e vbox -d cf scp diego-cell:/tmp/script.sh ~/Downloads/script.sh
bosh -e vbox -d cf scp diego-cell/209c42e5-3c1a-432a-8445-ab8d7c9f69b0:/tmp/script.sh ~/Downloads/script.sh
bosh -e vbox -d cf scp windows_diego_cd .cell:c:/temp/script/script.ps1:~/Downloads/script.ps1

# copy files from each instance into instance specific local directory

bosh -e vbox -d cf scp diego-cell:/tmp/logs/ /tmp/logs/((instance_id))


source linux_info.sh

bosh -e micro-bosh -d paasta scp diego-api/7cd8ac48-5903-44d8-ac92-4bc739889447:/tmp/scripts/*.txt ~/workspace/user/bami/linuxinfo/