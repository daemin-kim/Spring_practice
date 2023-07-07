package com.CNUSWAcademy.practice.MessageSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class MessageSourceTestCode {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void helloMessage() throws Exception {
        String message = messageSource.getMessage("hello", null, null);
        assert message.equals("hello");

        String messageEn = messageSource.getMessage("hello", null, Locale.ENGLISH);
        assert messageEn.equals("hello");

        messageEn = messageSource.getMessage("hello", new Object[]{"김", "대민"}, Locale.ENGLISH);
        assert messageEn.equals("hello 김");

        String noSuchMessage = messageSource.getMessage("no_match", null, "기본 메세지",Locale.KOREA);
        assert noSuchMessage.equals("기본 메세지");

    }
}
