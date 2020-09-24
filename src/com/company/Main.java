package com.company;

import java.util.*;

public class Main {
    public static String appendSpace(String text, int length) {
        while (text.length() < length) {
            text += " ";
        }
        return text;
    }

    public static String prependSpace(String text, int length) {
        while (text.length() < length) {
            text = " " + text;
        }
        return text;
    }

    public static class Table {
        public int col;
        public List<String[]> rows = new ArrayList<>();
        public int[] colWidths;

        public Table(int col) {
            this.col = col;
            this.colWidths = new int[col];
        }

        void addRow(String[] cols) {
            this.rows.add(cols);
            for (int i = 0; i < this.col; i++) {
                this.colWidths[i] = Math.max(this.colWidths[i], cols[i].length());
            }
        }

        void print() {
            for (String[] cols : this.rows) {
                String col0 = appendSpace(cols[0], this.colWidths[0]);
                String col1 = prependSpace(cols[1], this.colWidths[1]);
                String col2 = prependSpace(cols[2], this.colWidths[2]);
                System.out.println(col0 + "\t" + col1 + "\t" + col2);
            }
        }
    }

    public static final String[] foods = new String[]{"potato chips"};
    public static final String[] clothes = new String[]{"shoe"};

    public static class Product {
        public static boolean isFood(String productName) {
            for (String name : foods) {
                if (name.equals(productName)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean isClothing(String productName) {
            for (String name : clothes) {
                if (name.equals(productName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static double calcTaxRate(String location, String productName) {
        if (location.equals("CA")) {
            if (Product.isFood(productName)) {
                return 0;
            }
            return 9.75 / 100d;
        }
        if (location.equals("NY")) {
            if (Product.isFood(productName) || Product.isClothing(productName)) {
                return 0;
            }
            return 8.875 / 100;
        }
        return 0;
    }

    public static double roundTax(double tax) {
        return Math.round(tax * 20) / 20d;
    }

    public static void main(String[] args) {
        String input = "Location: CA, 1 book at 17.99, 1 potato chips at 3.99, 2 potato chips at 5.99, 1 dummy at 0.17, 10 shoe at 40";
        Table table = new Table(3);
        table.addRow(new String[]{"item", "price", "qty"});
        table.addRow(new String[]{"", "", ""});
        String[] commaParts = input.split(",");

        String location = commaParts[0]
                .replace("Location", "").trim()
                .replace(":", "").trim();
        double totalTax = 0;
        double subtotal = 0;

        for (int i = 1; i < commaParts.length; i++) {
            String part = commaParts[i].trim();
            int quantity = new Scanner(part).nextInt();
            String[] atParts = part.replaceFirst(quantity + "", "").trim()
                    .split(" at ");
            String productName = atParts[0].trim();
            double price = Double.parseDouble(atParts[1].trim());
            double itemSubTotal = roundTax(price * quantity);
            double tax = calcTaxRate(location, productName) * itemSubTotal;
            totalTax += roundTax(tax);
            subtotal += itemSubTotal;

            table.addRow(new String[]{productName, "$" + price, quantity + ""});
        }

        table.addRow(new String[]{"subtotal:", "", "$" + subtotal});
        table.addRow(new String[]{"tax:", "", "$" + totalTax});
        table.addRow(new String[]{"total:", "", "$" + (subtotal + totalTax)});

        table.print();
    }
}
