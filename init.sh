#!/bin/bash
sudo docker build -t karimullin_builder -f Dockerfile_builder .
sudo docker run --rm -v "$(pwd)/jar_holder":/app -d karimullin_builder
sudo docker build -t karimullin_ansible_image -f Dockerfile_ansible .
sudo docker-compose -p 'karimullin_proj' up