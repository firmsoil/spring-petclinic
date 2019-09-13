## Prep

```bash
cf create-service p-cloudcache dev-plan petclinic-pcc
cf create-service-key petclinic-pcc devkey
cf service-key petclinic-pcc devkey
```

## Create Regions

```bash

start locator
configure pdx --read-serialized=true
start server

create region --name=vets --type=PARTITION_REDUNDANT
create region --name=owners --type=PARTITION_REDUNDANT
create region --name=pets --type=PARTITION_REDUNDANT
create region --name=visits --type=PARTITION_REDUNDANT

shutdown --include-locators=true
```
destroy region --name=vets


## PCC
create region --name=vets --type=PARTITION_REDUNDANT_PERSISTENT
create region --name=owners --type=PARTITION_REDUNDANT_PERSISTENT


## Write Behind

what's difference between using a cache loader/writer and implementing the [AsyncEventListener for Write-Behind Cache Event Handling](https://gemfire.docs.pivotal.io/98/geode/developing/events/implementing_write_behind_event_handler.html)
best way to manage connection string information
how do you deploy jar file to pcc
