package fuellogg.model.binding;

import fuellogg.model.entity.Brand;
import fuellogg.model.entity.Picture;
import fuellogg.model.enums.EngineEnum;
import fuellogg.model.enums.TransmissionEnum;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class VehicleAddBindingModel {

    private Long modelId;
    private MultipartFile picture;
    private String name;
    private Integer horsePower;
    private Integer year;
    private EngineEnum engine;
    private TransmissionEnum transmission;
    private Integer odometer;
//    private Brand brand;

    public VehicleAddBindingModel() {
    }

    @NotNull
    public Long getModelId() {
        return modelId;
    }

    public VehicleAddBindingModel setModelId(Long modelId) {
        this.modelId = modelId;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public VehicleAddBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public VehicleAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }
    @NotNull
    @Positive
    public Integer getHorsePower() {
        return horsePower;
    }

    public VehicleAddBindingModel setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }
    @NotNull
    @Min(1950)
    public Integer getYear() {
        return year;
    }

    public VehicleAddBindingModel setYear(Integer year) {
        this.year = year;
        return this;
    }
    @NotNull
    public EngineEnum getEngine() {
        return engine;
    }

    public VehicleAddBindingModel setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }
    @NotNull
    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public VehicleAddBindingModel setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }
    @NotNull
    @PositiveOrZero
    public Integer getOdometer() {
        return odometer;
    }

    public VehicleAddBindingModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
//    @NotNull
//    public Brand getBrand() {
//        return brand;
//    }
//
//    public VehicleAddBindingModel setBrand(Brand brand) {
//        this.brand = brand;
//        return this;
//    }
}
