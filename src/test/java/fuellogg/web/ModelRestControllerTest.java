package fuellogg.web;

import fuellogg.model.entity.Brand;
import fuellogg.model.entity.Model;
import fuellogg.model.entity.User;
import fuellogg.repository.BrandRepository;
import fuellogg.repository.ModelRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fuellogg.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

@WithMockUser("gosho")
@SpringBootTest
@AutoConfigureMockMvc
class ModelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setPassword("password");
        testUser.setUsername("gosho");
        testUser.setEmail("gosho@gosho.com");
        testUser.setFullName("gosho gosho");
        testUser.setCreated(Instant.now());

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown(){
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetModels() throws Exception {
        String brandName = initBrand();

        mockMvc.perform(get("/api/model/" + brandName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("Ceed")))
                .andExpect(jsonPath("$.[1].name", is("Picanto")));;
    }

    private String initBrand(){
        Brand testBrand = new Brand();
        testBrand.setName("KIA");
        testBrand = brandRepository.save(testBrand);

        Model ceed = new Model();
        ceed.setCreated(Instant.now());
        ceed.setName("Ceed");
        ceed.setBrand(testBrand);

        Model picanto = new Model();
        picanto.setCreated(Instant.now());
        picanto.setName("Picanto");
        picanto.setBrand(testBrand);

        testBrand.setModels(List.of(ceed, picanto));

        return brandRepository.save(testBrand).getName();
    }
}