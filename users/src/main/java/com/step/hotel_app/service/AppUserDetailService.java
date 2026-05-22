package com.step.hotel_app.service;

import com.step.hotel_app.models.AppUser;
import com.step.hotel_app.repository.UserRepository;
import com.step.hotel_app.views.UserReq;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {
        return userRepository.findAppUserByUsernameAndPassword(username,password);
    }

    public boolean register(UserReq user){
        AppUser newUser = new AppUser(user.username(), user.password());
        try{
            userRepository.save(newUser);
            return true;
        }catch (Throwable e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAppUserByUsername(username);
    }
}
