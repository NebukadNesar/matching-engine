package book.strategy;

import org.example.book.strategy.Order;
import org.example.book.strategy.OrderContext;
import org.junit.Assert;
import org.junit.Test;

public class TestOrderContext {

    @Test
    public void testOrdersComparability() {
        OrderContext order1 = new Order.Builder()
                .withId("1001")
                .build();

        OrderContext order2 = new Order.Builder()
                .withId("1001")
                .build();

        OrderContext order3 = new Order.Builder()
                .withId("1003")
                .build();


        Assert.assertTrue("Orders are not compared by their ID correctly, Must be Equals", order1.compareTo((Order) order2) == 0);
        Assert.assertTrue("Orders are not compared by their ID correctly, Must be Different", order1.compareTo((Order) order3) != 0);
    }
}
