package com.seletivo.infra.api.controller;


import com.seletivo.domain.exceptions.DomainException;
import com.seletivo.domain.exceptions.NotFoundException;
import com.seletivo.domain.validation.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(final AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(final NullPointerException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public ResponseEntity<?> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException ex) {
        return ResponseEntity.badRequest().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = TransientDataAccessResourceException.class)
    public ResponseEntity<?> handleTransientDataAccessResourceException(final TransientDataAccessResourceException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<?> handleSQLException(final SQLException ex) {
        // Trata exceções SQL genéricas
        // Você pode verificar o SQLState ou o código de erro para tratamento mais específico
        return ResponseEntity.internalServerError().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        String error = "O parâmetro '%s' recebeu o valor '%s' que é de um tipo inválido".formatted(
                ex.getName(), ex.getValue()
        );
        return ResponseEntity.badRequest().body(new ApiError(error, null));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
        static ApiError from(final HttpRequestMethodNotSupportedException ex) {
            return new ApiError(ex.getMessage(), null);
        }
        static ApiError from(final AuthenticationException ex) {
            return new ApiError(ex.getMessage(), null);
        }
        static ApiError from(final HttpMessageNotReadableException ex) {
            return new ApiError(ex.getMessage(), null);
        }
        static ApiError from(final NullPointerException ex){
            return new ApiError(ex.getMessage(), null);
        }
        static ApiError from(final DataIntegrityViolationException ex) {
            return new ApiError(ex.getMessage(), null);
        }

        static ApiError from(final EmptyResultDataAccessException ex) {
            return new ApiError(ex.getMessage(), null);
        }

        static ApiError from(final InvalidDataAccessApiUsageException ex) {
            return new ApiError(ex.getMessage(), null);
        }

        static ApiError from(final TransientDataAccessResourceException ex) {
            return new ApiError(ex.getMessage(), null);
        }

        static ApiError from(final SQLException ex) {
            return new ApiError(ex.getMessage(), null);
        }

        static ApiError from(final SQLIntegrityConstraintViolationException ex) {
            return new ApiError(ex.getMessage(), null);
        }
    }
}