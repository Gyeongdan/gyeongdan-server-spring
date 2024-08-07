FROM amazoncorretto:17

# 빌드 시 인자를 받기 위한 ARG 설정
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG JWT_SECRET_KEY
ARG KAKAO_CLIENT_ID
ARG KAKAO_REDIRECT_URI
ARG OPENAI_API_KEY

# 환경 변수 설정
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
ENV JWT_SECRET_KEY=$JWT_SECRET_KEY
ENV KAKAO_CLIENT_ID=$KAKAO_CLIENT_ID
ENV KAKAO_REDIRECT_URI=$KAKAO_REDIRECT_URI
ENV OPENAI_API_KEY=$OPENAI_API_KEY

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", "-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME}", "-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", "-Dspring.jpa.properties.hibernate.default_schema=${SPRING_DATASOURCE_SCHEMA}","-Djwt.secret.key=${JWT_SECRET_KEY}","-jar","-Dkakao.client.id=${KAKAO_CLIENT_ID}", "-Dkakao.redirect.uri=${KAKAO_REDIRECT_URI}","-Dopenai.api.key=${OPENAI_API_KEY}","/app.jar"]
