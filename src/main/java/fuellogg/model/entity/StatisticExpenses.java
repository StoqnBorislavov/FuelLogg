package fuellogg.model.entity;

import fuellogg.model.enums.StatisticTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "statistics_expenses")
public class StatisticExpenses extends BaseEntity {
    private Integer odometer;
    private LocalDate date;
    private BigDecimal price;
    private String description;
    private StatisticTypeEnum type;
    private Vehicle vehicle;

    public StatisticExpenses() {
    }

    @Column(nullable = false)
    public Integer getOdometer() {
        return odometer;
    }

    public StatisticExpenses setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public StatisticExpenses setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public StatisticExpenses setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
//    @Lob
    public String getDescription() {
        return description;
    }

    public StatisticExpenses setDescription(String description) {
        this.description = description;
        return this;
    }
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public StatisticTypeEnum getType() {
        return type;
    }

    public StatisticExpenses setType(StatisticTypeEnum type) {
        this.type = type;
        return this;
    }
    @ManyToOne
    public Vehicle getVehicle() {
        return vehicle;
    }

    public StatisticExpenses setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
