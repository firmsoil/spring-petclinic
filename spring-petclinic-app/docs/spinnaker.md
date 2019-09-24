## Generate load on the production Spinnaker deployed service

```bash
kubectl -n default run injector-test --image=alpine:3.10 --generator=run-pod/v1  -- \
    /bin/sh -c "apk add --no-cache curl; \
    while true; do curl -sS --max-time 3 \
    http://spring-petclinic-test.apps.east.s1p.koundinya.cc/owners?lastName=; done"
```

```bash
kubectl -n default run injector-prod-east --image=alpine:3.10 --generator=run-pod/v1  -- \
    /bin/sh -c "apk add --no-cache curl; \
    while true; do curl -sS --max-time 3 \
    http://spring-petclinic.apps.east.s1p.koundinya.cc/owners?lastName=; done"
```

```bash
kubectl -n default run injector-prod-west --image=alpine:3.10 --generator=run-pod/v1  -- \
    /bin/sh -c "apk add --no-cache curl; \
    while true; do curl -sS --max-time 3 \
    http://spring-petclinic.apps.west.s1p.koundinya.cc/owners?lastName=; done"
```

## Deploy Spinnaker Application, Canary Config and Pipeline    

>Note: spin cli does not have methods to work on canary configs, so we have to use curl

```bash
spin application save \
  --application-name springpetclinic \
  --cloud-providers cloudfoundry,kubernetes \
  --owner-email dpfeffer@pivotal.io \
  --file ci/spinnaker/springpetclinic/application.json \
  --gate-endpoint https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live \
  --insecure

spin application get springpetclinic \
  --gate-endpoint https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live \
  --insecure \
  > ci/spinnaker/springpetclinic/application.json

curl -X POST 
  https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live/v2/canaryConfig \
  --header 'Content-Type: application/json' \
  --data @ci/spinnaker/springpetclinic/canary-config/petclnic-kayenta-test.json \
  --insecure 

curl https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live/v2/canaryConfig \
  --header 'Content-Type: application/json' \
  --insecure 

curl https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live/v2/canaryConfig/dd9509ac-dc63-40bd-bdb6-4ff705375da8 \
  --header 'Content-Type: application/json' \
  --insecure \
  > ci/spinnaker/springpetclinic/canary-config/petclnic-kayenta-test.json 

spin pipeline save \
  --file ci/spinnaker/springpetclinic/pipeline/s1p-deliver.json \
  --gate-endpoint https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live \
  --insecure

spin pipeline get \
  --application springpetclinic \
  --name "S1P Deliver" \
  --gate-endpoint https://api.spinnaker.ingress.kingslanding.pks.lab.winterfell.live \
  --insecure \
  > ci/spinnaker/springpetclinic/pipeline/s1p-deliver.json
```
