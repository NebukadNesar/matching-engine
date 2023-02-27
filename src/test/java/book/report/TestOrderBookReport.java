package book.report;

import org.example.book.BookStore;
import org.example.book.report.OrderBookReport;
import org.example.book.strategy.Order;
import org.junit.Assert;
import org.junit.Test;

public class TestOrderBookReport {


    @Test
    public void testOrderBookStatusReporting() {

        final String[] bulkOrderIds =
                {"101", "102", "103", "104", "105",
                        "106", "107", "108", "109"};
        final String[] sides =
                {"S", "B", "S", "B", "B",
                        "B", "S", "B", "S"};
        BookStore bookStore = new BookStore();
        int index = 0;
        for (String orderId : bulkOrderIds) {
            bookStore.addOrder(new Order.Builder()
                    .withId(orderId)
                    .withSide(sides[index++])
                    .withPrice(100)
                    .withQuantity(10001)
                    .build());
        }

        OrderBookReport orderBookReport = new OrderBookReport(bookStore);
        final StringBuilder report = new StringBuilder(10 * 50);
        orderBookReport.createReportTable(report);
        
        //a simple test to see a desired output included inside the report
        Assert.assertTrue("OrderBookReport did not produce the correct report",
                report.indexOf("10,001") > -1);
    }
}
