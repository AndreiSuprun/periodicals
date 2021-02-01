package com.suprun.periodicals.view;

import com.suprun.periodicals.view.filter.LocaleFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum SupportedLocale {
    EN(new Locale("en", "EN")),
    RU(new Locale("ru", "BY"));

    private final static Locale DEFAULT_LOCALE = RU.getLocale();

    private Locale locale;

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Get default locale
     *
     * @return default locale
     */
    public static Locale getDefault() {
        return DEFAULT_LOCALE;
    }

    /**
     * Convert language code to appropriate locale
     *
     * @param lang language code in lower case for
     *             appropriate locale in ISO 639 format
     * @return appropriate locale or default
     */
    public static Locale getLocaleOrDefault(String lang) {
        return Arrays.stream(SupportedLocale.values())
                .map(SupportedLocale::getLocale)
                .filter(x -> x.getLanguage().equals(lang))
                .findFirst()
                .orElse(getDefault());
    }

    /**
     * Get supported languages
     *
     * @return List of supported languages
     */
    public static List<String> getSupportedLanguages() {
        return Arrays.stream(SupportedLocale.values())
                .map(x -> x.getLocale().getLanguage())
                .collect(Collectors.toList());
    }
}
