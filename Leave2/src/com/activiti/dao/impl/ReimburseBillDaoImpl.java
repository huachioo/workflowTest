package com.activiti.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.activiti.dao.ReimburseBillDao;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.ReimburseBill;
@Repository("reimnburseBillDao")
public class ReimburseBillDaoImpl extends IBaseImpl implements ReimburseBillDao {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)
	{

		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public void saveReimburseBill(ReimburseBill reimburseBill) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(reimburseBill);
	}


	@Override
	public List<ReimburseBill> findReimburseBillList(Employee employee) {
		String hql = "from ReimburseBill o where o.employee=?";//指定当前用户的请假单
		@SuppressWarnings("unchecked")
		List<ReimburseBill> list = (List<ReimburseBill>) this.getHibernateTemplate().find(hql,employee);
		return list;
	}


	@Override
	public Object findBillById(Long id) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(ReimburseBill.class, id);
	}

}
