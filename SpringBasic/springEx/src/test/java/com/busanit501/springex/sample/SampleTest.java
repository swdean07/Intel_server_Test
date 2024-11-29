package com.busanit501.springex.sample;

import com.busanit501.springex.sample.sample.SampleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 테스트 설정.
//JUnit4 테스트 설정. @Runwith
// 설정 파일의 경로를 지정.
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTest {

    // 주입 방식
    // 1)필드 주입 방식, 2) 생성자 주입방식 3) 메서드 주입방식
    // 해당 타입의 빈이 존재하면 가져와 사용함.
    @Autowired // 시스템에 등록된 빈을 연결 하겠다.
    // ApplicationContext (앞에 서버프로그램, 서블릿 컨텍스트와 비슷한 구조, )
    // ApplicationContext : 전역, 공유 공간, 여기에,
    // SampleService  등록을 해서, 지금 단위 테스트 하는 파일에서 불러올수 있음.
    // 즉 , 테스트 파일 뿐만 아니라, 어느 파일에서도 다 불러와서 사용가능.
    private SampleService sampleService;

    @Test
    public void testService1() {
        log.info("testService1 : " + sampleService);
        Assertions.assertNotNull(sampleService);
    }
}
