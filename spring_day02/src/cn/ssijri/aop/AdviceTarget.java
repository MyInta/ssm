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
	 * 环绕增强
	 * @param proceedingJoinPoint
	 * @throws Throwable 
	 */
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		//方法之前
		System.out.println("方法执行之前...");
		
		//执行被增强的方法
		proceedingJoinPoint.proceed();
		
		//方法之后
		System.out.println("方法执行之后...");
	}
}
