#!/bin/bash

USERXX=$1

if [ -z "$USERXX" -o "$USERXX" = "userXX" ]
  then
    echo "Usage: Input your username like deploy-all.sh user1"
    exit;
fi

echo Your username is $USERXX

oc delete deployments,dc,bc,build,svc,route,pod,is --all
echo "Waiting 30 seconds to finialize deletion of resources..."
sleep 30

oc project $USERXX-project

sh $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/scripts/deploy-customers-service.sh
sh $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/scripts/deploy-customers-service.sh
sh $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/scripts/deploy-visits-service.sh
sh $CHE_PROJECTS_ROOT/quarkus-workshop-m3-labs/scripts/deploy-petclinic-web-v2.sh

oc rollout status -w dc/vets-service &&
  oc rollout status -w dc/customers-service &&
  oc rollout status -w dc/visits-service &&
  oc rollout status -w dc/petclinic-web-v2