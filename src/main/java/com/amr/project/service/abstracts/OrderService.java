package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;

public interface OrderService extends ReadWriteService<Order, Long> {
    Order findById(Long id);
    Status getOrderStatus(Long id);
}
