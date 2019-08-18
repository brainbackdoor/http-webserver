
> [2장 URL과 리소스](../url/README.md)

---

# 3장 HTTP 메시지

- 메시지가 어떻게 흘러가는가
- HTTP 메시지의 세 부분(시작줄, 헤더, 개체 본문)
- 요청과 응답 메시지의 차이
- 요청 메시지가 지원하는 여러 기능(메서드)들
- 응답 메시지가 반환하는 여러 상태 코드듣
- 여러 HTTP 헤더들은 무슨 일을 하는가

---

## HTTP Message

- 메시지는 (클라이언트로부터의) 요청 메시지나 (서버로부터의) 응답 메시지로 분류된다.
- Message는 RequestLine/ResponseLine, Header, EntityBody로 구성된다. 
- RequestLine/ResponseLine과 Header는 줄바꿈 문자열로 끝난다.
- EntityBody는 Text나 Binary Data를 포함할 수도 있고 그냥 비어있을 수도 있다.
- Header는 EntityBody에 대한 정보를 준다.
    - Copntent-Type은 본문이 무엇인지 알려준다.
    - Content-Length는 본문의 크기를 말해준다.

---
#### RequestMessage
- RequestMessage는 웹 서버에 어떤 동작을 요구한다.
- RequestMessage는 다음과 같이 구성된다.
```
<메서드> <요청 URL> <버전>
<헤더>

<엔티티 본문>
```

#### RequestLine
- Method, RequestURL, Version으로 구성된다.
- 각 필드는 공백으로 구분된다.

#### Method
- 서버가 리소스에 대해 수행해주길 바라는 동작

|메서드|설명|메시지 본문이 있는가?|
|---|---|---|
|GET|서버에서 어떤 문서를 가져온다.||
|HEAD|서버에서 어떤 문서에 대해 헤더만 가져온다.||
|POST|서버가 처리해야 할 데이터를 보낸다.|O|
|PUT|서버에 요청 메시지의 본문을 저장한다.|O|
|TRACE|메시지가 프락시를 거쳐 서버에 도달하는 과정을 추적한다.||
|OPTIONS|서버가 어떤 메서드를 수행할 수 있는지 확인한다.||
|DELETE|서버에서 문서를 제거한다.||


- GET과 HEAD 요청으로 서버에 어떤 작용도 없으므로 안전하다.
- HEAD는 GET처럼 행동하지만 서버는 응답으로 헤더만 돌려준다. (엔터티 본문은 반환하지 않는다.)
    - 리소스를 가져오지 않고도 상태코드를 통해 개체가 존재하는지, 리소스가 변경되었는지를 검사할 수 있다.
- PUT은 서버가 요청의 본문을 가지고 요청 URL의 이름대로 새 문서를 만들거나, 이미 URL이 존재한다면 본문을 사용해서 교체한다.
- POST는 서버에 입력 데이터를 전송하기 위해 설계되었다.
- TRACE
  클라이언트가 어떤 요청을 할 때, 그 요청은 방화벽, 프락시, 게이트웨이 등의 애플리케이션을 통과할 수 있다. 
  이들에게는 원래의 HTTP 요청을 수정할 수 있는 기회가 있다.
  TRACE는 클라이언트에게 자신의 요청이 서버에 도달했을 때 어떻게 보이게 되는지 알려준다.
  
  목적지 서버에서 loopback 진단을 시작한다.
  프락시는 POST 요청을 바로 서버로 통과시키는 반면 GET 요청은 웹 캐시와 같이 다른 HTTP 애플리케이션으로 전송한다.
  TRACE는 메서드를 구별하는 메커니즘을 제공하지 않는다. 
  
    ```
    client >> proxy
    TRACE /product-list.txt HTTP/1.1
    Accept: *
    Host: www.joes-hardware.com
    
    proxy >> www.joes-hardware.com server
    TRACE /product-list.txt HTTP/1.1
    Host: www.joes-hardware.com
    Accept: *
    Via 1.1 proxy3.company.com
    
    server >> proxy
    HTTP/1.1 200 OK
    Content-type: text/plain
    Conent-length: 96
    
    TRACE /product-list.txt HTTP/1.1
    Host: www.joes-hardware.com
    Accept: *
    Via 1.1 proxy3.company.com
    
    proxy >> client
    HTTP/1.1 200 OK
    Content-type: text/plain
    Conent-length: 96
    
    TRACE /product-list.txt HTTP/1.1
    Host: www.joes-hardware.com
    Accept: *
    Via 1.1 proxy3.company.com
    ```
- OPTIONS는 웹 서버에게 특정 리소스에 대해 어떤 메서드가 지원되는지 물어본다.
- DELETE는 지정한 리소스를 삭제할 것을 요청한다.

#### RequestURL
- 요청 대상이 되는 리소스를 의미한다.
- 완전한 URL이 아닐 경우 호스트/포트가 자신을 가리키는 것으로 간주한다.

#### Version
- ```HTTP/<메이저>.<마이너>```
- Major, Minor 모두 정수이다.

 
---
#### ResponseMessage
- ResponseMessage는 요청의 결과를 클라이언트에게 돌려준다.
- ResponseMessage는 다음과 같이 구성된다.
```
<버전> <상태코드> <사유 구절>
<헤더>

<엔티티 본문>
```
#### ResponseLine
- Version, StatusCode, ReasonPhrase
- 각 필드는 공백으로 구분된다.

#### StatusCode

- 요청 중에 무엇이 일어났는지를 설명하는 세자리 숫자

|정의된 범위|분류|
|---|---|
|100-101|정보|
|200-206|성공|
|300-305|리다이렉션|
|400-415|클라이언트 에러|
|500-505|서버 에러|

#### ReasonPhrase
- 숫자로 된 상태 코드의 의미를 설명하는 짧은 문구

---

#### Header
- 이름/값 쌍의 목록이다.

- 일반 헤더, 요청 헤더, 응답 헤더, 엔터티 헤더 등이 있다.

#### EntityBody
- Header나 EntityBody가 없더라도 HTTP Header 집합은 항상 빈 줄로 끝나야 한다.