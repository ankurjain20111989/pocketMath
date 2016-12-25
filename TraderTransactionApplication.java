package com.pocketmath.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur jain <br/>
 * Date: 25-Dec-16 <br/>
 * Time: 9:15 AM <br/>
 */
public class TraderTransactionApplication {
    public static void main(String args[]) {
        try {
            URL url = new URL("https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com/prod/question");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("x-api-key", "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: Http Error Code:" + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            System.out.println("The Question to be solved is: ");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            List<Trader> traders = getAllTraders();
            List<Transaction> transactions = getAllTransactions();
            tradersFromSingaporeSortedByName(traders);
            transactionWithTheMaxValue(transactions);
            transactionsInYear2016SortedByName(transactions);
            averageOfTransactionsFromTradersLivingInBeijing(traders, transactions);
            connection.disconnect();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tradersFromSingaporeSortedByName(List<Trader> traders) {
        List<Trader> tradersAfterFilteration = traders.stream().filter(p -> p.getCity().equals("Singapore")).collect(Collectors.toList());
        Collections.sort(tradersAfterFilteration, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        System.out.println("Traders From Singapore Sorted By name");
        for (Trader trader : tradersAfterFilteration) {
            System.out.println(trader.getCity() + " " + trader.getId() + " " + trader.getName());
        }
    }

    private static void transactionWithTheMaxValue(List<Transaction> transactions) {
        List<Double> transactionValues = new ArrayList<Double>();
        for (Transaction t : transactions) {
            transactionValues.add(Double.valueOf(t.getValue()));
        }
        DoubleSummaryStatistics stats = transactionValues.stream().mapToDouble((x) -> x).summaryStatistics();
        System.out.println("Transaction with the Highest Value is :" + stats.getMax());
        List<Transaction> transactionWithMaxValue = transactions.stream().filter(t -> Double.valueOf(t.getValue()) == stats.getMax()).collect(Collectors.toList());
        for (Transaction t : transactionWithMaxValue) {
            System.out.println(t.getTimestamp() + " " + t.getTraderId() + " " + t.getValue());
        }
    }

    private static void transactionsInYear2016SortedByName(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.valueOf(t.getTimestamp()) * 1000);
            int year = cal.get(Calendar.YEAR);
            t.setYear(year);
        }
        List<Transaction> transactionFilterOnYear = transactions.stream().filter(item -> item.getYear() == 2016).collect(Collectors.toList());
        Collections.sort(transactionFilterOnYear, (t1, t2) -> Double.valueOf(t2.getValue()).compareTo(Double.valueOf(t1.getValue())));
        System.out.println("Transaction in the year 2016 sorted by value are: ");
        for (Transaction t : transactionFilterOnYear) {
            System.out.println(t.getTimestamp() + " " + " " + t.getValue() + " " + t.getTraderId());
        }
    }

    private static void averageOfTransactionsFromTradersLivingInBeijing(List<Trader> traders, List<Transaction> transactions) {
        List<Trader> tradersInBeijing = traders.stream().filter(trader -> trader.getCity().equals("Beijing")).collect(Collectors.toList());
        double sum = 0d, count = 0;
        for (Transaction t : transactions) {
            for (Trader trader : tradersInBeijing) {
                if (trader.getId().equals(t.getTraderId())) {
                    sum = sum + Double.valueOf(t.getValue());
                    count++;
                }
            }
        }
        System.out.println("Average Of All Transactions whose traders are in beijing are:" + sum / count);
    }

    private static List<Trader> getAllTraders() {
        List<Trader> traders = new ArrayList<Trader>();
        try {
            URL url = new URL("https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com/prod/traders");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            StringBuilder str = new StringBuilder();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("x-api-key", "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: Http Error Code:" + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                str.append(output);
            }
            ObjectMapper mapper = new ObjectMapper();
            traders = mapper.readValue(str.toString(), new TypeReference<List<Trader>>() {
            });
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return traders;
    }

    private static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            URL url = new URL("https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com/prod/transactions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("x-api-key", "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: Http Error Code:" + connection.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            ObjectMapper mapper = new ObjectMapper();
            transactions = mapper.readValue(response.toString(), new TypeReference<List<Transaction>>() {
            });
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
