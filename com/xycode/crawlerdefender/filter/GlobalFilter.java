package com.xycode.crawlerdefender.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xycode.crawlerdefender.blocker.AbstractBlocker;
import com.xycode.crawlerdefender.blocker.simple.SimpleBlocker;
import com.xycode.crawlerdefender.config.Config;
import com.xycode.crawlerdefender.counter.ICounter;
import com.xycode.crawlerdefender.counter.simple.SimpleCounter;
import com.xycode.crawlerdefender.logcache.CacheManagement;
import com.xycode.crawlerdefender.persistence.IPersistence;
import com.xycode.crawlerdefender.persistence.LogObject;
import com.xycode.crawlerdefender.persistence.mongo.MongoPersistence;

/**
 * ������ҪӦ�÷������ҳ���filter
 * <p>��filter�����ã�</p>
 * 1.����ҳ���ܷ�֮ǰ��¼������־<br/>
 * 2.���ܷ�ҳ��ע��ajax�ű�<br/>
 * 3.��ֹ��������ip�ķ��ʡ�<br/>
 * 4.����ɵ�ip������֤ҳ��<br/>
 * 5.��λʱ���ڷ��ʳ���ָ����ֵ��ip������֤ҳ��<br/>
 * 
 * <p>����ж����棺</p>
 *  
 * 1.�ڷ���ҳ�����ʱ�����������ȷִ��ע���ajax���������������򲻻�ִ��js��
 * ��˶Ա�filter�еķ��ʼ�¼��ajax����ķ��ʼ�¼�ɱ���һ�������档<br/>
 * 2.���ʳ������õķ�ֵ��û�����У��ҳ�浫��Ȼ��Ƶ�����ʣ������ж�Ϊ���档<br/>
 * 3.��η���Bot Trap(��������)��Ҳ�����ж�Ϊ���档<br/>
 * @author xiaoyang
 */
public class GlobalFilter implements Filter {
	
	private IPersistence pc;
	
	private ICounter counter;
	
	private AbstractBlocker blocker;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		//����configfile
		{
			String configPath= arg0.getInitParameter("configFile");
			
			if(configPath!=null && !"".endsWith(configPath)){
				Config.setConfigFilePath(configPath);
			}
			
			Config.getInstance();
		}
		
		
		//��ʼ���־û���������������������
		try {
			
			pc=MongoPersistence.getInstance();
			
			counter=SimpleCounter.getInstance();
			
			blocker=new SimpleBlocker();
			
			pc.init();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest  request=(HttpServletRequest)arg0;
		HttpServletResponse resonse=(HttpServletResponse)arg1;
		
		
		String ip=request.getRemoteAddr();//.subSequence(beginIndex, endIndex);
		
		String userAgent=request.getHeader("User-Agent");
		String url=request.getRequestURL().toString();
		
		if(url.contains("?")){//ȥ������
			url=url.substring(0, url.indexOf("?"));
		}
		
		Date time=new Date();
		
		//����
		if(blocker.block(request,resonse)) return;
		
		//��¼filter������־����д�뻺��
		{
			LogObject  log= new LogObject(url, ip, userAgent, time,0);
			CacheManagement.getLogCachePool().add(log);
		}
		
		//д������
		{
			counter.secondCount(ip);
			counter.minuteCount(ip);
		}
		
		
		//���ܷ�ҳ�����ajax�ű�
		resonse.getWriter().print("<script> (function(){ var xmlhttp= window.XMLHttpRequest  ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP'); if (xmlhttp) { xmlhttp.open('GET', '"+request.getContextPath()+"/crawler-defender/logger', true ); xmlhttp.send( null ); } })(); </script>\r\n");
		
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void destroy() {
		pc.destroy();
	}

}
