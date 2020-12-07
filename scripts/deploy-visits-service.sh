#!/bin/bash

echo Deploy visits-service........

oc new-app -e POSTGRESQL_USER=visits \
  -e POSTGRESQL_PASSWORD=mysecretpassword \
  -e POSTGRESQL_DATABASE=visits openshift/postgresql:latest \
  --name=visits-database

mvn clean package -DskipTests -f $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/quarkus-petclinic-visits-service

oc label dc/visits-database app.openshift.io/runtime=postgresql --overwrite && \
oc label dc/visits-database app.kubernetes.io/part-of=visits-app --overwrite