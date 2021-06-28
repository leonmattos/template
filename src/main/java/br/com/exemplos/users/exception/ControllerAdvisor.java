package br.com.exemplos.users.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class ControllerAdvisor {
    public static final String ENTITY_NOT_FOUND_MESSAGE = "entity.not.found.message";
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, locale))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Erro(null, errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Erro> handlerEntityNotFoundException(EntityNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ENTITY_NOT_FOUND_MESSAGE, null, locale);
        log.error(ex.getMessage());
        return new ResponseEntity<>(new Erro(String.format(message, ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Erro> handlerGenericException(GenericException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new Erro(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
