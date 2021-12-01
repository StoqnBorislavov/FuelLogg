package fuellogg.model.binding;

import fuellogg.model.enums.StatisticTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddExpensesBindingModel {
    private Long id;
    private Integer odometer;
    private Long vehicleId;
    private LocalDate date;
    private BigDecimal price;
    private String description;
    private StatisticTypeEnum type;

    public Long getId() {
        return id;
    }

    public AddExpensesBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
    @NotNull
    public Long getVehicleId() {
        return vehicleId;
    }

    public AddExpensesBindingModel setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }
    @NotNull
    public Integer getOdometer() {
        return odometer;
    }

    public AddExpensesBindingModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public AddExpensesBindingModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public AddExpensesBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddExpensesBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
    @NotNull
    public StatisticTypeEnum getType() {
        return type;
    }

    public AddExpensesBindingModel setType(StatisticTypeEnum type) {
        this.type = type;
        return this;
    }
}
