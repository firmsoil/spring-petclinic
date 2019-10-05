# Spring PetClinic Sample Application (Adapted for Multi-region HA)

The following repo is adapted from [Spring PetClinic](https://github.com/spring-projects/spring-petclinic).

Key aspects:

- Refactored JPA relational backend to target [Pivotal Cloud Cache](https://pivotal.io/pivotal-cloud-cache)
- Added custom metrics to important business transactions
- Instrumented for data dog
- Added geode docker image for local testing
- Added scripts for application CI with concourse and application CD with spinnaker
- Instructions for configuration of bi-directional PCC wan gateway replication


## Local Testing

For Local Testing use the [Pet Clinic Geode Docker Contianer](geode-docker/Readme.md).


## PCC Setup on PAS

> Per guidelinces in [WAN Bi-directional Setup](https://docs.pivotal.io/p-cloud-cache/1-8/WAN-bi-setup.html)

1. Setup East Instance

```bash
cf create-service p-cloudcache dev-plan petclinic-pcc -c '{
"distributed_system_id" : 1 }'
    cf create-service-key petclinic-pcc k1
    cf service-key petclinic-pcc k1

```

2. Setup West Instance

```bash
cf create-service p-cloudcache dev-plan petclinic-pcc -c '
{
  "distributed_system_id":2,
  "remote_clusters":[
  {
    "remote_locators":[
      "10.0.4.10[55221]"],
    "trusted_sender_credentials":[
    {
      "username": "RETRIEVE_FROM_K1_ON_EAST_SERVER",
      "password":"RETRIEVE_FROM_K1_ON_EAST_SERVER"
    }]
  }]
}'
cf create-service-key petclinic-pcc k1
cf service-key petclinic-pcc k1
```

3. Update East Instance

```bash
cf update-service petclinic-pcc -c '
{
  "remote_clusters":[
  {
    "remote_locators":[
      "10.1.4.10[55221]"],
    "trusted_sender_credentials":[
    {
      "username":"RETRIEVE_FROM_K1_ON_WEST_SERVER",
      "password":"RETRIEVE_FROM_K1_ON_WEST_SERVER"
    }]
  }]
}'
cf delete-service-key petclinic-pcc k1 -f
cf create-service-key petclinic-pcc k1
cf service-key petclinic-pcc k1

```

4. Test on East Instance 1
```bash
create gateway-sender --id=send_to_peer --remote-distributed-system-id=2 --enable-persistence=true
create region --name=regionX --gateway-sender-id=send_to_peer --type=PARTITION_REDUNDANT
```

5. Test on West Instance 2
```bash
create gateway-sender --id=send_to_peer --remote-distributed-system-id=1 --enable-persistence=true
create region --name=regionX --gateway-sender-id=send_to_peer --type=PARTITION_REDUNDANT
```

6. Deploy on East Instance 1
```bash
create region --name=vets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=owners --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=pets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=visits --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
```

7. Deploy on West Instance 2
```bash
create region --name=vets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=owners --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=pets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=visits --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
```

## Setup Data Dog Credentials in Each Space
```bash
cf create-service credhub default petclinic-credhub -c '{"datadog-api-key":"$API_KEY","datadog-application-key":"$APP_KEY"}'
```

## CI/CD Strategy

CI/CD strategy follows that documented with this example: https://github.com/doddatpivotal/pickup-prediction-service.

## CI Pipeline

![concourse](/docs/ci.png)

Deploy pipeline

```bash
fly -t s1p login --team-name=digital -k
fly -t s1p set-pipeline -c ci/pipeline-spinnaker.yml -l ci/.secrets.yml -p spring-petclinic -n
fly -t s1p unpause-pipeline -p spring-petclinic
fly -t s1p expose-pipeline -p spring-petclinic

```

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

## Spinnaker Scripts

![spinnaker](/docs/cd.png)

1. Push app and pipeline
```bash
./scripts/push-spinnaker-config.sh
```

2. Pull app and pipeline
```bash
./scripts/pull-spinnaker-config.sh
```
