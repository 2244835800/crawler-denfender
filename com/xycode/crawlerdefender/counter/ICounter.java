package com.xycode.crawlerdefender.counter;

import com.xycode.crawlerdefender.config.Config;



/**
 * ������������ʵʱ��¼��λʱ������ip�ķ��ʴ���<br/>
 * @author xiaoyang
 *
 */
public interface ICounter {
	
	/**
	 * �뼶�������
	 * @param ip
	 */
	public int secondCount(String ip);
	
	/**
	 * �ּ��������
	 * @param ip
	 */
	public int minuteCount(String ip);
	

	public int getSecondCount(String ip);
	
	public int getMinuteCount(String ip);
	
	
	
}
