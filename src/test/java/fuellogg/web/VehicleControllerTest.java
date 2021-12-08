package fuellogg.web;

import fuellogg.model.entity.User;
import fuellogg.model.entity.UserRole;
import fuellogg.model.entity.Vehicle;
import fuellogg.model.enums.EngineEnum;
import fuellogg.model.enums.TransmissionEnum;
import fuellogg.model.enums.UserRoleEnum;
import fuellogg.repository.*;
import fuellogg.service.CloudinaryService;
import fuellogg.service.impl.MyUser;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser("gosho")
@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @MockBean
    private MyUser myUser;

    private User testUser;



    @BeforeEach
    void setUp() {
        UserRole role = this.userRoleRepository.findByRole(UserRoleEnum.USER);
        testUser = new User();
        testUser.setPassword("password");
        testUser.setUsername("gosho");
        testUser.setEmail("gosho@gosho.com");
        testUser.setFullName("gosho gosho");
        testUser.setRoles(Set.of(role));
        testUser.setCreated(Instant.now());

        List<GrantedAuthority> authorities =
                testUser.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" +r.getRole().name()))
                        .collect(Collectors.toList());

        testUser = userRepository.save(testUser);
        myUser = new MyUser(testUser.getUsername(), testUser.getPassword(), authorities);
    }

    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
        brandRepository.deleteAll();
        modelRepository.deleteAll();
        pictureRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testRenderAddVehicleForm() throws Exception {
        mockMvc.perform(get("/vehicle/add").with(user(myUser)))
                .andExpect(status().isOk())
                .andExpect(view().name("add-vehicle"));
    }

    @Test
    void testWithoutErrorsShouldPass() throws Exception {
        String imageUrl= "";
        try {
            Path pathToPicture = Paths.get("src/main/resources/static/images/about-background.jpg");
            byte[] imageBytes = Files.readAllBytes(pathToPicture);
            MockMultipartFile file = new MockMultipartFile("about-background", "about-background", MediaType.IMAGE_JPEG_VALUE, imageBytes);

            mockMvc.perform(multipart("/vehicle/add").file("picture", file.getBytes())
                            .param("brandName", "BMW")
                            .param("modelName", "3 series")
                            .param("name", "330")
                            .param("horsePower", "10")
                            .param("year", "1999")
                            .param("engine", EngineEnum.GASOLINE.name())
                            .param("transmission", TransmissionEnum.MANUAL.name())
                            .param("odometer", "287000")
                            .with(user(myUser))
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection());
            Vehicle vehicle = vehicleRepository.findAll().get(0);
            imageUrl = vehicle.getPicture().getPublicId();

            Assert.assertEquals("BMW", vehicle.getBrand().getName());
            Assert.assertEquals("1999", vehicle.getYear().toString());
        } finally {
            cloudinaryService.delete(imageUrl);
        }

    }

    @Test
    void testWithErrorsShouldReturnBack() throws Exception {
        String imageUrl= "";
        try {
            Path pathToPicture = Paths.get("src/main/resources/static/images/about-background.jpg");
            byte[] imageBytes = Files.readAllBytes(pathToPicture);
            MockMultipartFile file = new MockMultipartFile("about-background", "about-background", MediaType.IMAGE_JPEG_VALUE, imageBytes);

            mockMvc.perform(multipart("/vehicle/add").file("picture", file.getBytes())
                            .param("brandName", "BMW")
                            .param("modelName", "3 series")
                            .param("name", "330")
                            .param("horsePower", "-10")
                            .param("year", "1100")
                            .param("engine", EngineEnum.GASOLINE.name())
                            .param("transmission", TransmissionEnum.MANUAL.name())
                            .param("odometer", "287000")
                            .with(user(myUser))
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection());

            Assert.assertEquals(imageUrl, "");
//            Assert.assertEquals("1999", vehicle.getYear().toString());
        } finally {
            if(!imageUrl.equals("")) {
                cloudinaryService.delete(imageUrl);
            }
        }

    }

}