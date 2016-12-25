package com.pocketmath.test;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur jain <br/>
 * Date: 25-Dec-16 <br/>
 * Time: 11:10 AM <br/>
 */
public class Transaction {
    String timestamp;
    String traderId;
    String value;
    int year;


    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraderId() {
        return this.traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
