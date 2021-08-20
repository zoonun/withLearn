# vue.js 프론트 빌드만을 위해 node 이미지를 사용하여 작업하는 과정
### vue.js 빌드만을 위한 node 이미지를 사용
### 작업 디렉토리를 /hompage로 설정한 후,
### 패키지 설치를 위해 package.json을 작업 디렉토리로 옮긴 후 설치
### 나머지 프론트 소스코드와 리소스를 복사하여 빌드 진행
FROM node:lts-alpine as build-stage
WORKDIR /homepage
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
# 빌드한 프론트 결과물을 nginx 이미지에 옮겨서 실행하는 과정
### 필요없는 기존 conf 문서를 삭제하고, nginx에 설정하기 위한 conf를 복사
### build-stage(=상위 node 이미지)에서 빌드한 결과물을 nginx로 옮긴 후,
### 80번 포트 개방과 "nginx -g daemon off;" 명령을 실행
FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./default.conf /etc/nginx/conf.d/default.conf
#COPY ./nginx/homepage.conf /etc/nginx/conf.d/homepage.conf
#COPY ~/S05P13D106/backend/src/main/resources/dist ./homepage/dist
COPY --from=build-stage ./frontend/homepage/dist /usr/share/nginx/html
#COPY ./homepage/dist /usr/share/nginx/html/homepage
EXPOSE 80
EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]
