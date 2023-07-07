package com.CNUSWAcademy.practice.HttpMessageConverter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface HttpMessageConverter<T> {
    
    // 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크
    boolean canRead(Class<T> clazz, @Nullable MediaType mediaType);
    
    // 메시지 컨버터가 해당 클스, 미디어타입을 지원하는지 체크
    boolean canWrite(Class<T> clazz, @Nullable MediaType mediaType);

    List<MediaType> getSupportMediaTypes();

    default List<MediaType> getSupportedMediaTypes(Class<T> clazz) {
        return (canRead(clazz, null) || canWrite(clazz, null) ?
                getSupportMediaTypes() : Collections.emptyList());
    }
    // 메시지 컨버터를 통해 메시지를 읽음
    T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;

    // 메시지를 컨버터를 통해 메시지를 씀
    void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;
}


/*
    Spring boot에서 등록하는 기본 메시지 컨버터

    ByteArrayHttpMessageConverter
        -> byte[] 타입의 데이터를 처리함
        -> 클래스 타입 : byte[], 미디어 타입 : '*//*'
        -> 요청 예 -> @RequestBody byte[] data
        -> 응답 예 -> @ResponseBody return Body[], 쓰기 미디어타입 : application/actet-stream

    StringHttpMessageConverter
        -> String 문자로 데이터를 처리함
        -> 클래스 타입 : String, 미디어 타입 : '*//*'
        -> 요청 예 -> @RequestBody String data
        -> 응답 예 -> @ResponseBody return "ok", 쓰기 미디어타입 : test/plain

    MappingJackson2HttpMessageConverter
        -> application/json을 처리함
        -> 클래스 타입 : 객체 또는 HashMap, 미디어 타입 : application/json
        -> 요청 예 -> @RequestBody HelloData data
        -> 응답 예 -> @RequestBody return HelloData, 쓰기 미디어타입 : application/json

    HTTP 요청 데이터 읽기

        컨트롤러에서 @RequestBody, HttpEntity 파라미터를 사용한 상태에서 HTTP 요청이 온 경우, 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해
        canRead를 호출함.

        다음 두 조건을 만족하는지 확인함 -> 대상 클래스 타입을 지원하는가?, HTTP 요청의 Content-Type 미디어 타입을 지원하는가?

        canRead() 조건을 만족하면 read()를 호출해서 객체를 생성하고 반환함.

    HTTP 응답 데이터 생성

        @RequestBody, HTTPEntity로 컨트롤러에서 값을 반환하는 경우, 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWrite()를 호출함

        다음 두 조건을 만족하는 확인함 -> 대상 클래스 타입을 지원하는가?, HTTP 요청의 Accept 미디어 타입을 지원하는가?

        canWrite() 조건을 만족하면 write를 호출해서 HTTP 응답 메시지 바디에 메시지를 생성함.

    HTTPMessageConverter 사용 위치

        HTTPMessageConverter는 어노테이션 기반의 컨트롤러, 즉 @RequestMapping을 처리하는 핸들러 어댑터인 RequestMappingHandlerAdapter에서 사용함

    HandlerMethodArgumentResolver

        어노테이션 기반의 컨트롤러는 매우 다양한 파라미터(HttpServletRequest, @RequestParam,
        @ModelAttribute, @RequestBody, HttpEntity)를 사용할 수 있음.

        이러한 것이 가능한 이유가 HandlerMethodArgumentResolver 덕분이다.

        어노테이션 기반 컨트롤러를 처리하는 RequestMappingHandlerAdapter는 컨트롤러를 호출하기 전에 ArgumentResolver를 사용해서
        들어온 요청 정보를 컨트롤러가 필요로 하는 데이터 형식으로 변환하여 줌

    HandlerMethodReturnValueHandler

        응답 데이터를 전송할 때는 HandlerMethodReturnValueHanlder를 사용해서 컨트롤러가 반환한 데이터를 변환시켜서 반환해줌

    HttpMessageConverter 사용위치

        결국 HttpMessageConverter는 HandlerMethodArgumentResolver와 HandlerMethodReturnValueHandler에서 사용된다.

        HttpMessageConverter를 사용하는 @RequestBody 역시도 컨트롤러가 필요로 하는 파라미터에 사용됨
        @ResponseBody는 컨트롤러의 반환 값에서 사용됨.

        즉 요청의 경우 @RequestBody를 처리하는 HandlerMethodArgumentResolver가 있으며, HttpEntity를 처리하는 HandlerMethodArgumentResolver가 있음.
        해당 HandlerMethodArgumentResolver들에서 HttpMessageConverter를 사용해서 필요한 객체를 생성함.

 */
