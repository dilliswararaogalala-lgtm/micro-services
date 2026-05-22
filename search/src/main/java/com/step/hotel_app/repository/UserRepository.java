package com.step.hotel_app.repository;

import com.step.hotel_app.models.AppUser;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<AppUser, String> {
    UserDetails getAppUsersByUsername(String username);

    UserDetails getAppUsersByUsernameAndPassword(String username, String password, Limit limit);

    UserDetails findAppUserByUsernameAndPassword(String username, String password);

    UserDetails findAppUserByUsername(String username);
}
