package com.fintrack.demo.shared.util;

public final class CpfUtil {
    private CpfUtil() {}    

    public static String cleanCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("[^0-9]", "");
    }

    public static String maskCpf(String cpf) {
        String cleanedCpf = cleanCpf(cpf);
        if (cleanedCpf == null || cleanedCpf.length() != 11) {
            return null;
        }

        return "***." + cleanedCpf.substring(3, 6) + "." + cleanedCpf.substring(6, 9) + "-**";
    }
}   