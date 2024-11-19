package kr.lililli.hellorestfulservice.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.setTimestamp(new Date());
    exceptionResponse.setMessage(ex.getMessage());
    exceptionResponse.setDetails(request.getDescription(false));

    System.out.println("@@@@@@@@@@@@handleAllExceptions");
    return new ResponseEntity<>(exceptionResponse,
        org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<?> handleUserNotFoundException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.setTimestamp(new Date());
    exceptionResponse.setMessage(ex.getMessage());
    exceptionResponse.setDetails(request.getDescription(false));

    System.out.println("@@@@@@@@@@@@handleUserNotFoundException");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();

    StringBuilder details = new StringBuilder();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      details.append(error.getDefaultMessage());
    });

    exceptionResponse.setTimestamp(new Date());
    exceptionResponse.setMessage(details.toString().trim());
    exceptionResponse.setDetails(request.getDescription(false));

    System.out.println("@@@@@@@@@@@@handleUserNotFoundException");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.setTimestamp(new Date());
    exceptionResponse.setMessage("페이지를 찾을 수 없습니다.");
    exceptionResponse.setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }
}
