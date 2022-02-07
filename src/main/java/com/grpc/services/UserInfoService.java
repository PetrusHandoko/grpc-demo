package com.grpc.services;


import com.grpc.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoService {

    @Autowired
    private GrpcService grpcServ;

    @PostMapping(value = "/userinfo",consumes =  MediaType.APPLICATION_JSON_VALUE)
    public UserEntity updateUserInfo(@RequestBody() UserEntity uinfo){
        return grpcServ.sendMessage(uinfo);
    }
}
