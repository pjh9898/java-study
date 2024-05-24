package kr.co.hanbit.prroduct.management.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import kr.co.hanbit.prroduct.management.domain.exception.EntityNotFoundException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handelConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage()
                )
                .toList();
        ErrorMessage errorMessage = new ErrorMessage(errors.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(
                        fieldError ->
                                fieldError.getField() + ", " + fieldError.getDefaultMessage()
                )
                .toList();

        ErrorMessage errorMessage = new ErrorMessage(errors.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(errors.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    private String extractField(Path path) {
        String[] splittedPath = path.toString().split("[.]");
        return splittedPath[splittedPath.length - 1];
    }
}
