package com.beyond.board;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession // 세션 스토리지로 레디스를 사용하겠다는 어노테이션.
@EnableBatchProcessing // batch 사용 시 작성해주는 어노테이션.
@EnableScheduling // 스케줄러 사용 시 작성해주는 어노테이션.
@SpringBootApplication
public class BoardApplication {
	public static void main(String[] args)  {
		SpringApplication.run(BoardApplication.class, args);
	}

}
