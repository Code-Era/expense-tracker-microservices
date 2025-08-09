package com.financeservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String statusCode; // Status code for the API response
    private String statusMsg;  // Status message (e.g., "Success" or error details)
    private T payload;         // The actual response data (generic type)
    private ErrorModel error;
    private LocalDateTime timestamp;// ErrorModel details, if any

}