package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * Class that represents subscription period for periodicals
 *
 * @author Andrei Suprun
 * @see Periodical
 */
public class SubscriptionPeriod implements Serializable {

    private static final long serialVersionUID = -6000652267715967778L;

    private Integer id;
    private String name;
    private Integer monthsAmount;
    private BigDecimal rate;

    public static class Builder {
        private final SubscriptionPeriod subscriptionPeriod;

        public Builder() {
            subscriptionPeriod = new SubscriptionPeriod();
        }

        public Builder setId(Integer id) {
            subscriptionPeriod.setId(id);
            return this;
        }

        public Builder setName(String name) {
            subscriptionPeriod.setName(name);
            return this;
        }

        public Builder setMonthsAmount(Integer monthsAmount) {
            subscriptionPeriod.setMonthsAmount(monthsAmount);
            return this;
        }

        public Builder setRate(BigDecimal rate) {
            subscriptionPeriod.setRate(rate);
            return this;
        }

        public SubscriptionPeriod build() {
            return subscriptionPeriod;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public SubscriptionPeriod() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMonthsAmount() {
        return monthsAmount;
    }

    public void setMonthsAmount(Integer monthsAmount) {
        this.monthsAmount = monthsAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionPeriod that = (SubscriptionPeriod) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (monthsAmount != null ? !monthsAmount.equals(that.monthsAmount) : that.monthsAmount != null) return false;
        return rate != null ? rate.equals(that.rate) : that.rate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (monthsAmount != null ? monthsAmount.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SubscriptionPeriod.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("monthsAmount=" + monthsAmount)
                .add("rate=" + rate)
                .toString();
    }
}
