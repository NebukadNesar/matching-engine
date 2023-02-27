package org.example.book.strategy;

/**
 * Class provides and Order details access capability
 * Comparable by Order ID on ASC order
 */
public interface OrderContext extends Comparable<Order> {

    String getId();

    int getQuantity();

    int getWorstPrice();

    String getSide();

    void updateQuantity(long updateAmount);

    Order.OrderType getOrderType();

}
