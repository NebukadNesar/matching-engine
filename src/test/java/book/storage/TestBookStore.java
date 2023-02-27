package book.storage;

import org.example.book.BookStore;
import org.example.book.strategy.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


public class TestBookStore {

    @Test
    public void testOrderOfInsertById() {

        final String[] bulkOrderIds =
                {"109", "107", "103", "104", "105",
                        "102", "101", "100", "106"};

        BookStore bookStore = new BookStore();
        for (String orderId : bulkOrderIds) {
            bookStore.addOrder(new Order.Builder()
                    .withId(orderId)
                    .withSide("S")
                    .withPrice(100)
                    .withQuantity(10001)
                    .build());
        }

        Iterator<Order> askIterator = bookStore.getAsksIterator();
        int index = 0;
        Arrays.sort(bulkOrderIds);
        while (askIterator.hasNext()) {
            String actual = bulkOrderIds[index++];
            String expected = askIterator.next().getId();
            Assert.assertTrue("sequence of Orders is not correct in BookStore",
                    Objects.equals(expected, actual));
        }
    }

    @Test
    public void addSameOrderIdOrdersForUniqenessTest() {
        final String[] bulkOrderIds =
                {"109", "101", "101", "105", "105",
                        "102", "101", "106", "106"};

        BookStore bookStore = new BookStore();
        for (String orderId : bulkOrderIds) {
            bookStore.addOrder(new Order.Builder()
                    .withId(orderId)
                    .withSide("S")
                    .withPrice(100)
                    .withQuantity(10001)
                    .build());
        }

        Iterator<Order> askIterator = bookStore.getAsksIterator();
        int uniqIdCount = 0;
        Set<String> uniqIds = new HashSet<>(Arrays.asList(bulkOrderIds));
        uniqIdCount = uniqIds.size();
        while (askIterator.hasNext()) {
            uniqIds.remove(askIterator.next().getId());
            uniqIdCount--;
        }
        Assert.assertTrue("Uniq Id counts dont match, BookStore cannot handle uniqness",
                uniqIds.size() == uniqIdCount);
    }
}
