package com.xycode.crawlerdefender.persistence;

import com.xycode.crawlerdefender.logcache.LogCachePool;


/**
 * �־û�������������db��filesystem��<br/>
 * <p>ְ��</p>
 * 1.������־�ĳ־û�<br/>
 * 2.����Сʱ���켶��ķ��ʼ���
 * @author xiaoyang
 *
 */
public interface IPersistence {
	
	/**
	 * ��ʼ��
	 */
	public abstract void init()  ;
	
	/**
	 * ����
	 */
	public abstract void destroy()  ;
	
	/**
	 * �������е���־д��־û�����
	 */
	public abstract void cacheToPersistence(LogCachePool cachePool) ; 
	
	/**
	 * д������־
	 * @param log
	 */
	public abstract void insertVisitLog(LogObject log) ;
	
	
	/**
	 * ��ȡ���1Сʱ��ָ��ip�ķ��ʴ���
	 */
	public abstract int getHourVisitCount(String ip);

	
	/**
	 * ��ȡ���1����ָ��ip�ķ��ʴ���
	 */
	public abstract int getDayVisitConut(String ip);
	
}
