4장의 설명은 블로그로 대신한다.

[TCP Connection](https://brainbackdoor.tistory.com/124)

[TCP 오류복구](https://brainbackdoor.tistory.com/125)

[TCP 성능](https://brainbackdoor.tistory.com/126)

[keepalive](https://brainbackdoor.tistory.com/127)

---


## OSI 7 Layer 

- TCP/IP 모델이 국제 표준이 되었지만 OSI 7 Layer는 다른 Protocol Suite와 하드웨어 장비간의 상호작용을 설명하는데에 도움이 되어 네트워크의 전반적인 동작을 설명하는 도구로 자리잡아 교육용과 참조용으로 널리 사용되고 있다.

![](https://techcourse-storage.s3.ap-northeast-2.amazonaws.com/2019/level3/network/osi.jpg)

---
1계층 : Physical Layer 
- 역할: Bit Stream을 전기, 빛 등의 신호로 변환
- PDU: Bit
- 대표장비: 케이블(LAN - UTP, WAN - Serial), 허브, 리피터, 커넥터(LAN - RJ45) 등
- 유형범위: 로컬 장비간에 전송된 전기 또는 빛 신호

---

2계층 : Data-Link Layer
- 역할: 물리적 주소를 이용해 노드(L2 스위치에 연결될 수 있는 3계층 이상의 장비)간 연결
- PDU: Frame
- 대표장비: 브릿지, L2 스위치 등
- 유형범위: 로컬 장비간에 전송된 하위 수준 데이터 메시지
- 프로토콜 및 기술: LAN - Ethernet Protocol, WAN - PPP, HDLC 등

---

3계층 : Network Layer
- 역할: 논리적 주소로 최초 출발지부터 최종 목적지까지의 최적 경로 결정
- PDU: Packet Datagram
- 주소: 논리적 주소 (IP, IPX, Apple Talk)
- 대표장비: 라우터, L3 스위치 등
- 유형범위: 로컬 또는 원격 장비간의 메시지
- 프로토콜 및 기술: IPv4, IPv6, Routing Protocol(RIP, EIGRP, OSPF 등) / ARP와 ICMP에 대해서는 의견이 나뉜다.

---

4계층 : Transport Layer
- 역할: 포트번호를 이용해 서비스를 구분하고 데이터의 전송을 담당
- PDU: Segment
- 주소: Port (Well-known: 0\~1023, 그외: 1024\~65535)
- 대표장비: L4 스위치 등
- 유형범위: 소프트웨어 프로세스간의 통신
- 프로토콜 및 기술: TCP (확인응답-신뢰성), UDP(빠른 속도), NetBEUI

---

5계층 : Session Layer 
- 역할: 응용프로그램간 세션 수립/유지/종료
- 세션: 두 사용자간의 작업 시작부터 끝까지의 실시간 논리적인 연결
- 유형범위: 로컬 또는 원격 장비간의 세션
- 프로토콜 및 기술: NetBIOS, 소켓, 네임드 파이프, RPC

---

6계층 : Presentation Layer
- 역할: 데이터의 표현(확장자 연결, 압축, 암호화, 변환)
- 유형범위: 애플리케이션 데이터 표현
- 프로토콜 및 기술: SSL, MIME

---

7계층 : Application Layer
- 역할: 사용자에게 인터페이스 제공, 원본데이터 생성
- PDU: 사용자 데이터
- 유형범위: 애플리케이션 데이터
- 프로토콜 및 기술 : HTTP(TCP/80), HTTPS(TCP/443), SMTP(TCP/25), POP3(TCP/110), FTP(TCP/20,21), TFTP(UDP/69), Telnet(TCP/23), SSH(TCP/22), DHCP(UDP/67,68), DNS(UDP/53), SNMP(UDP/161,162) 등
                             
