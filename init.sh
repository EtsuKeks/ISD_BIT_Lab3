#!/bin/bash
sudo docker build -t karimullin_builder_image -f Dockerfile_builder .
sudo docker create -it --name karimullin_builder karimullin_builder_image bash
sudo docker cp karimullin_builder:/app ./app
sudo docker rm -f karimullin_builder
sudo docker build -t karimullin_ansible_image -f Dockerfile_ansible .
sudo docker-compose -p 'karimullin_proj' up