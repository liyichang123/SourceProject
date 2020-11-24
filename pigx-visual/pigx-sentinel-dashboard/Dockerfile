FROM java:8-jre

MAINTAINER wangiegie@gmail.com

#RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /pigx-sentinel-dashboard

WORKDIR /pigx-sentinel-dashboard

EXPOSE 25020

ADD ./pigx-visual/pigx-sentinel-dashboard/target/pigx-sentinel-dashboard.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-sentinel-dashboard.jar
