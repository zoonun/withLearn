# AWS setting

# EC2 생성

EC2 ubuntu18.04 LTS 설치

인바운드 규칙

![AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled.png](AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled.png)

![AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled%201.png](AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled%201.png)

22 port : ssh 접속용으로 개방

3306 port : mysql 사용을 위해 개방

3478 port (UDP, TCP) & 49152-65535 port (UDP) : coturn 사용을 위해 개방

# Docker 생성

```
sudo apt-get update

sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get install docker-ce docker-ce-cli containerd.io

sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose
```

# Kurento 컨테이너 생성

```jsx
kurento 최신 버전 설치

docker pull kurento/kurento-media-server:latest

kurento 컨테이너 kms라는 이름 & deamon으로 실행
sudo docker run -d --name kms --network host kurento/kurento-media-server:latest
```

# STUN/TURN 서버 설치

```jsx
coturn 설치
sudo apt-get install --no-install-recommends --yes coturn

/etc/default/coturn 파일 수정
TURNSERVER_ENABLED=1 주석 해제

/etc/turnserver.conf 파일 수정
listening-port=3478 주석 해제
tls-listening-port=5349 주석 해제
listening-ip=(EC2의 프라이빗 Ipv4 주소) 주석 해제 및 수정
external-ip=(EC2의 퍼블릭 Ipv4 주소)/(EC2의 프라이빗 Ipv4 주소) 주석 해제 및 수정
relay-ip=(EC2의 프라이빗 Ipv4 주소) 주석 해제 및 수정
fingerprint 주석 해제
lt-cred-mech  주석 해제
user=myuser:mypassword 주석 해제 및 수정
realm=myrealm 주석 해제 및 수정
log-file=/var/tmp/turn.log 주석 해제
simple-log 주석 해제

 Coturn 서버 재기동
sudo service coturn restart
```

# Docker 컨테이너 접속

![AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled%202.png](AWS%20setting%20bed471ca47c44ff7b16e4a29e0c30059/Untitled%202.png)

```jsx
접속후
apt-get update
apt-get install vim

vi /etc/kurento/modules/kurento/WebRtcEndpoint.conf.ini

stunServerAddress=(EC2의 퍼블릭 Ipv4 주소) 주석 해제 및 수정
stunServerPort=3478 주석해제
turnURL=myuser:mypassword@(EC2의 퍼블릭 Ipv4 주소)?transport=udp 주석 해제 및 수정
```
