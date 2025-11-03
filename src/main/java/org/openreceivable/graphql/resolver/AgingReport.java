package org.openreceivable.graphql.resolver;

import java.math.BigDecimal;

/**
 * Aging report response type
 */
public class AgingReport {
    private final String customerId;
    private final BigDecimal current;
    private final BigDecimal days1To30;
    private final BigDecimal days31To60;
    private final BigDecimal days61To90;
    private final BigDecimal days91To120;
    private final BigDecimal over120;
    private final BigDecimal totalOutstanding;

    public AgingReport(String customerId, BigDecimal current, BigDecimal days1To30,
                      BigDecimal days31To60, BigDecimal days61To90, BigDecimal days91To120,
                      BigDecimal over120, BigDecimal totalOutstanding) {
        this.customerId = customerId;
        this.current = current;
        this.days1To30 = days1To30;
        this.days31To60 = days31To60;
        this.days61To90 = days61To90;
        this.days91To120 = days91To120;
        this.over120 = over120;
        this.totalOutstanding = totalOutstanding;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public BigDecimal getDays1To30() {
        return days1To30;
    }

    public BigDecimal getDays31To60() {
        return days31To60;
    }

    public BigDecimal getDays61To90() {
        return days61To90;
    }

    public BigDecimal getDays91To120() {
        return days91To120;
    }

    public BigDecimal getOver120() {
        return over120;
    }

    public BigDecimal getTotalOutstanding() {
        return totalOutstanding;
    }
}
