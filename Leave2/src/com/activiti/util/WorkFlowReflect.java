package com.activiti.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.activiti.entity.Employee;

public class WorkFlowReflect {
	//根据传入的表单名获取object对象
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
	//修改对象的state值
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
	//自动生成目标对象的dao并赋值
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
	
	//获取object对象的方法，执行该方法
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
	
	//自动生成目标对象的service并获取list
		@SuppressWarnings("unchecked")
		public static List<Object> getList(Employee employee,String formName){
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			List<Object> list = null;
			char[] a = formName.toCharArray();
			a[0] = Character.toLowerCase(a[0]);
			String formName2 = "";
			for(int i=0;i<a.length;i++)
			{
				formName2=formName2+a[i];
			}
			String serviceName = formName2+"Service";
			Object objectService = ac.getBean(serviceName);
			Method queryList = null;
			String methodName = "find" + formName + "List";
			try {
				queryList = objectService.getClass().getDeclaredMethod(methodName,Employee.class);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				list = (List<Object>) queryList.invoke(objectService, employee);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

}
