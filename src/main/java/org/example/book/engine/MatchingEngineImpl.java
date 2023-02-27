package org.example.book.engine;

import org.example.book.strategy.Order;
import org.example.book.strategy.OrderContext;


public class MatchingEngineImpl implements MatchingEngine {

    public MatchingEngineImpl() {
    }

    private boolean isTradeableOrders(final OrderContext aggressingOrder, final OrderContext restingOrder) {
        if (aggressingOrder == null || restingOrder == null) {
            return false;
        }

        final Order.OrderType aggressingOrderType = aggressingOrder.getOrderType();
        final Order.OrderType restingOrderType = restingOrder.getOrderType();

        if (aggressingOrderType == restingOrderType) {
            return false;
        }

        if (aggressingOrder.getQuantity() == 0 || restingOrder.getQuantity() == 0) {
            return false;
        }

        final double aggressingOrderPrice = aggressingOrder.getWorstPrice();
        final double storageOrderPrice = restingOrder.getWorstPrice();
        if (aggressingOrderType.isBuy()) {
            return aggressingOrderPrice >= storageOrderPrice;
        } else {
            return aggressingOrderPrice <= storageOrderPrice;
        }
    }

    @Override
    public boolean match(final OrderContext aggressingOrder, final OrderContext restingOrder) {
        if (isTradeableOrders(aggressingOrder, restingOrder)) {

            final long aggressingOrderQuantity = aggressingOrder.getQuantity();
            final long restingOrderQuantity = restingOrder.getQuantity();

            final long min = Math.min(aggressingOrderQuantity, restingOrderQuantity);

            aggressingOrder.updateQuantity(-min);
            restingOrder.updateQuantity(-min);

            return true;
        }
        return false;
    }
}
