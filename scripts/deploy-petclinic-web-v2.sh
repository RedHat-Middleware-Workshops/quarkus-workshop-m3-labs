#!/bin/bash

echo Deploy deploy-petclinic-web-v2........

mvn clean package -DskipTests -f $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/quarkus-petclinic-web-v2