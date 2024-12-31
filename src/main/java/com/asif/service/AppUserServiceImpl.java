package com.asif.service;

import com.asif.entity.AppUser;
import com.asif.payload.LoginDto;
import com.asif.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl {
    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;

        this.jwtService = jwtService;
    }

    public AppUser createUser(AppUser user) {
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(7));
            user.setPassword(hashpw);


        return appUserRepository.save(user);
    }

   // public boolean veryFyLogin(LoginDto loginDto) {
    //it is also before generate the token
   public String veryFyLogin(LoginDto loginDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()) {
            AppUser appUser = opUser.get();
            //return BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword());
            //it is before generated the token
            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
                return jwtService.generateToken(appUser);
            }
        }
                return null;
            }
        }
       // return false;
       //before token generated


