package cn.emay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;

/**
 * 多线程测试
 * @author zjlWm
 * @date 2016-4-2
 */
public class ExecutorsTest {
	public static ExecutorService pool;
	
	/**
	 * 初始化线程
	 */
	private static void initThreadPool(){
		 int numberOfThreads = java.lang.Runtime.getRuntime().availableProcessors();
		 pool = Executors.newFixedThreadPool(numberOfThreads);
		 pool.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("--submit---");
			}
		});
	}
	
	public void startRuanable(){
		pool.execute(
				new Runnable() {
					@Override
					public void run() {
						testUrl();
					}
				}
			);
	}
	
	
	
	/**
	 * 获取验证码
	 * @param args
	 */
	public void testUrl(){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri("http://172.16.1.84/").build();
		HttpResponse response=LocalHttpClient.execute(httpUriRequest);
		System.out.println(response);
	}
	
	public static void main(String[] args) {
		ExecutorsTest t=new ExecutorsTest();
		initThreadPool();
		for(int i=0;i<10000;i++){
			t.startRuanable();
		}
		pool.shutdown();//启动一次顺序关闭，执行以前提交的任务，但不接受新任务。"
		while(true){
            if(pool.isTerminated()){  
                System.out.println("所有的子线程都结束了！");  
                break;  
            }  
        } 
	}
}
