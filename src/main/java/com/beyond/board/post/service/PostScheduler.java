// 계속 돌아가니까 전체 주석처리

//package com.beyond.board.post.service;
//
//import com.beyond.board.post.domain.Post;
//import com.beyond.board.post.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Component
//public class PostScheduler {
//
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostScheduler(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    // 아래 스케줄의 cron 부는 각 자리마다 "초 분 시간 일 월 요일" 을 의미.
//    // 예를 들어 ) * * * * * * => 매 월, 매 일, 매 분, 매 초 마다 새로고침 된다는 뜻.
//    //        ) 0 0 * * * * => 매일 매 시 0분 0초마다.
//    //        ) 0 0 11 * * * => 매일 11시에.
//
//    //        ) 0 0/1 * * * * => 매일 매 시 1분 마다.
//    //        ) 0 1 * * * * => 매일 매 시 1분'에'.
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Transactional
//    public void postSchedule(){
//        System.out.println("======== 🐰 예약 스케줄러 시작 🥕 ========");
//        Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");
//        for (Post post : posts) {
//            if(post.getAppointmentTime().isBefore(LocalDateTime.now())){
//                post.updateAppointmet("N");
//            }
//        }
//    }
//
//}
