package com.lover.jinxiu.elasticjob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

import javax.annotation.Resource;

public class ElasticJobListener extends AbstractDistributeOnceElasticJobListener {
//    @Resource
//    private TaskRepository taskRepository;

    public ElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
//        //任务执行完成后更新状态为已执行
//        JobTask jobTask = taskRepository.findOne(Long.valueOf(shardingContexts.getJobParameter()));
//        jobTask.setStatus(1);
//        taskRepository.save(jobTask);
    }
}