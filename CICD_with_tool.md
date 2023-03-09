# 툴을 사용한 CI/CD

# CI/CD

---

**Github Actions**

- Github에서 제공하며, 워크플로우를 설정함으로써 코드를 빌드, 테스트하고 외부 저장소로 코드를 전송하는 작업을 할 수 있다.
- 다른 툴과 비교하여 따로 설치나 클라우드를 만들지 않더라도 보유한 레포지터리에서 바로 진행 가능하다는 장점이 있어서 채택하였다.

**AWS CodeDeploy**

- AWS에서 제공하는 CD 서비스로 다른 AWS 서비스와 연동이 쉽다는 장점이 있다.
- AWS EC2를 통해 프로젝트의 서비스 서버를 구성하기 때문에 CodeDeploy를 이용하여 CD를 구성하였다.

**CI/CD 파이프 라인**

![제목 없는 다이어그램.drawio (4).png](%E1%84%90%E1%85%AE%E1%86%AF%E1%84%8B%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%A1%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20CI%20CD%205b0b7e909080452ab717d574edb72257/%25EC%25A0%259C%25EB%25AA%25A9_%25EC%2597%2586%25EB%258A%2594_%25EB%258B%25A4%25EC%259D%25B4%25EC%2596%25B4%25EA%25B7%25B8%25EB%259E%25A8.drawio_(4).png)

- Github로 소스코드를 Push 하면 Github Actions에서 테스트와 빌드를 하고 빌드 결과물을 압축하여 S3에 업로드 한다.
- Github Actions에서 CodeDeploy로 배포 요청을 보낸다.
- 요청을 받은 CodeDeploy는 S3로부터 압축된 파일을 EC2로 옮기고, 이를 실행하여 Spring Boot 서버를 실행한다. (EC2에는 CodeDeploy Agent와 Java가 설치되어 있어야 한다.)