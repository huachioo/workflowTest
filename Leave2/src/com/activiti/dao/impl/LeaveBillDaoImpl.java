package com.activiti.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.activiti.dao.LeaveBillDao;
import com.activiti.entity.Employee;
import com.activiti.entity.LeaveBill;

@Repository("leaveBillDao")
public class LeaveBillDaoImpl extends IBaseImpl implements LeaveBillDao {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)
	{

		super.setSessionFactory(sessionFactory);
	}
	

	@Override
	public List<LeaveBill> findLeaveBillList(Employee employee) {
		String hql = "from LeaveBill o where o.user=?";//ָ����ǰ�û�����ٵ�
		@SuppressWarnings("unchecked")
		List<LeaveBill> list = (List<LeaveBill>) this.getHibernateTemplate().find(hql,employee);
		return list;
	}


//	/**ʹ����ٵ�ID����ѯ��ٵ��Ķ���*/
//	@Override
//	public LeaveBill findLeaveBillById(Long id) {
//		return this.getHibernateTemplate().get(LeaveBill.class, id);
//	}


	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(leaveBill);
	}


	@Override
	public void deleteLeaveBillById(long id) {
		// TODO Auto-generated method stub
		LeaveBill leaveBill = (LeaveBill) this.findBillById(id);
		this.getHibernateTemplate().delete(leaveBill);
	}


	@Override
	public Object findBillById(Long id) {
		return this.getHibernateTemplate().get(LeaveBill.class, id);
	}

}
