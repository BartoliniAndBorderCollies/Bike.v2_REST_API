package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailAddress(email).orElseThrow(() -> new UsernameNotFoundException("Username with "
                + email + " was not found!"));
        //consumer, supplier, function, predicate
    }
}
