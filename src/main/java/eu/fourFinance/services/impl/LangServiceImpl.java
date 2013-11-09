package eu.fourFinance.services.impl;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import eu.fourFinance.services.LangService;

@Service
public class LangServiceImpl implements LangService {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource = null;

    private Locale defaultLocale = new Locale("en");

    public String get(String key, Object...args) {
        String result = null;
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        try {
            result = messageSource.getMessage(key, args, defaultLocale);
        } catch (NoSuchMessageException nsmEx) {
            result = key;
        }
        return result;
    }

    public String getMessage(Locale locale, String key, String... args) {
        String result = null;
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        try {
            result = this.messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException nsmEx) {
            result = key;
        }
        return result;
    }

}
