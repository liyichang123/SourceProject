FROM java:8-jre

MAINTAINER wangiegie@gmail.com

#RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


RUN mkdir -p /pigx-daemon-elastic-job

WORKDIR /pigx-daemon-elastic-job

EXPOSE 25002

ADD ./pigx-visual/pigx-daemon-elastic-job/target/pigx-daemon-elastic-job.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar pigx-daemon-elastic-job.jar
