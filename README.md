#艹了  傻逼git  浪费我3天时间解决问题

#Linux服务器   账号：root  密码：XSJroot123456
#宝塔服务管理 http://8.137.39.241:8888/c36099f2  账号：14708224247  密码：Root123456
#docker中mysql  账号：root 密码123456
#docker中单机启动的nacos 地址http://8.137.39.241:8848/nacos/   账号nacos 密码nacos


#创建一个网络给nacos集群
docker network create -d bridge nacos-cluster
#将nacos的数据库放到nacos网络上
docker network connect nacos-cluster mysql
#启动mysql
docker run --name mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7

##启动nacos
export CNAME=nacos1
docker rm -f ${CNAME}
docker volume rm -f ${CNAME}-logs
docker volume create ${CNAME}-logs
docker run -d --name ${CNAME} \
--network=nacos-cluster --restart=always \
--hostname=${CNAME} \
-e PREFER_HOST_MODE=hostname \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=mysql \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_PARAM="characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true" \
-e NACOS_AUTH_IDENTITY_KEY=2222 \
-e NACOS_AUTH_IDENTITY_VALUE=2xxx \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=16m \
-v ${CNAME}-logs:/home/nacos/logs \
-p 8847:8848 -p 9847:9848 -d \
nacos/nacos-server:v2.2.1-slim




export CNAME=nacos2
docker rm -f ${CNAME}
docker volume rm -f ${CNAME}-logs
docker volume create ${CNAME}-logs
docker run -d --name ${CNAME} \
--network=nacos-cluster --restart=always \
--hostname=${CNAME} \
-e PREFER_HOST_MODE=hostname \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=mysql \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_PARAM="characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true" \
-e NACOS_AUTH_IDENTITY_KEY=2222 \
-e NACOS_AUTH_IDENTITY_VALUE=2xxx \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=16m \
-v ${CNAME}-logs:/home/nacos/logs \
-p 8848:8848 -p 9848:9848 -d \
nacos/nacos-server:v2.2.1-slim



export CNAME=nacos3
docker rm -f ${CNAME}
docker volume rm -f ${CNAME}-logs
docker volume create ${CNAME}-logs
docker run -d --name ${CNAME} \
--network=nacos-cluster --restart=always \
--hostname=${CNAME} \
-e PREFER_HOST_MODE=hostname \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=mysql \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_PARAM="characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true" \
-e NACOS_AUTH_IDENTITY_KEY=2222 \
-e NACOS_AUTH_IDENTITY_VALUE=2xxx \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=16m \
-v ${CNAME}-logs:/home/nacos/logs \
-p 8849:8848 -p 9849:9848 -d \
nacos/nacos-server:v2.2.1-slim


#docker 启动rabbitMQ
#启动mq的时候把mq加到nacos网络中  防止发现不了服务
docker run -d  \
--network=nacos-cluster --restart=always \
--hostname rabbit \
--name rabbit \
-p 15672:15672 -p 5672:5672 \
-e RABBITMQ_DEFAULT_USER=rabbit \
-e RABBITMQ_DEFAULT_PASS=Root123456 \
-e RABBITMQ_DEFAULT_VHOST=rabbit  rabbitmq:management