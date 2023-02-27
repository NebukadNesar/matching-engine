package org.example.book.strategy;

import java.util.Objects;

/**
 * Immutable class for Order details
 * Comparable by Order Id in ASC order
 * and Quantity is updated upon successful
 * match.
 */
public class Order implements OrderContext {

    private final String id;
    private final String side;
    private final OrderType orderType;
    private final int worstPrice;
    private int quantity; // quantity updated

    private Order(final String id, final String side, final int worstPrice, final int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.worstPrice = worstPrice;
        this.side = side;
        this.orderType = OrderType.get(side);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSide() {
        return side;
    }

    @Override
    public int getWorstPrice() {
        return worstPrice;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public void updateQuantity(final long updateAmount) {
        this.quantity += updateAmount;
    }

    public static class Builder {

        private String id;
        private String side;
        private int price;
        private int quantity;

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withSide(final String side) {
            this.side = side;
            return this;
        }

        public Builder withPrice(final int price) {
            this.price = price;
            return this;
        }

        public Builder withQuantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Order build() {
            return new Order(this.id, this.side, this.price, this.quantity);
        }
    }

    @Override
    public int compareTo(Order o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum OrderType {
        BUY,
        SELL;

        public static OrderType get(String side) {
            if ("S".equals(side)) {
                return SELL;
            }
            if ("B".equals(side)) {
                return BUY;
            }
            return null;
        }

        public boolean isBuy() {
            return this == BUY;
        }

    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", side='" + side + '\'' +
                ", orderType=" + orderType +
                ", worstPrice=" + worstPrice +
                ", quantity=" + quantity +
                '}';
    }
}
