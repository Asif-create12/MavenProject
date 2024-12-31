package com.asif.controller;


import com.asif.entity.AppUser;
import com.asif.exception.UserExists;
import com.asif.payload.JWTToken;
import com.asif.payload.LoginDto;
import com.asif.repository.AppUserRepository;
import com.asif.service.AppUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AppUserRepository appUserRepository;
    private AppUserServiceImpl appUserService;

    public AuthController(AppUserRepository appUserRepository, AppUserServiceImpl appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()) {
            throw new UserExists("email id exists");
        }
        Optional<AppUser> opUsername = appUserRepository.findByEmail(user.getUsername());
        if (opUsername.isPresent()) {
            throw new UserExists("username is exists");
        }
        AppUser savedUser = appUserService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto){
        //boolean status = appUserService.veryFyLogin(loginDto);
        //it is before token generated
       String token = appUserService.veryFyLogin(loginDto);
        JWTToken jwtToken = new JWTToken();
        if(token!=null){
            // return new ResponseEntity<>("saccessful",HttpStatus.OK);
            //this is before generate token
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);

        }else{
            return new ResponseEntity<>("invalid username/password",HttpStatus.UNAUTHORIZED);
        }

    }


}
