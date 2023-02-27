package org.example.book;

import org.example.book.engine.MatchingEngine;
import org.example.book.report.OrderBookReport;
import org.example.book.strategy.Order;
import org.example.book.validation.ValidationUtil;
import org.example.digest.DigestContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Iterator;

import static org.example.digest.DigestContext.toByteArray;

public class BookImpl implements Book {

    private MessageDigest md;
    private final BookStore inMemoryStore;
    private final MatchingEngine matchingEngine;
    private final DigestContext digestContext;

    public BookImpl(final DigestContext digestContext,
                    final BookStore inMemoryStore,
                    final MatchingEngine matchingEngine) {
        this.inMemoryStore = inMemoryStore;
        this.matchingEngine = matchingEngine;
        this.digestContext = digestContext;
    }

    public void placeOrder(final Order order) {
        boolean isValid = ValidationUtil.validate(order);
        if (isValid) {
            inMemoryStore.addOrder(order);
            performMatch(order.getId(), order.getOrderType());
        }
    }

    private void performMatch(final String orderId, final Order.OrderType type) {
        final Order aggressingOrder = lookupOrder(orderId, type);
        if (aggressingOrder == null) {
            return;
        }
        Iterator<Order> oppositeSideOrders = inMemoryStore.getOrdersByType(type.isBuy() ? Order.OrderType.SELL : Order.OrderType.BUY);
        if (oppositeSideOrders == null) {
            return;
        }

        digestContext.resetDigest();
        digestContext.update(orderId.getBytes(StandardCharsets.UTF_8));

        boolean isMatch = false;
        int aggressingOrderInitialQuantity = aggressingOrder.getQuantity();
        while (aggressingOrder.getQuantity() > 0 && oppositeSideOrders.hasNext()) {
            final Order restingOrder = oppositeSideOrders.next();
            boolean ordersMatch = matchingEngine.match(aggressingOrder, restingOrder);
            if (ordersMatch) {
                isMatch = true;
                digestContext.update(restingOrder.getId().getBytes(StandardCharsets.UTF_8));
            }
        }
        if (isMatch) {
            digestContext.update(toByteArray(aggressingOrderInitialQuantity - aggressingOrder.getQuantity()));
        }
        cleanUpStorage();
    }


    private Order lookupOrder(String orderId, Order.OrderType type) {
        final Iterator<Order> orders = inMemoryStore.getOrdersByType(type);
        while (orders.hasNext()) {
            final Order order = orders.next();
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void cleanUpStorage() {
        inMemoryStore.cleanUp();
    }

    public void printStatus() {
        final StringBuilder builder = new StringBuilder(100);
        OrderBookReport orderBookReport = new OrderBookReport(inMemoryStore);
        orderBookReport.createReportTable(builder);
        System.out.println(builder);
        digestContext.digest();
    }
}
