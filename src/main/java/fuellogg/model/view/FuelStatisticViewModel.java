package fuellogg.model.view;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuelStatisticViewModel {

    private LocalDate date;
    private Integer trip;
    private BigDecimal fuelConsumption;
    private BigDecimal quantity;

    public FuelStatisticViewModel() {
    }

    public LocalDate getDate() {
        return date;
    }

    public FuelStatisticViewModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getTrip() {
        return trip;
    }

    public FuelStatisticViewModel setTrip(Integer trip) {
        this.trip = trip;
        return this;
    }

    public BigDecimal getFuelConsumption() {
        return fuelConsumption;
    }

    public FuelStatisticViewModel setFuelConsumption(BigDecimal fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public FuelStatisticViewModel setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
}
