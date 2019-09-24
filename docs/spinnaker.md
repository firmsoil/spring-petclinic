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
