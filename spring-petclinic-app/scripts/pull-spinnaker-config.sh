#!/usr/bin/env bash

spin application get springpetclinic \
  --gate-endpoint https://api.spinnaker.vimana.s1p.koundinya.cc \
  --insecure \
  > ci/spinnaker/springpetclinic/application.json

export config=`curl https://api.spinnaker.vimana.s1p.koundinya.cc/v2/canaryConfig \
  --header 'Content-Type: application/json' \
  --insecure`
export config="{\"data\": $config}"
export configId=`echo $config | jq '.data[] | select(.name=="petclinic-kayenta-test") | .id' -r`

curl https://api.spinnaker.vimana.s1p.koundinya.cc/v2/canaryConfig/$configId \
  --header 'Content-Type: application/json' \
  --insecure \
  > ci/spinnaker/springpetclinic/canary-config/petclnic-kayenta-test.json

spin pipeline get \
  --application springpetclinic \
  --name "S1P Deliver" \
  --gate-endpoint https://api.spinnaker.vimana.s1p.koundinya.cc \
  --insecure \
  > ci/spinnaker/springpetclinic/pipeline/s1p-deliver.json
