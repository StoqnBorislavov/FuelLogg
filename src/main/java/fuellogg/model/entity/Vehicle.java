package fuellogg.model.entity;
import fuellogg.model.enums.EngineEnum;
import fuellogg.model.enums.TransmissionEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {

        private Picture picture;
        private String name;
        private Integer horsePower;
        private Integer year;
        private EngineEnum engine;
        private TransmissionEnum transmission;
        private Integer odometer;
        private Brand brand;
        private List<Statistic> statistics;
        private User owner;

        public Vehicle() {
        }

        @OneToOne
        public Picture getPicture() {
                return picture;
        }

        public Vehicle setPicture(Picture picture) {
                this.picture = picture;
                return this;
        }
        @Column(nullable = false)
        public String getName() {
                return name;
        }

        public Vehicle setName(String name) {
                this.name = name;
                return this;
        }
        @Column
        public Integer getHorsePower() {
                return horsePower;
        }

        public Vehicle setHorsePower(Integer horsePower) {
                this.horsePower = horsePower;
                return this;
        }
        @Column
        public Integer getYear() {
                return year;
        }

        public Vehicle setYear(Integer year) {
                this.year = year;
                return this;
        }
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        public EngineEnum getEngine() {
                return engine;
        }

        public Vehicle setEngine(EngineEnum engine) {
                this.engine = engine;
                return this;
        }
        @Column
        @Enumerated(EnumType.STRING)
        public TransmissionEnum getTransmission() {
                return transmission;
        }

        public Vehicle setTransmission(TransmissionEnum transmission) {
                this.transmission = transmission;
                return this;
        }
        @Column(nullable = false)
        public Integer getOdometer() {
                return odometer;
        }

        public Vehicle setOdometer(Integer odometer) {
                this.odometer = odometer;
                return this;
        }
        @ManyToOne
        public Brand getBrand() {
                return brand;
        }

        public Vehicle setBrand(Brand brand) {
                this.brand = brand;
                return this;
        }
        @OneToMany(mappedBy = "vehicle")
        public List<Statistic> getStatistics() {
                return statistics;
        }

        public Vehicle setStatistics(List<Statistic> statistics) {
                this.statistics = statistics;
                return this;
        }

        @ManyToOne
        public User getOwner() {
                return owner;
        }

        public Vehicle setOwner(User owner) {
                this.owner = owner;
                return this;
        }
}
