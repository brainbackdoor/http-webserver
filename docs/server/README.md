
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

web.server package에 web server를 구현하였다. 
구동 후 브라우저에서 localhost:8080/index.html 를 올려 확인할 수 있다.
기존에 builder pattern을 직접 구현했던 부분을 lombok을 사용했기 때문에 이에 대한 조치를 해주어야 할 수도 있다.

> https://docs.spring.io/spring/docs/3.0.0.M4/spring-framework-reference/html/ch15s02.html

---
1) 커넥션을 맺는다
ServerSocket을 사용하여 구현하였다.

[ClientSocket vs ServerSocket](https://recipes4dev.tistory.com/153)

ServerSocket에는 shutdownInput이란 메서드가 있는데, 4장에서 gracefully shutdown과 연관된 개념이라 함께 보는 것을 추천한다.
https://stackoverflow.com/questions/15206605/purpose-of-socket-shutdownoutput

---
2) 요청 메시지 수신

요청줄을 파싱하여 요청 메서드, 지정된 리소스의 식별자(URI), 버전번호를 찾는다. 
메시지 헤더들을 읽는다.
헤더의 끝을 의미하는 빈 줄을 찾아낸다.
요청 본문이 있다면 읽어들인다. (길이는 Content-Length 헤더로 정의된다.)

관련하여 web.protocol.http.message 패키지의 내용을 확인해봐도 좋다. 
특히 ResponseMessageFactory 클래스에서 응답 패킷을 만들며 해당 부분을 작성하였으니 확인해봐도 좋겠다.

```
public static void response(ResponseMessage message, DataOutputStream dos) throws IOException {
        String responseLine = getResponseLine(message);
        String headers = getHeader(message);
        dos.writeBytes(responseLine);
        dos.writeBytes(headers);
        dos.write(message.getEntityBody().getBody(), 0, message.getEntityBody().getBody().length);
        dos.flush();
    }
```

커넥션 입력/출력 처리 아키텍처
단일 스레드  웹 서버, 멀티프로세스와 멀티프로세스 웹서버 등에 대한 비교는 [Apache MPM vs Nginx](https://brainbackdoor.tistory.com/28)로 대체한다.

---

3) 요청 처리

> https://docs.spring.io/spring/docs/3.0.0.M4/spring-framework-reference/html/ch15s02.html

요청에 대한 처리는 DispatcherServlet의 동작방식에 영감을 얻어 작성하였다.
현재는 RequestResolver class에서 index.html,style.css 등 파일에 대한 요청인지 /users와 같이  Controller에 대한 요청인지를 판단하며
그 이후  ControllerResolver, ResourceResolver가 URL에 맞는 Controller를 리턴하거나, 파일을 읽어 리턴하는 구조로 설계하였다.

---

4) 리소스의 매핑과 접근
(1) Docroot 
Apache web server는 DocumentRoot를 기준으로 문서를 읽으며  그 상위의  경로는 읽기를 허용하지 않도록, Directory listing을 허용하지 않도록 설정해야 한다.

```
<VirtualHost *:80>
  DocumentRoot /data/www/hub
  ServerName www.brainbackdoor.com
  <Location />
      RedirectMatch /(.*)$ https://www.brainbackdoor.com/$1
  </Location>
  ErrorLog "|/usr/sbin/cronolog /data/log/httpd/`hostname`/woowa.brainbackdoor.com/woowa.brainbackdoor.com_error_log_%Y%m%d"
  CustomLog "|/usr/sbin/cronolog /data/log/httpd/`hostname`-time/woowa.brainbackdoor.com/woowa.brainbackdoor.com-%Y%m%d-%H" securityteam
</VirtualHost>
```
(2) DirectoryIndex

웹서버의 디렉토리 접근시에 DirectoryIndex에서 지정한 파일(index.html 등)을 반환한다. 

(3) 동적 컨텐츠 리소스 매핑

특정 요청에 대해 ScriptAlias 속성을 통해 CGI 스크립트를 특정한 위치에서만 제공하도록 설정할 수 있다. 

(4) 접근제어

웹 서버는 클라이언트의 IP 주소에  근거하여 접근을 제어할 수 있고 특정 리소스에 접근하는  데에 있어 비밀번호를 물어볼 수 있다.

---

5) 응답 만들기

(1) 응답 엔터티 : ResponseMessage

- 응답 본문의 MIME 타입을 서술하는 Content-Type 헤더 (미구현)
- 응답 본문의 길이를 서술하는 Content-Length 헤더
- 실제 응답 본문의 내용

(2) 리다이렉션  

- Location 응답헤더를적극적으로 활용한다.

 

