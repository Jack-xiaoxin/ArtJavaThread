package chapter4;

import java.util.List;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


//实验目的：验证线程的优先级的作用，通过不停的让步，来检测，优先级高的线程被CPU调度起来的频率是不是比优先级低的线程更多
//实验结果：优先级为1的线程和优先级为10的线程，jobCount的数量差不多。这证明--程序的正确性不能依赖于线程的优先级高低
public class Priority {

    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = new ArrayList<Job>();
        for(int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }
        //notStart为false时，jobCount开始增加。否则一直在让步
        notStart = false;
        //睡眠10s，让这10个线程执行10秒
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;

        for(Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + " count : " + job.jobCount);
        }
    }

    static class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while(notStart) {
                Thread.yield();
            }
            while(notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
