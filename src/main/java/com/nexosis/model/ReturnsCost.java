package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.http.HttpHeaders;
import java.math.BigDecimal;
import java.util.Currency;

import static com.nexosis.util.NexosisHeaders.NEXOSIS_ACCOUNT_BALANCE;
import static com.nexosis.util.NexosisHeaders.NEXOSIS_REQUEST_COST;

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

    public void AssignCost(HttpHeaders headers)
    {
        if (headers != null) {
            if (headers.containsKey(NEXOSIS_REQUEST_COST)) {
                cost = ParseValue(headers.getFirstHeaderStringValue(NEXOSIS_REQUEST_COST));
            }
            if (headers.containsKey(NEXOSIS_ACCOUNT_BALANCE)) {
                balance = ParseValue(headers.getFirstHeaderStringValue(NEXOSIS_ACCOUNT_BALANCE));
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
