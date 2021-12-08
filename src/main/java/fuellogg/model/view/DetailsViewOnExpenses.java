package fuellogg.model.view;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetailsViewOnExpenses {
    private LocalDate date;
    private Integer odometer;
    private BigDecimal price;
    private String description;
    private String type;

    public LocalDate getDate() {
        return date;
    }

    public DetailsViewOnExpenses setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public DetailsViewOnExpenses setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DetailsViewOnExpenses setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DetailsViewOnExpenses setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return type;
    }

    public DetailsViewOnExpenses setType(String type) {
        this.type = type;
        return this;
    }
}
