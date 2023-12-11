#!/bin/bash
sudo docker create -t karimullin_jar_holder busybox
sudo docker build -t karimullin_build_container -f Dockerfile_jar_builder .
sudo docker run --rm -v "$(pwd)/jar_storage":/ISD_BIT_Lab3 -d karimullin_build_container
sudo docker build -t karimullin_ansible_image -f Dockerfile_ansible .
sudo docker-compose -p 'karimullin_proj' up