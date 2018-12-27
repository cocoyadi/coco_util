package com.coco;

/**
 * @author zhangxiaoxun
 * @desc 任务定时执行
 * @date 2018/12/27  21:58
 **/
public class TaskScheduleSystem {
    public static void main(String args[]) throws InterruptedException {
        // 启动一个生产者线程，模拟任务的产生
        new Thread(new TaskProvider()).start();

        Thread.sleep(15000);

        //启动一个线程者线程，模拟任务的处理
        new Thread(new TaskComsumer()).start();

        //主线程休眠
        Thread.sleep(Long.MAX_VALUE);
    }
}
