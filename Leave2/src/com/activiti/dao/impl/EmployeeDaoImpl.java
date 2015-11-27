package com.activiti.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.activiti.dao.EmployeeDao;
import com.activiti.entity.Employee;
@Repository("employeeDao")
public class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)
	{

		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Employee findEmployeeByName(String username) {
		String hql = "from Employee o where o.name = ?";
		@SuppressWarnings("unchecked")
		List<Employee> list = (List<Employee>) this.getHibernateTemplate().find(hql, username);
		Employee employee = null;
		if(list!=null && list.size()>0){
			employee = list.get(0);
		}
		return employee;
	}

}
