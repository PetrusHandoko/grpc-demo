package com.grpc.services;

import com.grpc.entities.UserInfo;
import com.grpc.lib.UserInfoStub;
import com.grpc.lib.UserInfoUpdateRequest;
import com.grpc.lib.UserInfoUpdateResponse;
import com.grpc.lib.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcService {

    @GrpcClient("cloud-grpc-server")
    private UserServiceGrpc.UserServiceBlockingStub UserServiceStub;

    public UserInfo sendMessage(final UserInfo userInfo) {
        try {
            final UserInfoUpdateResponse response  = this.UserServiceStub.sendUpdate(UserInfoUpdateRequest.newBuilder()
                    .getDefaultInstanceForType().newBuilder()
                    .setFirstname(userInfo.getFirstName())
                    .setLastname(userInfo.getLastName())
                    .setDob(userInfo.getDateOfBirth())
                    .setEmail(userInfo.getEmail())
                    .build());
            UserInfo updatedInfo = new UserInfo();
            updatedInfo.setFirstName(response.getFirstname());
            updatedInfo.setLastName(response.getLastname());
            updatedInfo.setEmail(response.getEmail());
            updatedInfo.setCreationDate(response.getLastupdate());
            updatedInfo.setCreationDate(response.getLastupdate());

            return updatedInfo;
        } catch (final StatusRuntimeException e) {
            log.error("Request failed", e);
            return new UserInfo();
        }
    }

}