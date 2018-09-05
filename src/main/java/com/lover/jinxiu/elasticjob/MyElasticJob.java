package com.lover.jinxiu.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import org.springframework.stereotype.Component;

@Component
@ElasticSimpleJob(cron="0 0 * * * ?",jobName="clearAddressCache",shardingTotalCount=2,jobParameter="测试参数",shardingItemParameters="0=A,1=B")
public class MyElasticJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("execute my job...");
        System.out.println("JobName:"+shardingContext.getJobName());
        System.out.println("JobParameter:"+shardingContext.getJobParameter());
        System.out.println("ShardingItem:"+shardingContext.getShardingItem());
        System.out.println("ShardingParameter:"+shardingContext.getShardingParameter());
        System.out.println("ShardingTotalCount:"+shardingContext.getShardingTotalCount());
        System.out.println("TaskId:"+shardingContext.getTaskId());
        System.out.println("---------------------------------------");

    }
}
