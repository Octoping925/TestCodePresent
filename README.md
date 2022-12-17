# TestCodePresent
TDD 스터디에서 발표한 단위 테스트 강의 관련 코드


## 대본
[테스트 코드 작성 관련 토요일 미니 강의]

1. public static void main 테스트의 문제점
        - 테스트를 할 때마다 직접 main을 실행해야 함
        - 값이 제대로 나오는지 print해서 나오는 결과를 눈으로 확인해야 함
        - 서비스가 실행 중이라면 서버를 껐다 켜야함


main method 테스트 문제점
Production code와 Test Code가 클래스 하나에 존재한다. 클래스 크기가 커짐. 복잡도 증가함.
Test Code가 실 서비스에 같이 배포됨.
main method 하나에서 여러 개의 기능을 테스트 함. 복잡도 증가.
method 이름을 통해 어떤 부분을 테스트하는지에 대한 의도를 드러내기 힘듦.
테스트 결과를 사람이 수동으로 확인

Calculator를 테스트해보자. 
sout(add(2, 3)) 같은 식으로 테스트 해야 함.
sout(multiply(10만, 10만))의 경우? 잘못된 값이 나오지만 사람이 눈으로 확인해야함. 복잡한 비즈니스 로직의 경우 확인이 어려울 수도..


-----------------------------------------
2. @Test로 테스트 함수 작성하기
        - test를 작성하는 위치
        - ctrl alt n으로 테스트 생성


-----------------------------------------
3. assertXXX로 값 검증하기
        - assert가 없으면 테스트가 무조건 성공으로 뜬다
        - given when then 패턴

코드의 간결함을 위해 Assertions은 static import를 하자.
assert 없이는 결국 sout으로 확인해야하는데, 결국 눈으로 확인하는 수밖에. assert를 쓰자.

-----------------------------------------
4. @BeforeEach, AfterEach, BeforeAll, AfterAll
        - 매번 초기화 작업이 귀찮아

BeforeEach는 초기화작업 (매번 Calculator를 생성하는 작업이라던가)
AfterEach는 DB 사용 후 초기화 작업?
BeforeAll은 외부와 커넥션하는 작업? AfterAll은 끊는 작업?
다양하게 쓸 수 있겠다.

-----------------------------------------
5. @ParameterizedTest로 여러 데이터 테스트하기
        - 각자 다른 인수로 여러번 테스트 가능
        - 다양한 source 사용 가능
        - ValueSource로 인수 넣기 @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 })
        - CsvSource로 여러 인수 넣기 @CsvSource({"1,2", "2,3", "5,10", "100,50"})
        

multiply의 경우, 결과가 int인 경우, 21억을 넘어가는 경우 두 파트를 체크해보고 싶을 수 있다.
이런 식의 다양한 케이스 검증에 사용해볼 법하다.

-----------------------------------------
6. @SpringbootTest
        - 등록한 빈을 사용해야 할 경우
        - Application Context가 올라가야하므로 느림
        - 원하는 빈만 등록해서 테스트하기가 가능 (대신 Application Context가 여러번 올라갈 수도)  @SpringBootTest(classes = {EmailController.class, EmailService.class})


-----------------------------------------
7. DB 연동 테스트 및 @Transactional
        - 
        - @Transactional로 테스트 사항 롤백하기

DB에 insert만 하고, 내가 직접 select 문으로 결과를 확인하는거 오바지. select문으로 다시 가져와서 검증도 해주자.

회원가입을 하는데, 처음 한번은 중복이 아니니 문제 없지만 두번째는 중복이라 오류가 난다. Transactional로 롤백하자.

@Rollback(false) 하면 롤백 안된다

-----------------------------------------
8. 테스트용 DB 나누기
        - 테스트용 DB는 메모리 DB로 따로 둔다
        - 운영과 테스트 환경의 application properties 분리하기

[h2 실행]
docker run -d -p 1521:1521 -p 81:81 --name=MyH2Instance -e H2_OPTIONS='-ifNotExists' oscarfonts/h2 

[properties]
spring.datasource.url=jdbc:h2:tcp://localhost:1521/test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa

spring.jpa.show-sql=true

# hibernate Setting
spring.jpa.database=h2
spring.jpa.hibernate.ddl-auto=create
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true

-----------------------------------------
9. 테스트 더블
        - Mocking
        - Stub
        - Spy

회원가입할 때 이메일도 이제 보낸다고 하자.
EmailService의 경우 진짜 이메일이 날아가버리니 join을 테스트하기 어려움

테스트를 진행하기 어려운 경우 이를 대신해 테스트를 진행할 수 있도록 만들어주는 객체를 말합니다.
단어 자체의 뜻은 '대역'에 가깝고 스턴트맨과 유사한 의미를 가집니다.

LottoGenerator에서 getRandomNo때문에 getLottoNums가 중복이 포함된 값이 뽑힐경우와 중복이 하나도 안 나올경우를 분리해서 테스트할 수 없음
-> mockito 사용,  LottoGenerator generator = Mockito.mock(LottoGenerator.class);

https://github.com/mockito/mockito/wiki/Mockito-features-in-Korean


Mockito.doAnswer(invocation -> {
        Object[] args = invocation.getArguments();
        System.out.println("Fake Email! " + Arrays.toString(args));
        return null;
}).when(emailService).sendEmail(Mockito.anyString(), Mockito.anyString());


하지만 모킹을 하면 모킹된 빈이 들어간 앱콘텍스트가 또 생기는 거니 스프링이 여러번 올라감.
이런 문제는 생성자주입으로 스텁을 한 객체를 넣어주는 방식으로 해결 => SpringBootTest를 안 켜도 된다! Autowired를 지양하라는게 이런 뜻.
대신 이러면 테스트 대상 코드가 늘어나기 때문에 테스트 코드 읽기 어려워짐. 그러니 Mock이 나을 때도 있다.


근데 이건 회원가입 테스트는 하지만 이메일은 날리기 싫은 경우이고,
이메일 전송함수가 실행이 잘 되는지를 체크하고 싶은데 이메일 전송이 되지 않았으면 좋겠는 경우는 spy를 사용.


Mockito.verify(emailService, Mockito.times(1)).sendEmail(email, "가입 완료");
한번 실패하는 케이스도 보여주자.



[장점]
테스트 대상 코드 격리
테스트 속도 개선
예측 불가능한 실행 요소 제거
특수한 상황 테스트 가능
감춰진 정보를 확인 가능

————————————————————
10. 테스트하기 좋은 구조를 향하여
        - 의존성 주입
        - 숨겨진 입력값을 경계하기
        - 조작할 수 없는 값을 밖으로 빼기
        - private 메소드의 테스팅

LocalDateTime.now의 경우? -> Member의 huntMonster로 경험치 얻는게 일요일에는 2배일 경우 -> 일요일에만 테스트가 성공? LocalDateTime을 밖에서 받아오자
Random의 경우? ->
