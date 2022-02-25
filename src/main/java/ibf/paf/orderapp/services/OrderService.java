package ibf.paf.orderapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.orderapp.models.OrderSummary;
import ibf.paf.orderapp.models.PurchaseOrder;
import ibf.paf.orderapp.repositories.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;

    public Optional<Integer> addOrder(PurchaseOrder order) {

        int result = orderRepo.addOrder(order);
        if (result == -1) {
            return Optional.empty();
        } else {
            // result is the order id - oId
            return Optional.of(result);
        }
    }

    public Optional<List<OrderSummary>> getAllOrders() {
        List<OrderSummary> result = orderRepo.getAllOrders();
        return Optional.ofNullable(result);
    }
}
