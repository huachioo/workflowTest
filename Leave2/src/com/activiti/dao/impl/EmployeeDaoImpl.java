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

	@Override
	public List<Employee> findEmployeeList() {
		// TODO Auto-generated method stub
		String hql = "from Employee";
		@SuppressWarnings("unchecked")
		List<Employee> list = (List<Employee>) this.getHibernateTemplate().find(hql);
		return list;
	}


	@Override
	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(employee);
	}

	@Override
	public Employee findEmployById(long id) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Employee.class, id);
	}
	
	@Override
	public void deleteEmployeeById(Long id) {
		//2：使用请假单ID，查询请假单信息，获取对象LeaveBill
		Employee employee = this.findEmployById(id);
		//3：执行删除
		this.getHibernateTemplate().delete(employee);
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		this.getHibernateTemplate().update(employee);
	}
	

}
