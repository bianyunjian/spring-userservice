FROM java:8
VOLUME /tmp
ADD target/user-service-0.0.1-SNAPSHOT.jar /app.jar
ENV TimeZone Asia/Shanghai
RUN bash -c  'touch /app.jar'
EXPOSE 9091
ENTRYPOINT ["java","-Xmx1024m","-Xms512m","-jar","/app.jar"]