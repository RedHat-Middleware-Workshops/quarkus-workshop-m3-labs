#!/bin/bash

echo Deploy deploy-petclinic-web-v2........

mvn clean package -DskipTests -f ${PROJECT_SOURCE}/quarkus-petclinic-web-v2