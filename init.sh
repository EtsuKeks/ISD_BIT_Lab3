#!/bin/bash
sudo rm -rf app
sudo docker rm $(docker ps -a -q)
sudo docker-compose down -v
sudo docker build -t karimullin_builder_image -f Dockerfile_builder .
sudo docker create -it --name karimullin_builder karimullin_builder_image bash
sudo docker cp karimullin_builder:/ISD_BIT_Lab3 ./app
sudo docker rm -f karimullin_builder
sudo docker build -t karimullin_ansible_image -f Dockerfile_ansible .
sudo docker build -t karimullin_servers_image -f Dockerfile_servers .
sudo docker-compose -p 'karimullin_proj' up --force-recreate

# В mysql поменять etc/mysql/mariadb.conf.d/50-server.cnf  на 0.0.0.0,
# USE mydatabase;
# CREATE USER 'user'@'172.18.0.2' IDENTIFIED BY 'password';
# GRANT ALL PRIVILEGES ON mydatabase.* TO 'user'@'172.18.0.2' WITH GRANT OPTION;