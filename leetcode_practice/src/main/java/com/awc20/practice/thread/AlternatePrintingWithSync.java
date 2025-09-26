package com.awc20.practice.thread;

public class AlternatePrintingWithSync {

    // 1. 共享锁对象（任何对象都可以，通常就用this）
    private static final Object lock = new Object();
    // 2. 控制打印轮次的标志
    // false: 该数字线程打印
    // true: 该字母线程打印
    private static boolean flag = false;

    public static void main(String[] args) {
        // 线程A：打印数字
        Thread threadA = new Thread(() -> {
            for (int i = 1; i <= 26; i++) {
                printNumber(i);
            }
        }, "Thread-A-(Number)");

        // 线程B：打印字母
        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                printLetter((char) ('A' + i));
            }
        }, "Thread-B-(Letter)");

        threadA.start();
        threadB.start();
    }

    /**
     * 打印数字的方法
     */
    private static void printNumber(int number) {
        // 获取锁
        synchronized (lock) {
            // 必须用while循环检查条件，不能用if
            while (flag) { // 如果flag为true，说明不该数字线程打印
                try {
                    lock.wait(); // 释放lock锁，当前线程进入等待状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 条件满足，执行打印任务
            System.out.print(number);

            // 任务完成，更改标志位，通知其他线程
            flag = true;
            lock.notifyAll(); // 唤醒所有在lock上等待的线程
        }
        // 同步块结束，自动释放锁
    }

    /**
     * 打印字母的方法
     */
    private static void printLetter(char letter) {
        synchronized (lock) {
            while (!flag) { // 如果flag为false，说明不该字母线程打印
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(letter);
            flag = false;
            lock.notifyAll();
        }
    }
}
