package fuellogg.web;


import fuellogg.model.entity.User;
import fuellogg.model.entity.UserRole;
import fuellogg.model.enums.UserRoleEnum;
import fuellogg.repository.UserRepository;
import fuellogg.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "petar")
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void initUser(){
        userRepository.deleteAll();
        UserRole roleAdmin = new UserRole().setRole(UserRoleEnum.ADMIN);
        UserRole roleUser = new UserRole().setRole(UserRoleEnum.USER);
        userRoleRepository.saveAll(List.of(roleAdmin, roleUser));

        User user= new User()
                .setEmail("dimitar@petarov.com")
                .setFullName("Dimitar Petrov")
                .setPassword("123456")
                .setUsername("dimitar")
                .setRoles(Set.of(roleAdmin));
    }

    @AfterEach
    void tearDownRecentUsers(){
        userRepository.deleteAll();
    }

    @Test
    public void getHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

}