package fuellogg.service.impl;

import fuellogg.model.entity.User;
import fuellogg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelLoggUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public FuelLoggUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
        return mapToUserDetails(user);
    }
    private static UserDetails mapToUserDetails(User user){

        List<GrantedAuthority> authorities =
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" +r.getRole().name()))
                        .collect(Collectors.toList());

        return new MyUser(user.getUsername(), user.getPassword(), authorities);
    }
}
