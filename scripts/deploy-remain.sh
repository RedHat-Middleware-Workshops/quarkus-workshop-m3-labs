#!/bin/bash

USERXX=$1

if [ -z "$USERXX" -o "$USERXX" = "userXX" ]
  then
    echo "Usage: Input your username like deploy-remain.sh user1"
    exit;
fi

echo Your username is $USERXX

oc project $USERXX-project

sh ${PROJECT_SOURCE}/scripts/deploy-customers-service.sh
sh ${PROJECT_SOURCE}/scripts/deploy-visits-service.sh
sh ${PROJECT_SOURCE}/scripts/deploy-petclinic-web-v2.sh

oc rollout status -w dc/customers-service &&
  oc rollout status -w dc/visits-service &&
  oc rollout status -w dc/petclinic-web-v2