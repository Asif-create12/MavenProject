package com.asif.service;


import com.asif.entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithem.key}")
    private String algirithemKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private Long expiryTime;

    private Algorithm algorithem;

    private static final String USER_NAME="username";
    //this method is used for get jwt token

    @PostConstruct
    public void postContruct() throws UnsupportedEncodingException {
       // System.out.println(algirithemKey);
      //  System.out.println(issuer);
      //  System.out.println(expiryTime);
      algorithem = Algorithm.HMAC256(algirithemKey);
    }
    public String generateToken(AppUser user){
        return JWT.create().
              //  withClaim("username",user.getUsername()).
                //this is before get jwt token
                        withClaim(USER_NAME,user.getUsername()).

                       withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).
                        withIssuer(issuer)
                .sign(algorithem);
    }
    public String getUsername(String token){
        //jocky rocky with bodybuilder vikram
        DecodedJWT decodedjwt = JWT.require(algorithem).withIssuer(issuer)
                .build().verify(token);

      return  decodedjwt.getClaim(USER_NAME).asString();
        //means get username & return back as a String

    }

}
