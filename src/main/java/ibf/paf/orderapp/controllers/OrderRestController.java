package ibf.paf.orderapp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf.paf.orderapp.models.OrderSummary;
import ibf.paf.orderapp.models.PurchaseOrder;
import ibf.paf.orderapp.services.OrderService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {
    private static final Logger logger = Logger.getLogger(OrderRestController.class.getName());

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<String> getAllOrders() {
        Optional<List<OrderSummary>> opt = orderService.getAllOrders();
        if (opt.isPresent()) {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            opt.get().stream().forEach(i -> arrBuilder.add(i.toJson()));
            return ResponseEntity.ok(arrBuilder.build().toString());
        } else {
            return ResponseEntity.ok(Json.createArrayBuilder().build().toString());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrder(@RequestBody String payload) {
        PurchaseOrder po = PurchaseOrder.create(payload);
        logger.info(po.toString());
        Optional<Integer> opt = orderService.addOrder(po);
        if (opt.isPresent()) {
            JsonObject body = Json.createObjectBuilder()
                    .add("message", "Purchase Order Id: %s".formatted(opt.get()))
                    .build();
            return ResponseEntity.ok(body.toString());
        } else {
            JsonObject err = Json.createObjectBuilder()
                    .add("error", "Failed to make purchase order")
                    .build();
            return ResponseEntity.badRequest().body(err.toString());
        }
    }
}
