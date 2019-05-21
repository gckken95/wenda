package com.nowcoder;

import sun.print.PrintJob2D;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

class MyThread extends Thread{
    private int tid;

    public MyThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        try {
            for(int i=0;i<10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d",tid,i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {
    public static void testThread(){
        for (int i = 0; i < 10; i++) {
            //new MyThread(i).start();
        }

//        for (int i = 0; i < 10; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        for(int i=0;i<10;i++){
//                            Thread.sleep(1000);
//                            System.out.println(String.format("T2:%d",i));
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }

    private static Object obj=new Object();

    public static void testSynchronized1(){
        synchronized (obj){
            try {
                for(int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T3:%d",i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public static void testSynchronized2(){
        synchronized (obj){
            try {
                for(int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T4:%d",i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }


    class Consumer implements Runnable{
        private BlockingQueue<String> q;

        public Consumer(BlockingQueue<String> q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {
                while (true){
                    System.out.println(Thread.currentThread().getName()+":"+q.take());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    class Producer implements Runnable{
        private BlockingQueue<String> q;

        public Producer(BlockingQueue<String> q) {
            this.q = q;
        }

        @Override
        public void run() {
            try {

                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    q.put(String.valueOf(i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//    public static void testBlockingQueue(){
//            BlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
//            new Thread(new Producer(q)).start();
//            new Thread(new Consumer(q),"Consumer1").start();
//            new Thread(new Consumer(q),"Consumer2").start();
//    }



    public static void main(String[] args) {
//        testThread();
//        testSynchronized();
//        testBlockingQueue();
    }

}
