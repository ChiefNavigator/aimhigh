# AimHigh

- 고객이 신뢰할 수 있는 사업자 송금 서비스를 제공하겠습니다.
- 개발 디자인 문서 : [사업자 송금 API 개발](resources/README.md)

***

## 1. Develop Period
2024-04-03 ~ 2024-04-06

## 2. Contributor
@ChiefNavigator

## 3. Function
- 사업자 송금 API : 타 사업자에게 금액을 입금 받고, 이를 다른 사업자에게 송금
- 사업자 송금 실패 처리 배치 : 일정 주기로 송금 과정 중 실패한 단계에서 송금 재시도
- 사업자 송금 실패 경고 메세지 송신 : 10분 이상 송금 완료가 처리되지 않은 송금 요청에 대해, 모니터링 채널에 경고 메세지 송신

## 4. How to run


## 5. Environment
- IDE : IntelliJ IDEA 2023.1.5
- JAVA : Java 17
- JDK : Amazon Corretto version 19.0.2
- Framework
  - SpringBoot 3.2.4
  - Spock 2.3-groovy-3.0
  - Redis
  - Kafka


## 6. Software Architecture
- CleanArchitecture
![image](resources/images/AimHighSoftwareArchitecture.png)


