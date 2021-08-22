package chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

//实验目的：证明 一个JAVA程序的运行不仅仅是main()方法的运行，而是main线程和其他多个线程的同时运行。
public class MultiThread {

    public static void main(String[] args) {
        //获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //过去线程和线程堆栈信息，不需要获取同步的monitor和synchronizer信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息，打印线程id和线程名称
        for(ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }
}
