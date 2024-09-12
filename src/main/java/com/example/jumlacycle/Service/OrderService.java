package com.example.jumlacycle.Service;

import com.example.jumlacycle.API.ApiException;

import com.example.jumlacycle.Model.*;
import com.example.jumlacycle.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;

    //extra endpoint
    public List<Order> getAllOrder(Integer userId){
        if (!orderRepository.findOrdersByFacilityId(userId).isEmpty()){
            return orderRepository.findOrdersByFacilityId(userId);
        } else if (!orderRepository.findOrdersByCustomerId(userId).isEmpty()) {
            return orderRepository.findOrdersByCustomerId(userId);
        }else {
            throw new ApiException("You dont have any orders");
        }
    }

    //extra endpoint
    public Order getOrderById(Integer orderId,Integer userId){
        if (orderRepository.findByOrderIdAndCustomerId(orderId,userId)!=null){
            return orderRepository.findByOrderIdAndCustomerId(orderId,userId);
        } else if (orderRepository.findOrderByIdAndFacilityId(orderId,userId)!=null) {
            return orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        }else {
            throw new ApiException("You dont have any order by this id: "+orderId);
        }
    }

    //extra endpoint
    //there is error here in  "order.getProducts().add(product);"
    public void addNewOrder(Integer productId, Order order, Integer userId) {
        User user = authRepository.findUserById(userId);
        Product product=productRepository.findProductById(productId);
        if (product==null){
            throw new ApiException("You dont have any product to add order");
        }else {
            if (user.getRole().equalsIgnoreCase("customer")) {
                Customer customer = customerRepository.findCustomerById(user.getId());
                order.setCustomer_orders(customer);
                product.setQuantity(order.getQuantity()-product.getQuantity());
                order.getProducts().add(product);
                orderRepository.save(order);
                productRepository.save(product);
            } else if (user.getRole().equalsIgnoreCase("Facility")) {
                Facility facility = facilityRepository.findFacilityById(user.getId());
                order.setFacility_orders(facility);
                order.getProducts().add(product);
                product.getOrders().add(order);
                orderRepository.save(order);
                productRepository.save(product);
            } else {
                throw new ApiException("Invalid role to add order");
            }

        }

    }

    //extra endpoint
    public void updateOrder(Order order, Integer orderId,Integer userId){
        Order oldOrder = orderRepository.findOrderById(orderId);
        if (authRepository.findUserById(userId).getRole().equalsIgnoreCase("customer")){
            Customer customer=customerRepository.findCustomerById(userId);
            oldOrder.setCustomer_orders(customer);
            oldOrder.setQuantity(order.getQuantity());
            oldOrder.setProductName(order.getProductName());
            oldOrder.setShippingMethod(oldOrder.getShippingMethod());
            orderRepository.save(oldOrder);
        }else if (authRepository.findUserById(userId).getRole().equalsIgnoreCase("Facility")){
            Facility facility=facilityRepository.findFacilityById(userId);
            oldOrder.setFacility_orders(facility);
            oldOrder.setQuantity(order.getQuantity());
            oldOrder.setProductName(order.getProductName());
            oldOrder.setShippingMethod(oldOrder.getShippingMethod());
            oldOrder.setTotalAmount(order.getTotalAmount());
            orderRepository.save(oldOrder);
        } else {
            throw new ApiException("Invalid role to update order");
        }
    }

    public void deleteOrder(Integer orderId,Integer userId){
        if (orderRepository.findByOrderIdAndCustomerId(orderId,userId)!=null){
            Order order = orderRepository.findByOrderIdAndCustomerId(orderId,userId);
            order.getProducts().remove(productRepository.findProductById(orderId));
            orderRepository.deleteById(orderId);
        }else if (orderRepository.findOrderByIdAndFacilityId(orderId,userId)!=null){
            Order order = orderRepository.findOrderByIdAndFacilityId(orderId,userId);
            order.getProducts().remove(productRepository.findProductById(orderId));
            orderRepository.deleteById(orderId);
        }else {
            throw new ApiException("Order not found");
        }
    }
}
