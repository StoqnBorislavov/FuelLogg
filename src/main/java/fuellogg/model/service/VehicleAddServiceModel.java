package fuellogg.model.service;

import fuellogg.model.enums.EngineEnum;
import fuellogg.model.enums.TransmissionEnum;
import org.springframework.web.multipart.MultipartFile;

public class VehicleAddServiceModel {
    private Long modelId;
    private MultipartFile picture;
    private String name;
    private Integer horsePower;
    private Integer year;
    private EngineEnum engine;
    private TransmissionEnum transmission;
    private Integer odometer;

    public VehicleAddServiceModel() {
    }

    public Long getModelId() {
        return modelId;
    }

    public VehicleAddServiceModel setModelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public VehicleAddServiceModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    public String getName() {
        return name;
    }

    public VehicleAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public VehicleAddServiceModel setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public VehicleAddServiceModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public VehicleAddServiceModel setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public VehicleAddServiceModel setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public VehicleAddServiceModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
}
