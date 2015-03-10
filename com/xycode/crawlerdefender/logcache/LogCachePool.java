package com.xycode.crawlerdefender.logcache;

import java.util.LinkedList;
import java.util.List;

import com.xycode.crawlerdefender.persistence.LogObject;
import com.xycode.crawlerdefender.persistence.mongo.MongoPersistence;

/**
 * ��־���󻺴��
 * ���ڻ���ס������־���Ա㶨ʱ������д��־û�����
 * @author xiaoyang
 */
public final class LogCachePool {
	
	private static final int DEFAULT_SIZE=1000;//�����Ĭ�ϴ�С
	
	private int capability;
	
	private List<LogObject> cachedLogs;
	
	public LogCachePool(){
		this.capability=DEFAULT_SIZE;
		this.cachedLogs=new LinkedList<LogObject>();
	}
	
	public LogCachePool(int capability){
		this.capability=capability;
		this.cachedLogs=new LinkedList<LogObject>();
	}
	
	public void add(LogObject value){
		
		if(this.cachedLogs.size()>=this.capability){
			MongoPersistence.getInstance().insertVisitLog(value);//��������ʱ��ֱ�ӳ־û�
		}else{
			this.cachedLogs.add(value);
		}
		
	}
	
	
	public LogObject get(int index){
		return this.cachedLogs.get(index);
	}
	
	public LogObject remove(int index){
		return this.cachedLogs.remove(index);
	}
	
	public void clear(){
		this.cachedLogs.clear();
	}
	
	public boolean isEmpty(){
		return cachedLogs.isEmpty();
	}

	public List<LogObject> getCachedLogs() {
		return cachedLogs;
	}
	
	public int size(){
		return cachedLogs.size();
	}
	
}
