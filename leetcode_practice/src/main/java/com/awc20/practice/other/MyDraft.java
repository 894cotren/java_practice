package com.awc20.practice.other;


import java.util.Random;

public class MyDraft {
    public static void main(String[] args) throws InterruptedException {
        // 主线程创建3个工作线程
        Thread worker1 = new Thread(new Worker(), "Worker-1");
        Thread worker2 = new Thread(new Worker(), "Worker-2");
        Thread worker3 = new Thread(new Worker(), "Worker-3");

        // 启动所有工作线程
        worker1.start();
        worker2.start();
        worker3.start();

        System.out.println("主线程等待所有工作线程完成...");

        // 等待所有"子线程"完成
        worker1.join();
        worker2.join();
        worker3.join();

        // 统一处理结果
        System.out.println("所有工作线程已完成，开始汇总处理...");
        Thread.sleep(2000);
        System.out.println("处理完啦");

    }

    static class Worker implements Runnable {
        @Override
        public void run() {
            // 执行具体任务
            Random random = new Random();
            int workTime = random.nextInt(1, 5);
            System.out.println(Thread.currentThread().getName() + " 正在工作" +workTime+"秒");
            workTime=workTime*1000;
            try {
                Thread.sleep(workTime);
                System.out.println(Thread.currentThread().getName() + "工作了" +workTime+"秒"+"工作完啦");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
