/**
 * 
 */
package cn.emay;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cn.emay.common.messaging.IMBase;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 多线程测试
 * @author zjlWm
 * @date
 *
 */
public class MyThreadTest {
	static ExecutorService pool;
	static int currentIndex = 0;
	static int failedCount = 0;
	static int totalSize=0;
	static int success=0;
	static Date sdate;
	static Date edate;
	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	static ReentrantReadWriteLock lockFailCount = new ReentrantReadWriteLock();
	
	
	/**
	 * 
	 * 功能描述：<br>
	 * 当前处理索引加1
	 * 
	 * @return void
	 * 
	 * 修改记录:
	 */
	public static int addCurrentIndex(){
		synchronized (lock.writeLock()) {
			try{
				lock.writeLock().lock();
				currentIndex++;
			}finally{
				lock.writeLock().unlock();
			}
		}
		return currentIndex;
	}
	
	public static void addFailedCount(){
		synchronized (lockFailCount.writeLock()) {
			try{
				lockFailCount.writeLock().lock();
				failedCount++;
			}finally{
				lockFailCount.writeLock().unlock();
			}
		}
	}
	public static int getFailedCount(){
		int count = 0;
		synchronized (lockFailCount.readLock()) {
			try{
				lockFailCount.readLock().lock();
				count = failedCount;
			}finally{
				lockFailCount.readLock().unlock();
			}
		}
		return count;
	}
	
	
	
	
	private static void initThreadPool(){
		 int numberOfThreads = java.lang.Runtime.getRuntime().availableProcessors();
		 System.out.println(numberOfThreads);
		 pool = Executors.newFixedThreadPool(numberOfThreads);
	}
	
	
	private static void startRunnable(){
		pool.execute(
				new Runnable() {
					@Override
					public void run() {
						System.out.println("--------current index-----"+addCurrentIndex());
						try{
							ObjectNode jsonFriends=IMBase.getFriends("13161454672");
							System.out.println("jsonFriends:"+jsonFriends);
						}catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				}
			);
	}
	
	
	
	public static void main(String[] args) {
		initThreadPool();
		for(int i=0;i<3;i++){
			startRunnable();
		}
		pool.shutdown();
	}
}
