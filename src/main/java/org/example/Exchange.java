package org.example;

import org.example.book.BookImpl;
import org.example.book.BookStore;
import org.example.book.engine.MatchingEngine;
import org.example.book.engine.MatchingEngineImpl;
import org.example.digest.DigestContext;
import org.example.parse.InputFileParser;

public class Exchange {

    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                System.out.println("No input! Exiting...");
                return;
            }
            InputFileParser fileParser = new InputFileParser(args[0]);
            final BookStore internalStore = new BookStore();
            final MatchingEngine matchingEngine = new MatchingEngineImpl();
            final DigestContext digestContext = new DigestContext("MD5");
            final BookImpl book = new BookImpl(digestContext, internalStore, matchingEngine);
            fileParser.parseFile().forEach(book::placeOrder);
            book.printStatus();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fatal error occurred during parsing and placing orders = " + e);
        }
    }
}