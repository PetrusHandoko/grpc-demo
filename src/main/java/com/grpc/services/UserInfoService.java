package com.grpc.services;


import com.grpc.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
public class UserInfoService {

    @Autowired
    private GrpcService grpcServ;

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public UserInfo getUser(@RequestParam("id") int id ){
        UserInfo uinfo = new UserInfo();
        return uinfo;
    }

    @GetMapping("/test/{id}")
    public UserInfo getTest (@PathVariable(name = "id") int id ){
        UserInfo uinfo = new UserInfo( );
        return  uinfo;
    }

    @PostMapping(value = "/userinfo",consumes = MediaType.APPLICATION_JSON)
    public UserInfo updateUserInfo(@RequestBody() UserInfo uinfo){
        return grpcServ.sendMessage(uinfo);
    }
}
