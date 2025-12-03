package com.interview.candidateproject.service;

import com.interview.candidateproject.entity.Order;
import com.interview.candidateproject.entity.Order.OrderStatus;
import com.interview.candidateproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setOrderStatus(newStatus);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    public List<Order> getOrdersWithMinimumAmount(BigDecimal minAmount) {
        return orderRepository.findOrdersWithMinimumAmount(minAmount);
    }

    public long countUserOrdersByStatus(Long userId, OrderStatus status) {
        return orderRepository.countByUserIdAndStatus(userId, status);
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #7
     * 
     * This method should cancel an order if it's in a cancellable state.
     * 
     * Requirements:
     * - Find the order by ID (throw RuntimeException with message "Order not found"
     * if not found)
     * - Only orders with status PENDING or CONFIRMED can be cancelled
     * - If order status is SHIPPED, DELIVERED, or already CANCELLED, throw
     * IllegalStateException
     * with message "Order cannot be cancelled in current state: {currentStatus}"
     * - Update the status to CANCELLED and save the order
     * - Return the updated order
     * 
     * @param orderId The ID of the order to cancel
     * @return The cancelled order
     * @throws RuntimeException      if order not found
     * @throws IllegalStateException if order cannot be cancelled
     */
    public Order cancelOrder(Long orderId) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #8
     * 
     * This method should calculate the total revenue from completed orders within a
     * date range.
     * 
     * Requirements:
     * - Validate that startDate is not null (throw IllegalArgumentException)
     * - Validate that endDate is not null (throw IllegalArgumentException)
     * - Validate that startDate is before or equal to endDate (throw
     * IllegalArgumentException)
     * - Only include orders with status DELIVERED
     * - Sum up the totalAmount of all matching orders
     * - Return BigDecimal.ZERO if no orders match
     * 
     * Hint: Use getOrdersInDateRange and filter by status, then sum amounts
     * 
     * @param startDate The start of the date range
     * @param endDate   The end of the date range
     * @return The total revenue from delivered orders
     * @throws IllegalArgumentException if dates are invalid
     */
    public BigDecimal calculateRevenueInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #9
     * 
     * This method should return the most recent orders for a user, limited to a
     * specified count.
     * 
     * Requirements:
     * - Validate that userId is not null (throw IllegalArgumentException with
     * message "User ID cannot be null")
     * - Validate that limit is positive (throw IllegalArgumentException with
     * message "Limit must be positive")
     * - Get all orders for the user
     * - Sort by orderDate in descending order (most recent first)
     * - Return only the first 'limit' orders
     * 
     * Hint: Use Java Stream API for sorting and limiting
     * 
     * @param userId The user ID
     * @param limit  Maximum number of orders to return
     * @return List of most recent orders for the user
     * @throws IllegalArgumentException if parameters are invalid
     */
    public List<Order> getRecentOrdersByUser(Long userId, int limit) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }
}
