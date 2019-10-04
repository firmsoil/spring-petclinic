# Concourse Notes

## CI Pipeline

Deploy pipeline

```bash
fly -t s1p login --team-name=digital -k
fly -t s1p set-pipeline -c ci/pipeline-spinnaker.yml -l ci/.secrets.yml -p spring-petclinic -n
fly -t s1p unpause-pipeline -p spring-petclinic
fly -t s1p expose-pipeline -p spring-petclinic

```
