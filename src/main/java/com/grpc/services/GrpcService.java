package com.grpc.services;

import com.grpc.entities.UserEntity;
import com.grpc.lib.UserInfoServiceGrpc;
import com.grpc.lib.UserInfoUpdateRequest;
import com.grpc.lib.UserInfoUpdateResponse;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
public class GrpcService {

    @GrpcClient("userServiceServer")
    private UserInfoServiceGrpc.UserInfoServiceBlockingStub UserServiceStub;

    public UserEntity sendMessage(final UserEntity userEntity) {
        try {
            String current = Timestamp.from(Instant.now()).toString();
            final UserInfoUpdateResponse response = this.UserServiceStub.sendUpdate(UserInfoUpdateRequest.newBuilder()
                    .getDefaultInstanceForType().newBuilder()
                    .setFirstname(userEntity.getFirstName())
                    .setLastname(userEntity.getLastName())
                    .setDob(userEntity.getDateOfBirth())
                    .setEmail(userEntity.getEmail())
                    .setTimestamp(current)
                    .build());
            String timestamp = response.getTimestamp();
            String status = response.getStatus();
            userEntity.setTimeStamp(timestamp);
            return userEntity;
        } catch (final StatusRuntimeException e) {
            log.error("Request failed", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available\n");
        }

    }
}