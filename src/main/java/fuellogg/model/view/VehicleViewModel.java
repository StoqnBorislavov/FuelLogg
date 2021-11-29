package fuellogg.model.view;

import java.math.BigDecimal;

public class VehicleViewModel {

    private Long id;
    private String brand;
    private String name;
    private Integer odometer;
    private Double fuelConsumption;
    private BigDecimal averageConsumption;
    private String url;

    public VehicleViewModel() {
    }

    public Long getId() {
        return id;
    }

    public VehicleViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public VehicleViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public VehicleViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public VehicleViewModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public VehicleViewModel setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public BigDecimal getAverageConsumption() {
        return averageConsumption;
    }

    public VehicleViewModel setAverageConsumption(BigDecimal averageConsumption) {
        this.averageConsumption = averageConsumption;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public VehicleViewModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
