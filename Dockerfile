FROM amazoncorretto:17

# 빌드 시 인자를 받기 위한 ARG 설정
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

# 환경 변수 설정
ENV SPRING_DATASOURCE_URL={SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME={SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD={SPRING_DATASOURCE_PASSWORD}

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", "-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME}", "-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", "-Dspring.jpa.properties.hibernate.default_schema=${SPRING_DATASOURCE_SCHEMA}", "-jar", "/app.jar"]
