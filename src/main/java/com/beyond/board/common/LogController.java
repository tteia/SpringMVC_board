package com.beyond.board.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// slf4j 어노테이션을 통한 로거 선언 방법
@Slf4j
public class LogController {
    // Slf4j 어노테이션 또는 Logger 직접 선언 => 방법 두 가지 !
//    // 우리는 로거 직접 선언 방식 진행. Logger logger = LoggerFactory.getLogger(클래스명.class);
//    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test1")
    public String logTest1(){
        // 기존의 log 방식 : System.out.println => 항상 출력됨. 분류가 안 된다 !
        // 문제점 1) 성능이 좋지 않음. 2) 로그 분류 작업 불가.
        System.out.println("println 로그입니다.");
        // Logger 직접 선언일 때.
//        logger.info("info 로그입니다.");
//        logger.error("error 로그입니다.");
        log.info("slfj4 를 통한 info 로그입니다.");
        log.error("slfj4 를 통한 error 로그입니다.");


        return "ok";
    }
}
