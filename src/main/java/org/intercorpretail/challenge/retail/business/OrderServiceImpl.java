package org.intercorpretail.challenge.retail.business;

import lombok.extern.slf4j.Slf4j;
import org.intercorpretail.challenge.retail.business.output.OrderService;
import org.intercorpretail.challenge.retail.repository.OrderRepository;
import org.intercorpretail.challenge.retail.repository.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order order) {
         this.orderRepository.insertOrder(order.getId(), order.getUserId(), order.getTotal(), order.getSubTotal(), order.getTaxes(), order.getDiscounts(), order.getCreatedDate())
                .subscribe(integer -> log.info("Saved order {} with the following result: {}", order.getId(), integer > 0 ? "SUCCESS" : "FAILED"));
    }
}
