# openJDK8이 설치된 기본 이미지를 사용
# 백엔드를 빌드한 jar파일을 /app.jar로 복사
# 자바 추가 옵션은 없으며, 최종적으로 "java -jar /app.jar" 명령을 실행

#FROM openjdk:8-jdk-alpine AS builder
FROM openjdk:8-jdk-alpine
ADD ./build/libs/*.jar app.jar
#COPY --from=builder ./backend/build/libs/*.jar app.jar
#ENV JAVA_OPTS="-Dkms.url=ws://3.35.0.121:8888/kurento"
ENTRYPOINT ["java", "-jar", "/app.jar"]