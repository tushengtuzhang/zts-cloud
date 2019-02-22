## ZTS-CLOUD

#Start up 
0.rabbitMQ,redis,mysql

1.eureka(8761,8762,8763) 注册与发现
java -jar eureka-1.0-SNAPSHOT.jar --spring.profiles.active=zts1
java -jar eureka-1.0-SNAPSHOT.jar --spring.profiles.active=zts2
java -jar eureka-1.0-SNAPSHOT.jar --spring.profiles.active=zts3

2.config(8888,8889) 中心化配置
java -jar config-server-1.0-SNAPSHOT.jar
java -jar config-server-1.0-SNAPSHOT.jar  --spring.profiles.active=backup

3.company(7000) 公司服务
java -jar company-server-1.0-SNAPSHOT.jar  --server.port=7000
java -jar company-server-1.0-SNAPSHOT.jar  --server.port=7001

4.similarity(9000) 相似调用服务 nlp(9010) 自然语言处理服务

5.robot-common(8000) robot公用模块,case(8010) 场景服务 qa(8020) 问答服务 flow(8030) 流程服务 control-center(8080) 控制中心





