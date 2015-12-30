package com.activiti.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WorkFlowReflect {
	public static  Object getObject(long id, String formName){
		@SuppressWarnings("rawtypes")
		Class classType = null;
		Object object = null;
		String className = "com.activiti.entity."+formName;
		try {
			classType = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			object = classType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		object = WorkFlowReflect.inputPath(id,formName);
		return object;		
	}
	
	//修改表单状态 0 未审批 1已审批 2 审批完成
	public static void  setState(Object object,int state){
		Method setState = null;
		try {
			setState = object.getClass().getDeclaredMethod("setState",Integer.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setState.invoke(object, state);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object inputPath(long id,String formName){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		Object object = null;
		char[] a = formName.toCharArray();
		a[0] = Character.toLowerCase(a[0]);
		String formName2 = "";
		for(int i=0;i<a.length;i++)
		{
			formName2=formName2+a[i];
		}
		String daoName = formName2+"Dao";
		Object objectDao = ac.getBean(daoName);
		Method findBillById = null;
		try {
			findBillById = objectDao.getClass().getDeclaredMethod("findBillById",Long.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			object = findBillById.invoke(objectDao, id);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	public static int doMethod(Object object,String MethodName){
		Method method = null;
		String methodName = "get"+MethodName;
		int i = 0;
		try {
		    method = object.getClass().getDeclaredMethod(methodName);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			i = (int) method.invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
		
	}

}
