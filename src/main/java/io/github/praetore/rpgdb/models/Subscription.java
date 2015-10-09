package io.github.praetore.rpgdb.models;

/**
 * Created by darryl on 9-10-15.
 */
public class Subscription {
    private String name;
    private int cost;
    private int months;

    public Subscription(String name, int cost, int months) {
        this.name = name;
        this.cost = cost;
        this.months = months;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMonths() {
        return months;
    }
}
