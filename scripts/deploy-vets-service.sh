#!/bin/bash

echo Deploy vets-service........

oc new-app openshift/postgresql:latest \
            --name=vets-database \
            -e POSTGRESQL_USER=vets \
            -e POSTGRESQL_PASSWORD=mysecretpassword \
            -e POSTGRESQL_DATABASE=vets 

mvn clean package -DskipTests -f $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/quarkus-petclinic-vetsvets-service

oc label dc/vets-database app.openshift.io/runtime=postgresql --overwrite && \
oc label dc/vets-database app.kubernetes.io/part-of=vets-app --overwrite