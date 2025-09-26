package com.awc20.practice.thread;

public class TurnEachPrint01 {
    /**
     * 目标，两个线程轮流打印0和1
     *
     */

    //首先要一把锁
    public final static Object myLock = new Object();
    //标志位
    public static boolean isZeroPrint =true;  //判断是否输出0
    //定义一个各输出50次的限制吧。
    public static int zeroNums=50;
    public static int oneNums=50;

    //通过标志位判断等待唤醒。

    public static void main(String[] args) throws InterruptedException {
        Thread t0 =new Thread(()->{
            try {
                TurnEachPrint01.print0();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t1 =new Thread(()->{
            try {
                TurnEachPrint01.print1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.setName("t1");
        t0.setName("t0");
        t1.start();
        t0.start();
        t1.join();
        t0.join();
        System.out.println("01打印完成");
    }



    public static void print0() throws InterruptedException {
        while (zeroNums>0){
            synchronized (myLock){
                //判断是否还有次数
                while (!isZeroPrint){  //如果没有执行权直接去等待
                    myLock.wait();
                }
                String curThreadName = Thread.currentThread().getName();
                zeroNums--;
                System.out.println("线程名："+curThreadName+"输出：0"+"还剩余："+zeroNums+"次");
                isZeroPrint=false;
                myLock.notify();
            }
        }
    }

    public static void print1() throws InterruptedException {
        while (oneNums>0){
            synchronized (myLock){
                while (isZeroPrint){  //如果没有执行权直接去等待
                    myLock.wait();
                }
                String curThreadName = Thread.currentThread().getName();
                oneNums--;
                System.out.println("线程名："+curThreadName+"输出：1"+"还剩余："+oneNums+"次");
                isZeroPrint=true;
                myLock.notify();
            }
        }
    }

}
