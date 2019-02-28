package cn.ssijri.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AdviceTarget {
	
	public void beforeAdd() {
		System.out.println("before....");
	}
	
	public void afterAdd() {
		System.out.println("after... ...");
	}
	
	/**
	 * ������ǿ
	 * @param proceedingJoinPoint
	 * @throws Throwable 
	 */
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		//����֮ǰ
		System.out.println("����ִ��֮ǰ...");
		
		//ִ�б���ǿ�ķ���
		proceedingJoinPoint.proceed();
		
		//����֮��
		System.out.println("����ִ��֮��...");
	}
}
