package dev.poncio.SystemApps.InstantNotificationCenter.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageSource messageSource;

    public String get(String key) {
        return get(key, null);
    }

    public String get(String key, Locale locale) {
        return messageSource
            .getMessage(key, null, 
                (locale != null ? locale : getLocale()));
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

}