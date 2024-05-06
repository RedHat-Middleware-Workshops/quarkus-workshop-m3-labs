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

sh ${PROJECT_SOURCE}/scripts/deploy-vets-service.sh
sh ${PROJECT_SOURCE}/scripts/deploy-customers-service.sh
sh ${PROJECT_SOURCE}/scripts/deploy-visits-service.sh
sh ${PROJECT_SOURCE}/scripts/deploy-petclinic-web-v2.sh

oc rollout status -w deployment/vets-service &&
  oc rollout status -w deployment/customers-service &&
  oc rollout status -w deployment/visits-service &&
  oc rollout status -w deployment/petclinic-web-v2