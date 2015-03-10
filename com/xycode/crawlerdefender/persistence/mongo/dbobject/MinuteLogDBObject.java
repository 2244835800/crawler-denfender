package com.xycode.crawlerdefender.persistence.mongo.dbobject;

import java.util.Date;

import com.xycode.crawlerdefender.persistence.LogObject;

/**
 * �Է���Ϊά�ȵ���־����
 * @author xiaoyang
 */
public class MinuteLogDBObject extends LogDBObject {

	private static final long serialVersionUID = -7115225072633901955L;

	public MinuteLogDBObject(LogObject log){
		
		super(log);
		
		this.clear();
		
		this.append("url", log.getUrl())
		.append("ip", log.getIp() )
		.append("user_agent", log.getUserAgent())
		.append("time",super.getFormatedTime(log.getTime(),"\\d{2}:\\d{3}","00:000"))
		.append("type", log.getType())//0:��filter�м�¼�£�1����servlet�м�¼��
		.append("count", log.getCount());
		
	}
	
	public static void main(String[] args) {
		LogObject obj=new LogObject();
		obj.setTime(new Date());
		
		System.out.println(new MinuteLogDBObject(obj).get("time"));
	}
}
