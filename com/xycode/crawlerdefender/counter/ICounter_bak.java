package com.xycode.crawlerdefender.counter;



/**
 * ������������ʵʱ��¼��λʱ����ip�ķ��ʴ���<br/>
 * @author xiaoyang
 *
 */
public interface ICounter_bak {
	
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
	
	/**
	 * Сʱ���������
	 * @param ip
	 */
	public int hourCount(String ip);
	
	/**
	 * �켶�������
	 * @param ip
	 */
	public int dayCount(String ip);
	

	public int getSecondCount(String ip);
	
	public int getMinuteCount(String ip);
	
	public int getHourCount(String ip);
	
	public int getDayCount(String ip);
	
	
}
