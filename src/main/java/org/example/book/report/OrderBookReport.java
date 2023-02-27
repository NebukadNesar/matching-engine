package org.example.book.report;

import org.example.book.BookStore;
import org.example.book.strategy.Order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class provides Reporting capability by creating a table like view for the
 * BookStore Bids Side and Asks Side
 */
public class OrderBookReport {

    final StringBuilder spaceBuilder = new StringBuilder(100);
    private final String QUANTITY_HEADER = "   Quantity   ";
    private final String PRICE_HEADER = "   Price   ";
    private final String BIDS_ASKS_HEADER = "       Bids (Buying)      |      Asks (Selling)       ";
    private final String BUY_SIDE = QUANTITY_HEADER + "|" + PRICE_HEADER;
    private final String SELL_SIDE = PRICE_HEADER + "|" + QUANTITY_HEADER;
    private final int QUANTITY_WITDH = QUANTITY_HEADER.length();
    private final int PRICE_WITDH = PRICE_HEADER.length();
    private final String EMPTY_ROW_SPACE = getPadding(QUANTITY_WITDH + PRICE_WITDH + 1);

    private final String TOP_BOTTOM_LAYER = generateLayer(BIDS_ASKS_HEADER.length());

    private final BookStore bookStore;

    public OrderBookReport(final BookStore bookStore) {
        this.bookStore = bookStore;
    }

    public void createReportTable(StringBuilder builder) {
        final List<String[]> reportResult = new ArrayList<>();
        final Iterator<Order> asksIterator = bookStore.getAsksIterator();
        final Iterator<Order> bidsIterator = bookStore.getBidsIterator();

        while (bidsIterator.hasNext() || asksIterator.hasNext()) {
            String[] row = new String[4];
            if (bidsIterator.hasNext()) {
                final Order bid = bidsIterator.next();
                row[0] = String.format("%,d", bid.getQuantity());
                row[1] = String.format("%d", bid.getWorstPrice());
            }
            if (asksIterator.hasNext()) {
                final Order ask = asksIterator.next();
                row[2] = String.format("%d", ask.getWorstPrice());
                row[3] = String.format("%,d", ask.getQuantity());

            }
            reportResult.add(row);
        }

        builder.append(TOP_BOTTOM_LAYER).append("\n")
                .append(BIDS_ASKS_HEADER).append("\n")
                .append(BUY_SIDE).append("|").append(SELL_SIDE).append("\n");

        for (int i = 0; i < reportResult.size(); i++) {
            int padding;
            String[] row = reportResult.get(i);
            String buyQuantity = row[0];
            String buyPrice = row[1];
            if (buyQuantity != null) {
                padding = (QUANTITY_WITDH - buyQuantity.length()) / 2;
                String paddingString = getPadding(padding);
                builder.append(paddingString)
                        .append(buyQuantity)
                        .append(paddingString);

                int buyLen = buyPrice.length();
                padding = (PRICE_WITDH - buyLen) / 2 + 1;
                paddingString = getPadding(padding);
                builder.append(paddingString)
                        .append(buyPrice)
                        .append(paddingString);
            } else {
                builder.append(EMPTY_ROW_SPACE);
            }

            builder.append("|");

            String sellPrice = row[2];
            String sellQuantity = row[3];
            if (sellQuantity != null) {
                int sellLen = sellPrice.length();
                padding = (PRICE_WITDH - sellLen) / 2;
                String paddingString = getPadding(padding);
                builder.append(paddingString)
                        .append(sellPrice)
                        .append(paddingString);

                padding = (QUANTITY_WITDH - sellQuantity.length()) / 2 + 1;
                paddingString = getPadding(padding);
                builder.append(paddingString)
                        .append(sellQuantity)
                        .append(paddingString);
            }
            builder.append("\n");
        }
        builder.append(TOP_BOTTOM_LAYER);
    }

    private String getPadding(int padding) {
        spaceBuilder.setLength(0);
        for (int i = 0; i < padding; i++) {
            spaceBuilder.append(" ");
        }
        return spaceBuilder.toString();
    }

    private String generateLayer(int headerLen) {
        spaceBuilder.setLength(0);
        for (int i = 0; i < headerLen; i++) {
            spaceBuilder.append("-");
        }
        return spaceBuilder.toString();
    }
}
