
# 5장 웹 서버

- 어떻게 웹 서버가 HTTP 트랜잭션을 처리하는지 단계별로 설명한다.
- HTTP 통신을 진단해주는 간단한 웹 서버를 작성해본다.

---

## 웹 서버가  하는  일

1) 커넥션을 맺는다 - 클라이언트의 접속을 받아들이거나, 원치않는 클라이언트라면 닫는다.
2) 요청을 받는다 - HTTP 요청 메시지를 네트워크로부터 읽어들인다.
3) 요청을 처리한다 - 요청 메시지를 해석하고 행동을 취한다.
4) 리소스에 접근한다 - 메시지에서 지정한 리소스에 접근한다.
5) 응답을 만든다 - 응답을 클라이언트에게 돌려준다.
6) 응답을 보낸다 - 응답을 클라이언트에게 돌려준다
7) 트랜잭션을 로그로 남긴다 - 로그파일에 트랜잭션 완료에 대한 기록을 남긴다

---
