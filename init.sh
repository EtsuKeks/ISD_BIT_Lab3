#!/bin/bash

sudo docker build -t karimullin_ansible_image -f Dockerfile_ansible .

cd jar_builder
sudo docker build -t build_container .
# sudo docker build --no-cache -t build_cont .
sudo docker run --rm -v karimullin_jar_storage:/ISD_BIT_Lav3 -d build_container
cd ..

sudo docker-compose -p 'karimullin_proj' up