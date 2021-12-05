package fuellogg.web;


import fuellogg.model.entity.User;
import fuellogg.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDownRecentUsers() {
        userRepository.deleteAll();
    }

    @Test
    void testRenderRegisterForm() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    private static final String TEST_USER_EMAIL = "gosho@gosho.com";
    private static final String TEST_USER_USERNAME = "gosho";
    private static final String TEST_USER_PASSWORD = "test12";
    private static final String TEST_USER_FULL_NAME = "Georgi Georgiev";


    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/users/register").
                param("username", TEST_USER_USERNAME).
                param("fullName", TEST_USER_FULL_NAME).
                param("email", TEST_USER_EMAIL).
                param("password", TEST_USER_PASSWORD).
                param("confirmPassword", TEST_USER_PASSWORD).
                with(csrf()).
                contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<User> justCreatedUser = userRepository.findByUsername(TEST_USER_USERNAME);

        Assertions.assertTrue(justCreatedUser.isPresent());

        User newUser = justCreatedUser.get();

        Assertions.assertEquals(TEST_USER_USERNAME, newUser.getUsername());
        Assertions.assertEquals(TEST_USER_FULL_NAME, newUser.getFullName());
        Assertions.assertEquals(TEST_USER_EMAIL, newUser.getEmail());
    }

    @Test
    public void testRegisterShouldRedirectToRegisterIfPasswordsNotMatch() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("username", TEST_USER_USERNAME).
                        param("fullName", TEST_USER_FULL_NAME).
                        param("email", TEST_USER_EMAIL).
                        param("password", TEST_USER_PASSWORD).
                        param("confirmPassword", "test123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(flash().attribute("passwordsNotMatch", true));
    }
}