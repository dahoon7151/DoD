package com.dahoon.toy.artcollector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest     // @DataJpaTest 는 모든 빈을 등록하지 않고 JPA 환경에 필요한 빈만 등록
@Transactional
@ActiveProfiles("test")
class ArtcollectorApplicationTests {

	@Test
	void contextLoads() {
	}

}
