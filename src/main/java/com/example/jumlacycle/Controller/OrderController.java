package com.example.jumlacycle.Controller;

import com.example.jumlacycle.Model.Order;
import com.example.jumlacycle.Model.User;
import com.example.jumlacycle.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get-order-by-userId/{orderId}")
    public ResponseEntity findOrderByUserId(Integer orderId, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getOrderById(orderId, user.getId()));
    }

    @GetMapping("/get-all-by-userId")
    public ResponseEntity findAllByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getAllOrder(user.getId()));
    }

    @PostMapping("/add-order/{productId}")
    public ResponseEntity addOrder(@PathVariable Integer productId,@Valid @RequestBody Order order, @AuthenticationPrincipal User user) {
        orderService.addNewOrder(productId,order, user.getId());
        return ResponseEntity.status(200).body("order added successfully");
    }

    @PutMapping("/update-by-userId/{orderId}")
    public ResponseEntity updateOrderById(@Valid @RequestBody Order order, Integer orderId, @AuthenticationPrincipal User user) {
        orderService.updateOrder(order, orderId, user.getId());
        return ResponseEntity.status(200).body("order updated successfully");
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity deleteOrderById(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.deleteOrder(orderId, user.getId());
        return ResponseEntity.status(200).body("order deleted successfully");
    }
}
