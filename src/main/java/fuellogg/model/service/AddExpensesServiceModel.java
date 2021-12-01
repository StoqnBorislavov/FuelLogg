package fuellogg.model.service;

import fuellogg.model.enums.StatisticTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddExpensesServiceModel {
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

    public AddExpensesServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public AddExpensesServiceModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public AddExpensesServiceModel setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public AddExpensesServiceModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddExpensesServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddExpensesServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public StatisticTypeEnum getType() {
        return type;
    }

    public AddExpensesServiceModel setType(StatisticTypeEnum type) {
        this.type = type;
        return this;
    }
}
