package com.CNUSWAcademy.practice.JSON;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ObjectMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String DIRECT_STRING_LIST = """ 
        {
          "address" : "123",
          "visitor :
            [ "daemin";
              "Spring"
              "practice"
            ]
        }
            """;

    @Test
    @DisplayName("List<String> 반환")
    public void stringListTest() throws JsonProcessingException {
        final Blog blog = objectMapper.readValue(DIRECT_STRING_LIST, Blog.class);
        System.out.println(blog.getAddress());
        for(int i = 0; i < blog.getVisitor().size(); i++) {
            System.out.println(blog.getVisitor().get(i));
        }
    }

}
