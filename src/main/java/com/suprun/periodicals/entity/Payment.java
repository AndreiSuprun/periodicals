package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Class that represents payment made by some user purchasing some periodicals
 *
 * @author Andrei Suprun
 * @see User
 * @see Periodical
 */

public class Payment implements Serializable {

    private static final long serialVersionUID = -22759703204341490L;

    private Long id;
    private User user;
    private BigDecimal totalPrice;
    private boolean paid;
    private LocalDateTime paymentDate;

    public static class Builder {
        private final Payment payment;

        public Builder() {
            this.payment = new Payment();
        }

        public Builder setId(Long id) {
            payment.setId(id);
            return this;
        }

        public Builder setUser(User user) {
            payment.setUser(user);
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            payment.setTotalPrice(totalPrice);
            return this;
        }

        public Builder setPaid(boolean isPaid) {
            payment.setPaid(isPaid);
            return this;
        }

        public Builder setPaymentDate(LocalDateTime paymentDate) {
            payment.setPaymentDate(paymentDate);
            return this;
        }

        public Payment build() {
            return payment;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Payment() {
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaid(boolean isPaid){
        this.paid = isPaid;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paid != payment.paid) return false;
        if (id != null ? !id.equals(payment.id) : payment.id != null) return false;
        if (user != null ? !user.equals(payment.user) : payment.user != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(payment.totalPrice) : payment.totalPrice != null) return false;
        return paymentDate != null ? paymentDate.equals(payment.paymentDate) : payment.paymentDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Payment.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("totalPrice=" + totalPrice)
                .add("isPaid=" + paid)
                .add("paymentDate=" + paymentDate)
                .toString();
    }
}
