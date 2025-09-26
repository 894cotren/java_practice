package com.awc20.practice.thread;

public class Ticket4Thread {

    // 1. 共享锁对象（任何对象都可以，通常就用this）
    private static final Object lock = new Object();

    // 共享资源：总票数
    private static int ticketCount = 100;


    public static void main(String[] args) throws InterruptedException {

        Thread t1 =new Thread(()->{
            Ticket4Thread.getTicket();
        });
        Thread t2 =new Thread(()->{
            Ticket4Thread.getTicket();
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("100张票售卖完成");
    }

    /**
     * 打印数字的方法
     */
    private static void getTicket() {
        // 获取锁

        while (true){
                synchronized (lock) {
                if (ticketCount<=0){
                    break;
                }
                ticketCount--;
                String curName = Thread.currentThread().getName();
                System.out.println("线程："+curName+"抢到了一张票"+"剩余票数："+ticketCount);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
