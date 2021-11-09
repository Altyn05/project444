package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;

public interface OrderDao extends ReadWriteDao<Order, Long> {
    Order findById(Long id);
    Status getOrderStatus(Long id);
}
