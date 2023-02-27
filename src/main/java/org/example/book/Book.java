package org.example.book;

import org.example.book.strategy.Order;


/**
 * Class provides a simple OrderBook capability
 */
public interface Book {

    /**
     * Places and Order into orderBook and store in BookStore
     *
     * @param order
     */
    void placeOrder(Order order);

    /**
     * CleanUp BookStore where Orders are matched and traded and quantity drained to zero
     */
    void cleanUpStorage();

    /**
     * Prepare and Print the current status of the OrderBook
     * Bids Side and Asks Side
     * and MD5 sum of the output
     */
    void printStatus();
}
