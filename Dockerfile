FROM alpine:3.11
RUN apk add --no-cache openssh-client ansible git sshpass
RUN mkdir -p /root/.ssh
RUN echo "HOST *" > /root/.ssh/config && echo " StrictHostKeyChecking no" >> /root/.ssh/config
EXPOSE "127.0.0.1:8081:8081"
COPY ./ansible /ansible
WORKDIR /ansible
CMD [""]