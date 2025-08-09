    package com.authuserservice.authExceptionHandler;


    import com.authuserservice.payload.ApiResponse;
    import com.authuserservice.payload.ErrorModel;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.SerializationFeature;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.security.web.access.AccessDeniedHandler;

    import java.io.IOException;

    /**
     * Authorization failed
     */
    public class CustomAccessDeniedHandler  implements AccessDeniedHandler {

        private final ObjectMapper objectMapper;

        // Constructor injection of ObjectMapper
        public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            // Enable pretty-printing for JSON response
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException) throws IOException, ServletException {
            String message =  (accessDeniedException != null && accessDeniedException.getMessage() != null )
                    ? accessDeniedException.getMessage() : "Authorization failed";

            response.setHeader("ExpanseTracker-denied-reason", "Authorization failed");
            response.setContentType("application/json;charset=utf-8");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=utf-8");

            ErrorModel error = new ErrorModel();
            error.setApiPath(request.getRequestURI());
            error.setErrorCode(HttpStatus.FORBIDDEN);
            error.setErrorMessage(accessDeniedException.getMessage());
            //error.setErrorTime(LocalDateTime.now());


            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setError(error);
            apiResponse.setStatusCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
            apiResponse.setStatusMsg("Authorization failed");
            String json = objectMapper.writeValueAsString(apiResponse);
          //  String json = objectMapper.writeValueAsString(respons);
            response.getWriter().write(String.valueOf(json));
        }
    }
