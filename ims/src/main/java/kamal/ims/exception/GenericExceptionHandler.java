package kamal.ims.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Invalid username or password.")
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(e.getClass() + e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) throws JsonProcessingException {
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CONFLICT)
                        .message("Data Integrity Violation "+ e.getMessage())
                        .build()
        );
    }
}
