// 계속 돌아서 전체 주석처리 ~!

//package com.beyond.board.post.service;
//
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//// batch 를 실행하기 위한 스케줄러 정의
//@Component
//public class PostBatchScheduler {
//
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private PostJobConfiguration postJobConfiguration = new PostJobConfiguration();
//
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void batchSchedule(){
//        Map<String, JobParameter> configMap = new HashMap<>();
//        configMap.put("time", new JobParameter(System.currentTimeMillis())); // 매개변수가 밀리초 요구 > 밀리초로 갖다줌
//        JobParameters jobParameters = new JobParameters(configMap);
//        try{
//            jobLauncher.run(postJobConfiguration.executeJob(), jobParameters);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
