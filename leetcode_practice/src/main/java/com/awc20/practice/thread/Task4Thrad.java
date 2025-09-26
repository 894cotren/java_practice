package com.awc20.practice.thread;

import java.util.concurrent.locks.Lock;

/**
 * 练习多线程轮流打印。
 */
public class Task4Thrad{

    public final static Object myLock=new Object();
    private int numIndex = 0; // 数字打印进度
    private int letterIndex = 0; // 字母打印进度
    private boolean isNumberTurn = true; // 标志位：是否轮到数字线程
    private int[] numbers = {1, 3, 5, 7, 9};
    private char[] letters = {'A', 'B', 'C', 'D', 'E'};


    //轮流打印
    public synchronized void printNumber() throws InterruptedException {
        if (isNumberTurn){
            this.wait();//方法锁对象是对象实例。静态方法才是class实例。
        }
        // 打印当前数字
        System.out.print(numbers[numIndex] + " -> ");
        numIndex++;
        // 切换标志位，唤醒字母线程
        isNumberTurn = false;
        this.notify(); // 唤醒等待的字母线程
    }

    // 字母线程执行的方法
    public synchronized void printLetter() throws InterruptedException {
        // 若没轮到自己，等待
        if (!isNumberTurn) {
            this.wait(); // 释放锁，进入等待
        }
        // 打印当前字母
        System.out.print(letters[letterIndex] + " -> ");
        letterIndex++;
        // 切换标志位，唤醒数字线程
        isNumberTurn = true;
        this.notify(); // 唤醒等待的数字线程
    }


//    这个是练啥我不太清楚
//    @Override
//    public void run() {
//        for (int i = 0; i < 100; i++) {
//            synchronized (myLock){
//                String curName = Thread.currentThread().getName();
//                System.out.println("当前打印的线程是--"+curName+"--打印的数为:"+i);
//
//         }
//       }
//    }
}
