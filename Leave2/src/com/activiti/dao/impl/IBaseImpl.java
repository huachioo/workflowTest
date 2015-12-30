package com.activiti.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.activiti.dao.IBase;
import com.activiti.entity.LeaveBill;
@Repository("IBase")
public class IBaseImpl extends HibernateDaoSupport implements IBase{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)
	{

		super.setSessionFactory(sessionFactory);
	}


	@Override
	public Object findBillById(Long id) {
		return this.getHibernateTemplate().get(LeaveBill.class, id);
	}

	

}
