package com.dahoon.toy.artcollector.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameDocumentTest {

    @BeforeEach
    public void before(){
        System.out.println("테스트 시작");
    }

    @AfterEach
    public void after(){
        System.out.println("테스트 종료");
    }

    @Test
    @DisplayName("테스트 이름")
    public void test1() throws Exception {
        System.out.println();
    }
}