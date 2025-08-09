package com.financeservice.payload;


import com.financeservice.payload.ApiResponse;
import com.financeservice.payload.ErrorModel;
import com.financeservice.payload.uitl.ApiResponseUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Getter
@Setter
@Service
@Data
public class ResponseService {


    public static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponseHeader(HttpHeaders headers,
                                                                                T payload,
                                                                                int statusCode,
                                                                                String statusMsg ) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createSuccessResponse(payload,
                String.valueOf(statusCode), statusMsg);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);


        return new ResponseEntity<>(apiResponse, headers, httpStatus);
      //  return new ResponseEntity<>(apiResponse, httpStatus);
    }

    // Generic success response (can be used for all operations)
    public static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T payload, int statusCode,
                                                                   String statusMsg ) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createSuccessResponse(payload,
                String.valueOf(statusCode), statusMsg);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }



    // Specialized success response for Create operation
    public static <T> ResponseEntity<ApiResponse<T>> buildCreateResponse(T payload, int statusCode,
                                                                  String statusMsg) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createSuccessResponse(payload,
                String.valueOf(statusCode), statusMsg);
        //apiResponse.setStatusMsg(statusMsg + " - Resource Created");
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    // Specialized success response for Update operation
    public static <T> ResponseEntity<ApiResponse<T>> buildUpdateResponse(T payload, int statusCode, String statusMsg) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createSuccessResponse(payload,
                String.valueOf(statusCode),statusMsg);
        // Add custom behavior for updates (optional)
        apiResponse.setStatusMsg(statusMsg + " - Resource Updated");
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    // Specialized success response for Delete operation
    public static <T> ResponseEntity<ApiResponse<T>> buildDeleteResponse(T payload, int statusCode,
                                                                  String statusMsg) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createSuccessResponse(payload,
                String.valueOf(statusCode),statusMsg );
        // Custom status message for deletion
        //apiResponse.setStatusMsg(statusMsg + " - Resource Deleted");
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    // Specialized response for validation failure
    public static <T> ResponseEntity<ApiResponse<T>> buildValidationErrorResponse(int statusCode,
                                                                           ErrorModel errorModelDetails, String  errorMsg) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createErrorResponse(statusCode, errorModelDetails, errorMsg);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    // ErrorModel response (generic or specific)
    public static  <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(int statusCode,
                                                                 ErrorModel errorModelDetails,
                                                                 String errorMsg) {
        ApiResponse<T> apiResponse = ApiResponseUtil.createErrorResponse(statusCode, errorModelDetails, errorMsg);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
    public static <T> ResponseEntity<ApiResponse<T>> buildErrorResponseWithoutErrorObject(int statusCode,
                                                                 ErrorModel errorModelDetails,
                                                                 String errorMsg) {
        ErrorModel errorModel = new ErrorModel();
        if(errorModelDetails == null) {
            errorModel.setApiPath("");
            errorModel.setErrorCode(HttpStatus.valueOf(statusCode));
            errorModel.setErrorMessage(errorMsg);
            errorModel.setErrorTime(LocalDateTime.now());
        }
        ApiResponse<T> apiResponse = ApiResponseUtil.createErrorResponse(statusCode, errorModel, errorMsg);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}

