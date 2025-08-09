package com.notificationservice.payload.util;


import com.notificationservice.payload.ApiResponse;
import com.notificationservice.payload.ErrorModel;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseUtil {

    // Method to create success response with dynamic status message
    public static <T> ApiResponse<T> createSuccessResponse(T payload, String statusCode, String statusMsg ) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(statusCode);  // 201 Created
        apiResponse.setStatusMsg(statusMsg != null ? statusMsg : "Resource created successfully.");
        apiResponse.setPayload(payload);
        return apiResponse;
    }

    // Method to create error response with dynamic status message
    public static <T> ApiResponse<T> createErrorResponse(int statusCode,
                                                         ErrorModel error,
                                                         String statusMsg) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(String.valueOf(statusCode));  // 400 Bad Request (example)
        apiResponse.setStatusMsg(statusMsg != null ? statusMsg : "ErrorModel processing request.");
        apiResponse.setError(error);
        return apiResponse;
    }

    // Generic response builder with dynamic status message
    public static <T> ApiResponse<T> createResponse(String statusCode, String statusMsg,
                                                    T payload, ErrorModel error) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(String.valueOf(statusCode));
        apiResponse.setStatusMsg(statusMsg != null ? statusMsg : "No message provided.");
        apiResponse.setPayload(payload);
        apiResponse.setError(error);
        return apiResponse;
    }

    public static ErrorModel createErrorModel(String apiPath, HttpStatus httpStatus,
                                              String exMsg) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(apiPath);
        error.setErrorCode(httpStatus);
        error.setErrorMessage(exMsg);
        error.setErrorTime(LocalDateTime.now());
        return error;
    }



}
