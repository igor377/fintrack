package com.fintrack.demo.shared.util;

import java.util.Locale;

public final class EmailUtil {
    private EmailUtil() {}

    public static String normalizeEmail(String email) {
        return email == null ? null : email.strip().toLowerCase(Locale.ROOT);
    }
}
