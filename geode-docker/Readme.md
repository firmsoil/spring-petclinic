# Geode Docker Example

## Build and Run Docker Image

```bash
docker build . -t petclinic-geode
docker run -d -p 10334:10334 -p 40404:40404 -p 1099:1099 -t petclinic-geode
# get container id

docker logs <container_id>
# validate all regions are created

docker exec -it <container_id> /bin/bash
$ ./geode/bin/gfsh

```

## Explanation
This will run a two node one locator geode cluster with a partitioned redundant region created.  You can then use gfsh to access it.

## References

- How to run Apache Geode with docker: https://cwiki.apache.org/confluence/display/GEODE/How+to+use+Geode+on+Docker

- Gemfire Docker Repo: https://github.com/GSSJacky/gemfire-docker/blob/master/Dockerfile

- Docker Geode: https://github.com/markito/geode-docker
