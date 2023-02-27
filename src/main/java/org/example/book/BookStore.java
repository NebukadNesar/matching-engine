package org.example.book;

import org.example.book.strategy.Order;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Class provides and in memory solution to store orders
 * Ask and Bids orders are stored separately, whenever an
 * aggressing order places opposite side will be iterated and
 * perform match against them.
 */
public class BookStore {

    /*
     * Comparable naturally
     */
    private final TreeSet<Order> bidsQueue = new TreeSet<>();
    private final TreeSet<Order> asksQueue = new TreeSet<>();

    public void addOrder(final Order order) {
        if (order.getOrderType().isBuy()) {
            bidsQueue.add(order);
        } else {
            asksQueue.add(order);
        }
    }

    public Iterator<Order> getBidsIterator() {
        return bidsQueue.iterator();
    }

    public Iterator<Order> getAsksIterator() {
        return asksQueue.iterator();
    }

    public void cleanUp() {
        cleanUpStorage(getAsksIterator());
        cleanUpStorage(getBidsIterator());
    }

    private void cleanUpStorage(Iterator<Order> iterator) {
        while (iterator.hasNext()) {
            final Order cleanOrder = iterator.next();
            final long quantity = cleanOrder.getQuantity();
            if (quantity == 0) {
                iterator.remove();
            }
        }
    }

    public Iterator<Order> getOrdersByType(Order.OrderType type) {
        switch (type) {
            case BUY:
                return getBidsIterator();
            case SELL:
                return getAsksIterator();
            default:
                return null;
        }
    }
}
