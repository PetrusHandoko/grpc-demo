package com.grpc.services;


import com.grpc.entities.UserInfo;
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
    public UserInfo updateUserInfo(@RequestBody() UserInfo uinfo){
        return grpcServ.sendMessage(uinfo);
    }
}
