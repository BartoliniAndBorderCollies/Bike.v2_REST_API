package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class provides a custom implementation of the UserDetailsService interface.
 * It is used for loading user-specific data during security authentication.
 * It overrides the loadUserByUsername method to fetch user details from the UserRepository using the email address.
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository for User objects.
     */
    private final UserRepository userRepository;

    /**
     * This method is used to load a User object by its email address.
     * @param email The email address of the User to be loaded.
     * @return UserDetails The UserDetails object of the User.
     * @throws UsernameNotFoundException If no User with the given email address is found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailAddress(email).orElseThrow(() -> new UsernameNotFoundException("Username with "
                + email + " was not found!"));
    }
}
