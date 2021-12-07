package com.grpc.services;

import com.google.type.DateTime;
import com.grpc.entities.UserInfo;
import com.grpc.lib.UserInfoUpdateRequest;
import com.grpc.lib.UserInfoUpdateResponse;
import com.grpc.lib.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
public class GrpcService {

    @GrpcClient("userServiceServer")
    private UserServiceGrpc.UserServiceBlockingStub UserServiceStub;

    public UserInfo sendMessage(final UserInfo userInfo) {
        try {
            String current = Timestamp.from(Instant.now()).toString();
            final UserInfoUpdateResponse response = this.UserServiceStub.sendUpdate(UserInfoUpdateRequest.newBuilder()
                    .getDefaultInstanceForType().newBuilder()
                    .setFirstname(userInfo.getFirstName())
                    .setLastname(userInfo.getLastName())
                    .setDob(userInfo.getDateOfBirth())
                    .setEmail(userInfo.getEmail())
                    .setTimestamp(current)
                    .build());
            String timestamp = response.getTimestamp();
            String status = response.getStatus();
            userInfo.setTimeStamp(timestamp);
            return userInfo;
        } catch (final StatusRuntimeException e) {
            log.error("Request failed", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available\n");
        }

    }
}