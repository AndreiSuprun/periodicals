package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * Class that represents user subscription of some plan for periodicals
 *
 * @author Andrei Suprun
 * @see User
 * @see SubscriptionPeriod
 * @see Periodical
 */
public class Subscription implements Serializable {

    private static final long serialVersionUID = 8540619478891337776L;

    private Long id;
    private User user;
    private Payment payment;
    private Periodical periodical;
    private SubscriptionPeriod subscriptionPeriod;
    private LocalDate startDate;
    private LocalDate endDate;

    public static class Builder {
        private final Subscription subscription;

        public Builder() {
            subscription = new Subscription();
        }

        public Builder setId(Long id) {
            subscription.setId(id);
            return this;
        }

        public Builder setUser(User user) {
            subscription.setUser(user);
            return this;
        }

        public Builder setPayment(Payment payment) {
            subscription.payment = payment;
            return this;
        }

        public Builder setPeriodical(Periodical periodical) {
            subscription.setPeriodical(periodical);
            return this;
        }

        public Builder setSubscriptionPeriod(SubscriptionPeriod subscriptionPeriod) {
            subscription.setSubscriptionPeriod(subscriptionPeriod);
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            subscription.setStartDate(startDate);
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            subscription.setEndDate(endDate);
            return this;
        }

        public Subscription build() {
            return subscription;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public SubscriptionPeriod getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public void setSubscriptionPeriod(SubscriptionPeriod subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (periodical != null ? !periodical.equals(that.periodical) : that.periodical != null) return false;
        if (subscriptionPeriod != null ? !subscriptionPeriod.equals(that.subscriptionPeriod) : that.subscriptionPeriod != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return endDate != null ? endDate.equals(that.endDate) : that.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (periodical != null ? periodical.hashCode() : 0);
        result = 31 * result + (subscriptionPeriod != null ? subscriptionPeriod.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Subscription.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("payment=" + payment)
                .add("periodical=" + periodical)
                .add("subscriptionPlan=" + subscriptionPeriod)
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
