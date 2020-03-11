# 使用aws 的 openjdk8
FROM amazoncorretto:11.0.6
# 作者署名
MAINTAINER Fengtoos
# 挂载的容器磁盘
VOLUME ["/tmp"]
# 使用东八区时间 环境 其他环境参数也可以通过ENV 声明
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#  add 命令是 将主机构建环境（上下文）目录中的文件和目录、以及一个URL标记的文件 拷贝到镜像中
ADD shop-admin-1.0.0.jar app.jar
# 暴露端口 跟springboot 默认应用端口一样
EXPOSE 8080
#  启动镜像就执行的命令操作   相当于执行  java -jar  app.jar  -Djava.security.egd=file:/dev/./urandom     最后的指令是为了加快启动
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]