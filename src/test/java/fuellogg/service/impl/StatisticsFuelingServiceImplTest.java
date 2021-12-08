package fuellogg.service.impl;

import fuellogg.model.entity.*;
import fuellogg.model.enums.EngineEnum;
import fuellogg.model.enums.RouteEnum;
import fuellogg.model.enums.TransmissionEnum;
import fuellogg.model.enums.UserRoleEnum;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.DetailsViewOnFueling;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.repository.StatisticsFuelingRepository;
import fuellogg.repository.VehicleRepository;
import fuellogg.service.VehicleService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class StatisticsFuelingServiceImplTest {

    private Vehicle testVehicle;
    private User testUser;
    private UserRole adminRole;
    private UserRole userRole;
    private AddFuelServiceModel fuelServiceModel;
    private StatisticFueling statisticFueling;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private VehicleService mockVehicleService;
    @Mock
    private StatisticsFuelingRepository mockStatisticsFuelingRepository;

    private StatisticsFuelingServiceImpl serviceToTest;

    @BeforeEach
    void init() {


        //ARRANGE
        serviceToTest = new StatisticsFuelingServiceImpl(mockStatisticsFuelingRepository, mockModelMapper, mockVehicleService, mockVehicleRepository);


        adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);

        testUser = new User();
        testUser.setUsername("georgi")
                .setEmail("georgi@georgi.com")
                .setFullName("Georgi Georgiev")
                .setPassword("password")
                .setRoles(Set.of(adminRole, userRole))
                .setCreated(Instant.now());

        testVehicle = new Vehicle();

        testVehicle
                .setBrand(new Brand().setName("VW"))
                .setOdometer(285000)
                .setName("GOLF")
                .setPicture(new Picture())
                .setOwner(testUser)
                .setTransmission(TransmissionEnum.MANUAL)
                .setEngine(EngineEnum.GASOLINE)
                .setHorsePower(230)
                .setYear(2010)
                .setCreated(Instant.now());
        testVehicle.setId(1L);

        fuelServiceModel = new AddFuelServiceModel()
                .setFuelSort("GASOLINE")
                .setOdometer(285200)
                .setTripOdometer(200)
                .setDescription("Good ride")
                .setPrice(new BigDecimal(22))
                .setQuantity(new BigDecimal(10))
                .setRoute(RouteEnum.COUNTRY_ROADS)
                .setVehicleId(testVehicle.getId())
                .setDate(LocalDate.now());
        statisticFueling = new StatisticFueling();
        statisticFueling.setCreated(Instant.now());
        statisticFueling
                .setVehicle(testVehicle)
                .setOdometer(285000)
                .setDescription("Best thing")
                .setFuelConsumption(new BigDecimal(12.58))
                .setDate(LocalDate.now())
                .setTrip(200)
                .setQuantity(new BigDecimal(25.20))
                .setPrice(new BigDecimal(52.32))
                .setRoute(RouteEnum.HIGHWAY);

    }




    @Test
    void addFuelShouldAddEntityInStatisticRepository() throws ObjectNotFoundException {
        // Arrange
        Mockito.when(mockStatisticsFuelingRepository.findById(testVehicle.getId())).
                thenReturn(Optional.of(statisticFueling));
        Mockito.when(mockModelMapper.map(statisticFueling, DetailsViewOnFueling.class))
                .thenReturn(new DetailsViewOnFueling().setFuelSort(statisticFueling.getVehicle().getEngine().name())
                        .setDate(statisticFueling.getDate()).setOdometer(statisticFueling.getOdometer())
                        .setFuelConsumption(statisticFueling.getFuelConsumption()).setPrice(statisticFueling.getPrice())
                        .setDescription(statisticFueling.getDescription()).setQuantity(statisticFueling.getQuantity())
                        .setRoute(statisticFueling.getRoute().name()).setTrip(statisticFueling.getTrip())
                        .setVehicle(statisticFueling.getVehicle().getName()));


        // Act
        DetailsViewOnFueling currentStatisticView = serviceToTest.getCurrentStatisticView(testVehicle.getId());

        // Assert

        Assertions.assertEquals(currentStatisticView.getOdometer(), statisticFueling.getOdometer());

    }

}