#!/bin/bash

echo Deploy vets-service........

oc delete all -l app=vets-database

oc new-app openshift/postgresql:10-el8 \
            --name=vets-database \
            -e POSTGRESQL_USER=vets \
            -e POSTGRESQL_PASSWORD=mysecretpassword \
            -e POSTGRESQL_DATABASE=vets 

mvn clean package -DskipTests -f ${PROJECT_SOURCE}/quarkus-petclinic-vets-service

oc label deployment/vets-database app.openshift.io/runtime=postgresql --overwrite && \
oc label deployment/vets-database app.kubernetes.io/part-of=vets-app --overwrite