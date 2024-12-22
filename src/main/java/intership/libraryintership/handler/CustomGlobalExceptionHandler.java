package intership.libraryintership.handler;

import intership.libraryintership.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(DuplicateProfileException.class)
    public ResponseEntity<?> exceptionHandler(DuplicateProfileException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> exceptionHandler(UnauthorizedException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> exceptionHandler(DuplicateDataException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<?> exceptionHandler(BookAlreadyExistsException e) {
        return ResponseEntity.status(200).body(e.getMessage());
    }

    @ExceptionHandler(AppBadRequestException.class)
    public ResponseEntity<?> exceptionHandler(AppBadRequestException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(LoanCountException.class)
    public ResponseEntity<?> exceptionHandler(LoanCountException e) {
        return ResponseEntity.status(200).body(e.getMessage());
    }

    @ExceptionHandler(StatusOKButException.class)
    public ResponseEntity<?> exceptionHandler(StatusOKButException e) {
        return ResponseEntity.status(200).body(e.getMessage());
    }
}
