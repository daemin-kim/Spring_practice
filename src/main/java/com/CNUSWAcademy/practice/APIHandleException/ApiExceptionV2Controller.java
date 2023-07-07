package com.CNUSWAcademy.practice.APIHandleException;

import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
  @ExceptionHandler에는 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다
  해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다.
  지정한 예외와, 그 하위 예외는 모두 잡을 수 있다.
  그리고 부모 예외와 자식 예외를 모두 지정한다면, 자식 예외가 우선순위 높게 처리된다.
 */

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(ExecutionControl.UserException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 사용자");
        }

        /*
          if(id.equals("user-ex")) {
            throw new ExecutionControl.UserException("사용자 오류");
          }
         */

        return new MemberDto(id, "hello " + id);
    }

    // 다양한 예외를 찹는 경우
    @ExceptionHandler({Exception.class, Exception.class})
    public String ex(Exception e) {
        log.info("exception e", e);
        return "";
    }

    /*
      예외를 생략할 수도 있다.
      @ExceptionHandler
      public ResponseEntity<ErrorResult> userExHandle(ExecutionControl.UserException e) { return null;}
     */

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
