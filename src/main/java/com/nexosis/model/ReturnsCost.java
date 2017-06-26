package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;

import java.math.BigDecimal;
import java.util.Currency;

/**
 *
 */
public abstract class ReturnsCost {
    @JsonIgnore
    private CurrencyAmount cost;
    @JsonIgnore
    private CurrencyAmount balance;

    public CurrencyAmount getCost() {
        return cost;
    }

    public void AssignCost(Header[] headersArray)
    {
        if (headersArray != null) {
            for (Header h : headersArray) {
                if (h.getName().equalsIgnoreCase("nexosis-request-cost")) {
                    cost = ParseValue(h.getValue());
                }
                if (h.getName().equalsIgnoreCase("nexosis-account-balance")) {
                    balance = ParseValue(h.getValue());
                }
            }
        }
    }

    public void setCost(CurrencyAmount cost) {
        this.cost = cost;
    }

    public CurrencyAmount getBalance() {
        return balance;
    }
    public void setbalance(CurrencyAmount balance) {
        this.balance = balance;
    }


    private CurrencyAmount ParseValue(String value)
    {
        final String[] parts = value.split( "\\ ");

        if (parts.length > 1)
        {
            return new CurrencyAmount() {{
                setAmount(new BigDecimal(parts[0]));
                setCurrency(Currency.getInstance(parts[1]));
            }};
        }
        else if (parts.length == 1)
        {
            return new CurrencyAmount() {{
                setAmount(new BigDecimal(parts[0]));
            }};
        }
        return null;
    }


}
