package com.bsu.classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Client {

    public enum Currency {
        USD(2.64), EUR(3.08), BYN(1), RUB(0.38);
        public double course;

        Currency(double course) {
            this.course = course;
        }
    }

    private final ArrayList<Currency> currencyList;
    private final Integer id;
    private final String portfolio;
    private final Date dateOfCreating;
    private final ArrayList<Double> sumList;

    public Client(Integer id, String portfolio, Date dateOfCreating, ArrayList<Currency> currencyList,
                  ArrayList<Double> sumList) {
        this.id = id;
        this.portfolio = portfolio;
        this.dateOfCreating = dateOfCreating;
        this.currencyList = currencyList;
        this.sumList = sumList;

    }

    public Integer getId() {
        return id;
    }

    public String getNameOfPortfolio() {
        return portfolio;
    }

    public Date getDateOfCreating() {
        return dateOfCreating;
    }

    public ArrayList<Currency> getCurrencyList() {
        return currencyList;
    }

    public ArrayList<Double> getSumList() {
        return sumList;
    }

    @Override
    public String toString() {
        return id + ", " + portfolio + ", " + dateOfCreating + ", " +
                currencyList + ", " + sumList + "\n";
    }

    public static Client createClient(String line) throws ParseException {
        String[] field = line.split(",");

        Integer id = Integer.parseInt(field[0]);

        String portfolio = field[1];

        String creatingDate = field[2];
        DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        Date dateOfCreating = date.parse(creatingDate);

        String[] currency = field[3].split(" ");
        ArrayList<Currency> currencyList = new ArrayList<>();
        for (String word : currency) {
            currencyList.add(Currency.valueOf(word));
        }

        String[] sum = field[4].split(" ");
        ArrayList<Double> sumList = new ArrayList<>();
        for (String word : sum) {
            sumList.add(Double.parseDouble(word));
        }

        return new Client(id, portfolio, dateOfCreating, currencyList,
                sumList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(currencyList, client.currencyList) &&
                Objects.equals(id, client.id) &&
                Objects.equals(portfolio, client.portfolio) &&
                Objects.equals(dateOfCreating, client.dateOfCreating) &&
                Objects.equals(sumList, client.sumList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyList, id, portfolio, dateOfCreating, sumList);
    }
}