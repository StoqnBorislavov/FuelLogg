package fuellogg.service.impl;

import fuellogg.model.entity.*;
import fuellogg.model.enums.*;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.DetailsViewOnExpenses;
import fuellogg.model.view.DetailsViewOnFueling;
import fuellogg.repository.StatisticsExpensesRepository;
import fuellogg.repository.StatisticsFuelingRepository;
import fuellogg.repository.VehicleRepository;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatisticsExpensesServiceImplTest {

    private Vehicle testVehicle;
    private User testUser;
    private UserRole adminRole;
    private UserRole userRole;
    private AddFuelServiceModel fuelServiceModel;
    private StatisticExpenses statisticExpenses;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private VehicleService mockVehicleService;
    @Mock
    private StatisticsExpensesRepository mockStatisticsExpensesRepository;

    private StatisticsExpensesServiceImpl serviceToTest;

    @BeforeEach
    void init() {


        //ARRANGE
        serviceToTest = new StatisticsExpensesServiceImpl(mockStatisticsExpensesRepository, mockModelMapper, mockVehicleService);


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
                .setOdometer(285200)
                .setTripOdometer(200)
                .setDescription("Good ride")
                .setPrice(new BigDecimal(22))
                .setQuantity(new BigDecimal(10))
                .setRoute(RouteEnum.COUNTRY_ROADS)
                .setVehicleId(testVehicle.getId())
                .setDate(LocalDate.now());
        statisticExpenses= new StatisticExpenses();
        statisticExpenses.setCreated(Instant.now());
        statisticExpenses
                .setVehicle(testVehicle)
                .setOdometer(285000)
                .setDescription("Best thing")
                .setDate(LocalDate.now())
                .setType(StatisticTypeEnum.ACCESSORIES);

    }

    @Test
    void testGetStatisticExpensesShouldReturnStatistic(){
        // Arrange
        Mockito.when(mockStatisticsExpensesRepository.findById(testVehicle.getId())).
                thenReturn(Optional.of(statisticExpenses));
        Mockito.when(mockModelMapper.map(statisticExpenses, DetailsViewOnExpenses.class))
                .thenReturn(new DetailsViewOnExpenses()
                        .setDate(statisticExpenses.getDate()).setOdometer(statisticExpenses.getOdometer())
                        .setPrice(statisticExpenses.getPrice()).setType(statisticExpenses.getType().name())
                        .setDescription(statisticExpenses.getDescription()));


        // Act
        DetailsViewOnExpenses currentStatisticView = serviceToTest.getCurrentStatisticView(testVehicle.getId());

        // Assert

        Assertions.assertEquals(currentStatisticView.getOdometer(), statisticExpenses.getOdometer());
    }
}