# Geode Docker Example

## Build and Run Docker Image

```bash
docker build . -t <YOUR_TAG>
docker run -t <YOUR_TAG>
```

## Explanation
This will run a two node one locator geode cluster with a partitioned redundant region created.  You can then use gfsh to access it.

## References

- How to run Apache Geode with docker: https://cwiki.apache.org/confluence/display/GEODE/How+to+use+Geode+on+Docker

- Gemfire Docker Repo: https://github.com/GSSJacky/gemfire-docker/blob/master/Dockerfile

- Docker Geode: https://github.com/markito/geode-docker
