package com.financeservice.service.Dashboard.clients;

import com.financeservice.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service")
public interface UserFeignClient {

    @GetMapping(value = "/v1/auth/getUserCounts", consumes = "application/json")
    ResponseEntity<ApiResponse<Long>> getAllUsersCount() ;

}
