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
