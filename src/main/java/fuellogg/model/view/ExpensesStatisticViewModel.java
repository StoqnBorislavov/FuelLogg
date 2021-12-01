package fuellogg.model.view;


import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpensesStatisticViewModel {

    private LocalDate date;
    private Integer odometer;
    private String type;
    private BigDecimal price;

    public LocalDate getDate() {
        return date;
    }

    public ExpensesStatisticViewModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public ExpensesStatisticViewModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public String getType() {
        return type;
    }

    public ExpensesStatisticViewModel setType(String type) {
        this.type = type;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ExpensesStatisticViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
