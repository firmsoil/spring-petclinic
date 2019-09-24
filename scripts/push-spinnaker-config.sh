#!/usr/bin/env bash
spin application save \
  --application-name springpetclinic \
  --cloud-providers cloudfoundry,kubernetes \
  --owner-email dpfeffer@pivotal.io \
  --file ci/spinnaker/springpetclinic/application.json \
  --gate-endpoint https://api.spinnaker.vimana.s1p.koundinya.cc \
  --insecure

curl -X POST \
  https://api.spinnaker.vimana.s1p.koundinya.cc/v2/canaryConfig \
  --header 'Content-Type: application/json' \
  --data @ci/spinnaker/springpetclinic/canary-config/petclnic-kayenta-test.json \
  --insecure

spin pipeline save \
  --file ci/spinnaker/springpetclinic/pipeline/s1p-deliver.json \
  --gate-endpoint https://api.spinnaker.vimana.s1p.koundinya.cc \
  --insecure
