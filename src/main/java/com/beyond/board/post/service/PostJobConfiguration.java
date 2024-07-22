package com.beyond.board.post.service;

import com.beyond.board.post.domain.Post;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Configuration
// batch 작업 목록 정의
public class PostJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private PostRepository postRepository;

    public Job executeJob(){
        return jobBuilderFactory.get("executeJob")
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("======== 🐰 예약 글쓰기 batch task1 시작 🥕 ========");
                    System.out.println("======== 🐰 예약 글쓰기 batch task1 종료 🥕 ========");
                    Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");
                    for (Post post : posts) {
                        if(post.getAppointmentTime().isBefore(LocalDateTime.now())){
                            post.updateAppointmet("N");
                        }
                    }
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step secondStep(){
        return stepBuilderFactory.get("secondStep")
        .tasklet(((stepContribution, chunkContext) -> {
            System.out.println("======== 🐰 예약 글쓰기 batch task2 시작 🥕 ========");
            System.out.println("======== 🐰 예약 글쓰기 batch task2 종료 🥕 ========");
            Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");
            for (Post post : posts) {
                if(post.getAppointmentTime().isBefore(LocalDateTime.now())){
                    post.updateAppointmet("N");
                }
            }
            return RepeatStatus.FINISHED;
        })).build();
    }
}
