FROM java:8-jre

MAINTAINER wangiegie@gmail.com

#RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pigx-tx-manager

WORKDIR /pigx-tx-manager

EXPOSE 25004
EXPOSE 29998

ADD ./pigx-visual/pigx-tx-manager/target/pigx-tx-manager.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-tx-manager.jar
