## Prep

```bash
cf create-service p-cloudcache dev-plan petclinic-pcc
cf create-service-key petclinic-pcc devkey
cf service-key petclinic-pcc devkey
```

## Create Regions

```bash

start locator --name=l1
configure pdx --read-serialized=true
start server --name=s1

create region --name=vets --type=PARTITION_REDUNDANT_PERSISTENT
create region --name=owners --type=PARTITION_REDUNDANT_PERSISTENT
create region --name=pets --type=PARTITION_REDUNDANT_PERSISTENT
create region --name=visits --type=PARTITION_REDUNDANT_PERSISTENT

shutdown --include-locators=true
```
destroy region --name=vets


or 

```bash
run --file=/Users/dpfeffer/workspace/spring-petclinic/spring-petclinic-app/geode-docker/gfsh/gofish.gfsh
```

## Cleanup
remove --region=/vets --all=true

cf delete-service-key petclinic-pcc k1 -f
cf delete-service petclinic-pcc -f

## PCC
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

6. Deploy on West Instance 2
```bash
create region --name=vets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=owners --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=pets --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
create region --name=visits --type=PARTITION_REDUNDANT_PERSISTENT --gateway-sender-id=send_to_peer
```

## Write Behind

deploy -jar /Users/dpfeffer/workspace/spring-petclinic/spring-petclinic-write-behind/target/spring-petclinic-write-behind-2.1.0.BUILD-SNAPSHOT.jar
create async-event-queue --id=ownerQueue --listener=io.pivotal.petclinic.pcc.OwnerAsyncEventListener --listener-param=url#jdbc:db2:SAMPLE,username#gfeadmin,password#admin1
create region --name=vets --type=PARTITION_REDUNDANT
create region --name=owners --type=PARTITION_REDUNDANT --async-event-queue-id=ownerQueue
create region --name=pets --type=PARTITION_REDUNDANT
create region --name=visits --type=PARTITION_REDUNDANT


what's difference between using a cache loader/writer and implementing the [AsyncEventListener for Write-Behind Cache Event Handling](https://gemfire.docs.pivotal.io/98/geode/developing/events/implementing_write_behind_event_handler.html)
best way to manage connection string information
how do you deploy jar file to pcc

## Setup Credentials
cf create-service credhub default petclinic-credhub -c '{"datadog-api-key":"$API_KEY","datadog-application-key":"$APP_KEY"}'