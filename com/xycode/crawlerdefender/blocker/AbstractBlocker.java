package com.xycode.crawlerdefender.blocker;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xycode.crawlerdefender.config.Config;
import com.xycode.crawlerdefender.counter.ICounter;
import com.xycode.crawlerdefender.counter.simple.SimpleCounter;
import com.xycode.crawlerdefender.persistence.IPersistence;
import com.xycode.crawlerdefender.persistence.mongo.MongoPersistence;

/**
 * ������<br/>
 * ְ��<br/>
 * 1��ʵʱ���أ����ݼ�������ȡ���ʴ������������ip�ڵ�λʱ���ڳ����趨��ֵ�������ز�������֤ҳ�棬������10��δ��д��֤ҳ���򽫴�ip���������<br/>
 * 2�����غ������е�ip<br/>
 * 3��ά���ڡ�������������������־�����ɺ�����<br/>
 * @author xiaoyang
 */
public abstract class AbstractBlocker {
	
	protected ICounter couter;
	protected IPersistence pt;
	protected Config config;
	
	protected List<String> ipWhiteList=new ArrayList<String>();
	protected List<String> ipBlackList=new ArrayList<String>();
	protected List<String> userAgentWhiteList=new ArrayList<String>();
	protected List<String> userAgentBlackList=new ArrayList<String>();
	
	public AbstractBlocker(){
		
		//��ʼ��
		couter=SimpleCounter.getInstance();
		pt=MongoPersistence.getInstance();
		config=Config.getInstance();
		
		//�������ļ��ж�ȡ�û��Զ���ĺڰ�����
		ipWhiteList.addAll(config.getConfigItem().getIpWhiteList());
		ipBlackList.addAll(config.getConfigItem().getIpBlackList());
		userAgentWhiteList.addAll(config.getConfigItem().getUserAgentWhiteList());
		userAgentBlackList.addAll(config.getConfigItem().getUserAgentBlackList());
		
		//�ӳ־û������л�ȡ�������ɵĺڰ�����
		
	}
	
	
	/**
	 * �����־û������е���־�������ڰ�����
	 */
	protected abstract boolean buildWhiteBlackListFromPersistence();
		
	
	/**
	 * ֱ�����ص���ip����user-agent
	 * @param key  	ip or user-agent
	 * @param type 	0��ip��1��user-agent
	 */
	protected abstract boolean block(String key,int type);
	
	/**
	 * ����ip������
	 * @param key  ip or user-agent
	 * @param type 0��ip��1��user-agent
	 */
	protected abstract boolean blockIpBlackList(List<String> blackList);
	
	/**
	 * ����userAgent������
	 * @param key  ip or user-agent
	 * @param type 0��ip��1��user-agent
	 */
	protected abstract boolean blockUserAgentBlackList(List<String> blackList);
	
	/**
	 * <p>���ݼ������Լ��־û��������ز�������֤ҳ��</p>
	 * <p>������ʵʱ�����롢�ּ���ļ���</p>
	 * <p>�־û��������߷������ݲ�����Сʱ���켶�����</p>
	 * @return boolean true:���ز�����У��  false:����Ҫ����
	 */
	protected abstract boolean blockAndValidate(HttpServletRequest request ,HttpServletResponse response);
	
	/**
	 * @return true����ʾ����ip�ں���������Ҫ���أ�false����ʾ����ip���ں�������
	 */
	public boolean block(HttpServletRequest request ,HttpServletResponse resonse){
		
		boolean b1,b2,b3;
		
		if(b1=blockAndValidate(request,resonse)){
			return b1;
		}else{
			if((b2=blockIpBlackList(ipBlackList))){
				return b2;
			}else{
				if(b3=blockUserAgentBlackList(userAgentBlackList)){
					return b3;
				}
			}
		}
		
		return false;
	}
	
}
