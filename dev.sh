#!/usr/bin/env bash

#export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home"
export MAVEN_OPTS="-server -Xms200m -Xmx768m -XX:MaxNewSize=256m -Djava.awt.headless=true  -Dfile.encoding=UTF-8 -Dcom.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager=true"

env

echo ==========

mvn -Dmaven.test.skip=true -Dcatalina.home=/soft/tomcat7 clean jetty:run -P $@