package com.coco;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;

/**
 * @author zhangxiaoxun
 * @desc 任务消费者
 * @date 2018/12/27  21:57
 **/
public class TaskComsumer implements Runnable{

    private static Jedis jedis = new Jedis("127.0.0.1",6379);
    Random random = new Random();

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //为了以防任务执行失败，可以将任务缓存在队列中
            //从任务队列中取出，缓存到临时队列中
            String uuid = jedis.rpoplpush("taskQueue", "tempTask");


            //模范实际情况（50%）的失败率
            if (random.nextInt() % 2 == 0) {
                //失败后在进入任务队列
                //单机只有一个县城的情况下是没有问题的，但是多线程情况下，程序需要调整，不然有些任务未来得及执行就会被从临时队列中清理出去
                jedis.rpoplpush("tempTask", "taskQueue");
                System.out.println("任务-" + uuid.toString() + "执行失败，重新进入任务队列");
            } else {
                jedis.rpop("tempTask");
                System.out.println("任务-" + uuid.toString() + "执行成功，从临时队列出列");
            }

        }
        }

}
