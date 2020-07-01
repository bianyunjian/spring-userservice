FROM java:8
VOLUME /tmp
ADD target/user-service-0.0.1-SNAPSHOT.jar /app.jar
ENV TimeZone Asia/Shanghai
RUN bash -c  'touch /app.jar'
EXPOSE 9091
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${EXT_CONFIG}"]