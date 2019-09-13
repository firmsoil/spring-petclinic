# Docker Apache Geode 1.9.1 represents GEODE version this can be changed to use a different version
FROM apachegeode/geode:1.9.1

# Install network and other tools need for linux image
#RUN env
#RUN yum makecache fast
#RUN yum install -y nc
#RUN yum install -y net-tools
#RUN yum install -y tcpdump

# Add files needed. The source directory is first followed by target directory
ADD gfsh/gofish.gfsh .
ADD build/rungeode.sh .

# TCP ports exposed by the container
# Ports 40404 are server ports
# Port 10334 default locator port
# Ports 8080, 7070 and 1099 http/jmx
EXPOSE  8080 10334 40404 1099 7070

# Run this command after the container is built 
CMD ./rungeode.sh
