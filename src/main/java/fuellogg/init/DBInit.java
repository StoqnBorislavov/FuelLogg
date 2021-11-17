package fuellogg.init;

import fuellogg.service.BrandService;
import fuellogg.service.ModelService;
import fuellogg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DBInit implements CommandLineRunner {

    private final ModelService modelService;
    private final BrandService brandService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBInit(ModelService modelService, BrandService brandService, UserService userService, PasswordEncoder passwordEncoder) {
        this.modelService = modelService;
        this.brandService = brandService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
     this.userService.initializeUsersAndRoles();
     this.brandService.initializeBrands();
     this.modelService.initializeModels();
    }
}
