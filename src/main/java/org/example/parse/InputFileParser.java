package org.example.parse;

import org.example.book.strategy.Order;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputFileParser {

    final private String ordersInputFile;

    public InputFileParser(final String ordersInputFile) {
        //this.ordersInputFile = ordersInputFile;
        this.ordersInputFile = "/Users/burhancerit/Documents/study/matching-engine/src/main/resources/out/orders.txt";//args[0];;
    }

    public List<Order> parseFile() {
        try {
            final List<Order> orderList = new ArrayList<>();
            final Path inputFilePath = Paths.get(ordersInputFile);
            if (!Files.exists(inputFilePath)) {
                return orderList;
            }
            final List<String> orders = Files.readAllLines(inputFilePath);
            orders.forEach(line -> {
                final Order parsedOrder = parseLine(line);
                orderList.add(parsedOrder);
            });
            return orderList;
        } catch (Exception e) {
            System.out.println("Error parsing file = " + ordersInputFile + ", Exception=" + e);
        }
        return Collections.emptyList();
    }


    private static Order parseLine(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }

        line = line.trim().replace(" ", "");
        String[] input = line.split(",");
        if (input.length != 4) {
            return null;
        }

        String id = input[0];
        String side = input[1];
        int price = Integer.parseInt(input[2]);
        int quantity = Integer.parseInt(input[3]);

        return new Order.Builder()
                .withId(id)
                .withSide(side)
                .withPrice(price)
                .withQuantity(quantity)
                .build();
    }
}
