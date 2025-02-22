# Spring Boot와 Docker Compose를 이용한 베이스 백엔드 서버
### 구축 사항
1. 자체 로그인 (java security)
2. Swagger Ui
3. MariaDb
4. Spring Boot

※ Base_FrontEnd 구현 필요
1. 현재 샘플코드로 `Flask`를 이용한 기본적인 프론트 서버 환경만 작성되어있다. [(링크)](https://github.com/junni01kim/FrontEnd)
2. 해당 코드는 [Docker 공식문서](https://docs.docker.com/compose/gettingstarted/)의 기본 `Dockerfile`이다.

# AWS 개설 후 해야할 것
1. IntelliJ와 동일한 JDK를 일치시킨다. (현재 correntto-21)
2. Docker Download
3. Docker Compose Download
4. mariadb 유저 권한 설정

## Git 설치 방법
[Linux Git Download](https://medium.com/@dassandeep0001/how-to-install-git-in-ec2-instance-1bfeb1cc9dc9)
```bash
# 모든 패키지를 업데이트
sudo yum update -y

# git 설치
sudo yum install git -y

# git 설치 확인
git — version
```

## Docker 설치 방법
[Amazon Linux 2024 Download for Docker](https://docs.aws.amazon.com/ko_kr/serverless-application-model/latest/developerguide/install-docker.html)
__링크만으론 sudo 없이 docker ps 사용안됨__  
이후 `$ newgrp docker` 이용할 것
```bash
# 모든 패키지를 업데이트
sudo yum update -y 

# Amazon Linux 2023 Docker 다운로드 방법
sudo yum install -y docker

# Docker 서비스 실행
sudo service docker start
```

# Docker 서비스 상시 실행 Docker Compose를 이용한 베이스 백엔드 서버
### 구축 사항
1. 자체 로그인 (java security)
2. Swagger Ui
3. MariaDb
4. Spring Boot

※ Base_FrontEnd 구현 필요
1. 현재 샘플코드로 `Flask`를 이용한 기본적인 프론트 서버 환경만 작성되어있다. [(링크)](https://github.com/junni01kim/FrontEnd)
2. 해당 코드는 Docker 공식문서의 기본 `Dockerfile`이다.

# AWS 개설 후 해야할 것
1. IntelliJ와 동일한 JDK를 일치시킨다. (현재 correntto-21)
2. Docker Download
3. Docker Compose Download
4. mariadb 유저 권한 설정

## Git 설치 방법
[Linux Git Download](https://medium.com/@dassandeep0001/how-to-install-git-in-ec2-instance-1bfeb1cc9dc9)
```bash
# 모든 패키지를 업데이트
sudo yum update -y

# git 설치
sudo yum install git -y

# git 설치 확인
git — version
```

## Docker 설치 방법
[Amazon Linux 2024 Download for Docker](https://docs.aws.amazon.com/ko_kr/serverless-application-model/latest/developerguide/install-docker.html)
__※ 링크만으론 `sudo` 없이 `docker ps` 사용안됨__  
```bash
# 모든 패키지를 업데이트
sudo yum update -y 

# Amazon Linux 2023 Docker 다운로드 방법
sudo yum install -y docker

# 도커 서비스 상시 실행 및 sudo 키워드 생략
sudo service docker start
sudo usermod -a -G docker ec2-user
newgrp docker

# Docker 설치 확인
docker ps
```

## Docker Compose 설치 방법
[Amazon Linux 2024 Download for Docker Compose](https://gist.github.com/npearce/6f3c7826c7499587f00957fee62f8ee9)
```bash
sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

# docker-compose 실행 권한(+x) 추가
sudo chmod +x /usr/local/bin/docker-compose

# Docker Compose 설치 확인
docker-compose version
```

## Amazon Corretto Download
[JAVA 21 설치 정리](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html)
1. tar파일 압축 해제
2. Java 파일 위치 지정
```bash
curl -LO https://corretto.aws/downloads/latest/amazon-corretto-21-x64-linux-jdk.tar.gz

tar -zxvf amazon-corretto-21-x64-linux-jdk.tar.gz

# 압축 해제된 JDK 디렉토리명 수정 및 디렉토리 이동
mv amazon-corretto-21.0.4.7.1-linux-x64/ JAVA21/

rm amazon-corretto-21-x64-linux-jdk.tar.gz
```

[자바 환경변수 설정](https://zetawiki.com/wiki/%EB%A6%AC%EB%88%85%EC%8A%A4_$JAVA_HOME_%ED%99%98%EA%B2%BD%EB%B3%80%EC%88%98_%EC%84%A4%EC%A0%95#google_vignette)
1. 압축 해제된 파일 위치에 환경변수 설정
```bash
# 1. 환경 변수 임시 적용방법
# JAVA_HOME 환경변수를 설치한 `JAVA21` 디렉토리로 설정
export JAVA_HOME=/home/ec2-user/JAVA21

# 2. 환경 변수 영구 적용방법
sudo vi /etc/environment
# 파일에 해당 텍스트 추가 
JAVA_HOME="/home/ec2-user/JAVA21"
PATH="$JAVA_HOME/bin:$PATH"

# 변경사항 적용
source /etc/environment

# JDK 및 환경변수 설정 확인
$JAVA_HOME/bin/javac -version
```

## 깃 클론
백엔드 다운로드
```bash
git clone https://github.com/junni01kim/BackEnd.git
```

프론트엔드 다운로드(임시)
```bash
git clone https://github.com/junni01kim/FrontEnd.git
```

## 프로젝트 실행
```bash
cd ~/BackEnd/

# `./gradlew` 실행 기준 처음에만 실행한다.
# gradlew 실행 권한(+x) 추가 (추후 repo 단계에서 수정 예정)
chmod +x ./gradlew 

# docker-compose가 실행되어 있다면, 전부 종료
docker-compose down

# 새로운 JAR 파일 생성
# `docker-compose up -d` 이전 `grrdle`에 Jar파일이 생성되지 않기 때문에, `docker-compose up` 이전에 필수적으로 진행해야 하는 작업
./gradlew clean build

# 새로운 JAR 파일을 `image`화 후 실행
docker-compose up -d
```

## BackEnd DB접근 오류 (초기 1회 127.\*.0.\* 권한 없음)
1. 최초 실행 시, `127.\*.0.\*` 데이터베이스 접근 불가로, 접속 ip 개방 SQL 코드 작성이 필요하다.
2. `image`를 통해 `mariadb`가 생성되면, 필요한 설정되지 않은 채로 개설되기 때문이다. (추후 수정 예정)
```bash
# mariadb docker 실행 상태에서 진행
# BackEnd는 Error 발생으로 exit(1)된 상태이다.
docker ps

# mariadb 컨테이너 접속
docker exec -it maria_db /bin/bash

mariadb -u root -p 
# 비밀번호는 존재하지 않음(Enter)

use mysql;
select user, host from user;

# 모든 접속지(ip)에서 모든 권한을 부여하는 것
# ※ 보안의 취약성이 존재해 수정 예정
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root1234' WITH GRANT OPTION;
```

## ※주의 사항※
__※중요※__
ec2 프리티어 t2.micro는 도커 실행 시 __인스턴스 과부하로__ 작동이 멈추기 때문에, 멈췄다면 __인스턴스 중지__ 후 __다시 시작__ 해야 한다.

# AWS 적용 방법

### 1. Git Pull로 코드 업데이트
```sh
cd ~/mazzip_backend/
git pull origin
```

### 2. Docker Compose 중지 및 컨테이너 제거
```sh
docker-compose down
```

### 3. Docker 시스템 정리 (불필요한 데이터 제거)
```sh
docker system prune -a -f
```

### 4. 프로젝트 빌드
```sh
./gradlew build
```

### 5. Docker Compose 실행 + 이미지 생성
```sh
docker-compose up -d --build
```

### 9. 배포 상태 확인
```sh
#로그 확인
docker-compose logs -f

#컨테이너 목록 확인
docker container ls -al
```

