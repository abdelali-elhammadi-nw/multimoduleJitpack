package com.nimbleways.springboilerplate.security;

import com.nimbleways.springboilerplate.entities.User;
import com.nimbleways.springboilerplate.exceptions.ErrorMessage;
import com.nimbleways.springboilerplate.exceptions.UserNotFoundException;
import com.nimbleways.springboilerplate.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.from("Auth error: user not found"),
                        email));
        return UserDetailsImpl.build(user);
    }

}
