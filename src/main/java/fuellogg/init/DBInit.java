package fuellogg.init;

import fuellogg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DBInit implements CommandLineRunner {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBInit(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
     this.userService.initializeUsersAndRoles();
    }
}
