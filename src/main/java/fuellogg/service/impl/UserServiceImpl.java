package fuellogg.service.impl;

import fuellogg.model.entity.User;
import fuellogg.model.entity.UserRole;
import fuellogg.model.enums.UserRoleEnum;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.service.UserRegisterServiceModel;
import fuellogg.repository.UserRepository;
import fuellogg.repository.UserRoleRepository;
import fuellogg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final FuelLoggUserServiceImpl fuelLoggUserService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleRepository userRoleRepository, FuelLoggUserServiceImpl fuelLoggUserService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.fuelLoggUserService = fuelLoggUserService;
    }

    @Override
    public void initializeUsersAndRoles() {
        this.initializeUsersRoles();
        this.initializeUsers();
    }

    public void initializeUsers() {
        if (this.userRepository.count() == 0) {
            UserRole adminRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMIN);
            UserRole userRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);

            User admin = new User();
            admin.
                    setUsername("stoyan").
                    setFullName("Stoyan Stoyanov").
                    setEmail("s.stoyanov@stoyanov.com").
                    setPassword(passwordEncoder.encode("1234")).
                    setRoles(Set.of(adminRole, userRole));

            this.userRepository.save(admin);

            User user = new User();
            user
                    .setUsername("pesho").
                    setFullName("Pesho Petrov").
                    setEmail("p.petrov@petrov.com").
                    setPassword(passwordEncoder.encode("1234")).
                    setRoles(Set.of(userRole));

            this.userRepository.save(user);
        }
    }

    public void initializeUsersRoles() {
        if (this.userRoleRepository.count() == 0) {
            UserRole admin = new UserRole();
            admin.setRole(UserRoleEnum.ADMIN);

            UserRole user = new UserRole();
            user.setRole(UserRoleEnum.USER);

            this.userRoleRepository.saveAll(List.of(admin, user));
        }
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found!"));
    }

    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {

        UserRole userRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);

        User newUser = new User();
        newUser
                .setUsername(userRegisterServiceModel.getUsername())
                .setFullName(userRegisterServiceModel.getFullName())
                .setEmail(userRegisterServiceModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()))
                .setRoles(Set.of(userRole));
        this.userRepository.save(newUser);
        UserDetails principal = fuelLoggUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, newUser.getPassword(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isUsernameFree(String username) {
        return this.userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public void changePassword(String newPassword, String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with name %s not found!", username)));
        user.setPassword(passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }
}
