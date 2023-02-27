package book.matchingengine;

import org.example.book.engine.MatchingEngine;
import org.example.book.engine.MatchingEngineImpl;
import org.example.book.strategy.Order;
import org.example.book.strategy.OrderContext;
import org.junit.Assert;
import org.junit.Test;

public class TestMathingEngine {

    @Test
    public void testMathingEnginePerformMatch() {
        OrderContext aggressingOrder = new Order.Builder()
                .withId("1001")
                .withSide("S")
                .withPrice(98)
                .withQuantity(1001)
                .build();

        OrderContext restingOrder = new Order.Builder()
                .withId("1002")
                .withSide("B")
                .withPrice(98)
                .withQuantity(889)
                .build();


        MatchingEngine matchingEngine = new MatchingEngineImpl();
        final boolean isMatch = matchingEngine.match(aggressingOrder, restingOrder);

        Assert.assertTrue("Mathing Engine did not perform match correctly ", isMatch);
        Assert.assertTrue("AggressingOrder is not updated correctly after match", aggressingOrder.getQuantity() == 1001 - 889);
        Assert.assertTrue("RestingOrder is not updated correctly after match ", restingOrder.getQuantity() == 0);


    }
}
