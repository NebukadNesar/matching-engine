package org.example.book.engine;

import org.example.book.strategy.OrderContext;

/**
 * Basic Mathing Engine
 * <p>
 * Provides capability to perform match against AggressingOrder
 * and RestingOrders from Bids vs Asks sides depending of the
 * AggressingOrder side
 */
public interface MatchingEngine {

    /**
     * Performs match agains resting orders and immediately updates the
     * OrderContexts until Order Quantity drained to zero
     *
     * @param aggressingOrder
     * @param restingOrder
     * @return
     */
    boolean match(final OrderContext aggressingOrder, final OrderContext restingOrder);
}
