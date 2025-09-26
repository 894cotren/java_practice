package com.awc20.practice.thread;

public class Test4Thread {

    public static void main(String[] args) throws InterruptedException {
        Task4Thrad task4Thrad = new Task4Thrad();
        Thread t1=new Thread(()->{
            try {
                for (int i = 0; i < 5; i++) {
                    task4Thrad.printNumber();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2=new Thread(()->{
            try {
                for (int i = 0; i < 5; i++) {
                    task4Thrad.printLetter();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("两个线程打印完成");
    }
}
