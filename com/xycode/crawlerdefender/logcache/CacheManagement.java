package com.xycode.crawlerdefender.logcache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.xycode.crawlerdefender.persistence.IPersistence;
import com.xycode.crawlerdefender.persistence.mongo.MongoPersistence;

/**
 * ��־���������
 * @author xiaoyang
 */
public class CacheManagement {
	
	private static LogCachePool cachePool=new LogCachePool(); 
	
	private static IPersistence pc=MongoPersistence.getInstance();
	
	private static ScheduledExecutorService ses=Executors.newScheduledThreadPool(1);
	
	static{
		
		//��ʱ���������־������־û�����
		ses.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				
				try{
					pc.cacheToPersistence(cachePool);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, 0,1, TimeUnit.SECONDS);
		
		
		
	}
	
	public static LogCachePool getLogCachePool(){
		return cachePool;
	}
	
}
