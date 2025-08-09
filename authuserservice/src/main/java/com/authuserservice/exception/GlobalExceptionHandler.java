package com.authuserservice.exception;




import com.authuserservice.constant.ResponseConstants;
import com.authuserservice.exception.common.*;
import com.authuserservice.payload.ApiResponse;
import com.authuserservice.payload.ErrorModel;
import com.authuserservice.payload.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler  {


    /***
     * For All Excpetion
     * @param exception
     * @param request
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception exception, WebRequest request) throws Exception {
        // Agar exception Spring Security ke related hai, to pass kar do (or ignore)
        if (exception instanceof org.springframework.security.core.AuthenticationException
                || exception instanceof org.springframework.security.access.AccessDeniedException) {
            // Let Spring Security handle these exceptions
            throw exception;
        }

        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getDescription(false));
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setErrorMessage(exception.getMessage());
        error.setErrorTime(LocalDateTime.now());


        return ResponseService.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    }

    /**
     * For handleValidationException
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();


        Map<String, String> errors = fieldErrors.stream()
                .collect(Collectors.toMap(
                        item -> Function.identity().apply(item.getField()).toString(),
                        item -> item.getDefaultMessage()
                ));


        ErrorModel errorModel = new ErrorModel();
        errorModel.setApiPath(request.getRequestURI());
        errorModel.setErrorCode(HttpStatus.BAD_REQUEST);
        errorModel.setErrorMessage(ResponseConstants.ERROR_VALIDATION_MSG);
        errorModel.setErrorTime(LocalDateTime.now());
        errorModel.setErrorList(errors);

        return   ResponseService.buildValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorModel,
                ResponseConstants.ERROR_CODE_VALIDATION );
    }

    /**
     * For opertaion fail
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<?> handleSaveFail(OperationFailedException ex, HttpServletRequest request) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorMessage(ex.getMessage());
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setErrorTime(LocalDateTime.now());

        return ResponseService.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error,
                ResponseConstants.SUCCESS_REGISTRATION_FAILED
        );
    }

    /**
     * For duplicate resource
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> duplicateResourceException(DuplicateResourceException ex, HttpServletRequest request) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorMessage(ex.getMessage());
        error.setErrorCode(HttpStatus.CONFLICT);
        error.setErrorTime(LocalDateTime.now());

        return ResponseService.buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                error,
                ex.getMessage()
        );
    }

    /*
     * For duplicate resource
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorMessage(ex.getMessage());
        error.setErrorCode(HttpStatus.NOT_FOUND);
        error.setErrorTime(LocalDateTime.now());

        return ResponseService.buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                error,
                ResponseConstants.RESOURCE_NOT_FOUND
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorMessage(ex.getMessage());
        error.setErrorCode(HttpStatus.FORBIDDEN);
        error.setErrorTime(LocalDateTime.now());

        return ResponseService.buildErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                error,
                ResponseConstants.ACCESS_DENIED
        );
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> throwBadRequest(BadRequestException ex, HttpServletRequest request) {
        ErrorModel error = new ErrorModel();
        error.setApiPath(request.getRequestURI());
        error.setErrorMessage(ex.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST);
        error.setErrorTime(LocalDateTime.now());

        return ResponseService.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                error,
                ResponseConstants.BAD_REQUEST
        );
    }

}
