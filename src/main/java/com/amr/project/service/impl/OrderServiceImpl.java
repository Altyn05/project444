package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.OrderDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Order;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.util.EmailUtil;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl
        extends ReadWriteServiceImpl<Order, Long>
        implements OrderService
{

    private final OrderDao orderDao;
    private final EmailUtil emailUtil;

    public OrderServiceImpl(
            ReadWriteDao<Order, Long> readWriteDao,
            OrderDao orderDao,
            EmailUtil emailUtil) {
        super(readWriteDao);
        this.orderDao = orderDao;
        this.emailUtil = emailUtil;
    }

    @Override
    public void persist(Order order) {
        orderDao.persist(order);
        emailUtil.sendMessage(
                order.getUser().getEmail(),
                "Новый заказ ID " + order.getId(),
                "Здравствуйте, " + order.getUser().getFirstName() + "!\n" +
                        "Ваш заказ ID " + order.getId() + " на сумму " + order.getTotal() + " рублей был успешно добавлен."
        );
    }

    @Override
    public void update(Order order) {
        Status oldStatus = getOrderStatus(order.getId());

        orderDao.update(order);

        if (oldStatus != order.getStatus()) {
            emailUtil.sendMessage(
                    order.getUser().getEmail(),
                    "Изменение заказа ID " + order.getId(),
                    "Здравствуйте, " + order.getUser().getFirstName() + "!\n" +
                            "Статус Вашего заказа был изменён на:\n" +
                            order.getStatus()
            );
        }
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public Status getOrderStatus(Long id) {
        return orderDao.getOrderStatus(id);
    }
}
