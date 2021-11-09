package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDaoImpl
        extends ReadWriteDaoImpl<Order, Long>
        implements OrderDao
{

    @PersistenceContext
    EntityManager em;

    @Override
    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    @Override
    public Status getOrderStatus(Long id) {
        return em.createQuery("SELECT status FROM Order WHERE id = :id", Status.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
