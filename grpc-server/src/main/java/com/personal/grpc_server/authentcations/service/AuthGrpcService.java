package com.personal.grpc_server.authentcations.service;

import com.personal.grpc_server.authentcations.jwt.JwtAuthProvider;
import com.project.AuthServiceGrpc;
import com.project.JWToken;
import com.project.JwtRequest;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {
    @Value("${jwt.signing.key}")
    String jwtSecretKey;

    private final JwtAuthProvider jwtAuthProvider;

    public AuthGrpcService(JwtAuthProvider jwtAuthProvider) {
        this.jwtAuthProvider = jwtAuthProvider;
    }

    @Override
    public void authenticate(JwtRequest request , StreamObserver<JWToken> responseObserver) {
        Authentication authenticate = jwtAuthProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName() , request.getPassword()));
        String authorize = authenticate.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.joining(","));
        Instant now =Instant.now();
        Instant expiration=now.plus(1, ChronoUnit.HOURS);
        String auth = Jwts.builder()
                .setSubject((String) authenticate.getPrincipal())
                .claim("auth" , authorize)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256 , jwtSecretKey)
                .compact();
        responseObserver.onNext(JWToken.newBuilder().setJwtToken(auth).build());
        responseObserver.onCompleted();
    }
}
