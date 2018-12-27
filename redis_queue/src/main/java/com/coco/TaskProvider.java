package com.coco;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;

/**
 * @author zhangxiaoxun
 * @desc 任务生产者
 * @date 2018/12/27  21:57
 **/
public class TaskProvider implements Runnable{
    private static Jedis jedis = new Jedis("127.0.0.1",6379);

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            UUID uuid = UUID.randomUUID();
            jedis.lpush("taskQueue",uuid.toString());
            System.out.println("任务-"+uuid.toString()+"进入队列了");

        }
    }
}
