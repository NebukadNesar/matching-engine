package org.example.book.validation;

import org.example.book.strategy.Order;

final public class ValidationUtil {

    private static final long MAX_PRICE = 999_999;
    private static final int MAX_QUANTITY = 999_999_999;

    public static boolean validate(final Order order) {
        if (order == null) {
            return false;
        }

        if (order.getQuantity() > MAX_QUANTITY) {
            return false;
        }

        if (order.getWorstPrice() > MAX_PRICE) {
            return false;
        }

        return Order.OrderType.get(order.getSide()) != null;
    }

}
