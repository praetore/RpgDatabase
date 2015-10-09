package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darryl on 9-10-15.
 */
public class SubscriptionDAO {
    private List<Subscription> subscriptions;

    public SubscriptionDAO() {
        this.subscriptions = new ArrayList<Subscription>();
        this.subscriptions.add(new Subscription("One Month", 5, 1));
        this.subscriptions.add(new Subscription("Two Months", 8, 2));
        this.subscriptions.add(new Subscription("Three Months", 10, 3));
        this.subscriptions.add(new Subscription("One Year", 35, 12));
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
