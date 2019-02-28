package cn.ssijri.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * ��ǿ����
 * @author ����
 *
 */
@Aspect
public class MyBook {
	
	@Before(value="execution(* cn.ssijri.aop.Book.*(..))")
	public void before() {
		System.out.println("before...");
	}
	@After(value="execution(* cn.ssijri.aop.Book.*(..))")
	public void after() {
		System.out.println("after...");
	}
	@Around(value="execution(* cn.ssijri.aop.Book.*(..))")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		System.out.println("����ִ��ǰ ...");
		proceedingJoinPoint.proceed();
		System.out.println("����ִ�к� ...");
	}
}
