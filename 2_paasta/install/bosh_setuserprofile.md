## Set BOSH director environment in user profile
### Add settings to bottom of user profile

```console
$ vi ~/.profile
```

```shell
~
## FOR BOSH
export PAASTA_INCEPTION_USER_NAME='ubuntu'
export PAASTA_DEPLOYMENT_WORKSPACE=/home/${PAASTA_INCEPTION_USER_NAME}/workspace
export PAASTA_BOSH_DIRECTOR='micro-bosh'
export PAASTA_BOSH_IAAS='aws'
export BOSH_ENVIRONMENT='10.0.1.6'
export BOSH_CLIENT='admin'
export BOSH_CLIENT_SECRET=`bosh -e ${PAASTA_BOSH_DIRECTOR} int ${PAASTA_DEPLOYMENT_WORKSPACE}/paasta-deployment/bosh/${PAASTA_BOSH_IAAS}/creds.yml --path /admin_password`
```

### Apply user profile

```console
$ source ~/.profile
```
