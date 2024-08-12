package com.spalla.customer.api.utils;

import org.apache.commons.lang3.RegExUtils;

public class CustomerDataUtils {

    public static String maskNameField(String name){
        return RegExUtils.replaceAll(name, "(?<=^.)(.*)(?=.$)", "*".repeat(name.length()-2));
    }

    public static String maskEmailField(String email){
        return RegExUtils.replaceAll(email,
                "(?<=.)[^@](?=[^@]*?@)|(?:(?<=@.)|(?!^)\\\\G(?=[^@]*$)).(?=.*\\\\.)", "*");
    }

    public static String maskPhoneField(String phoneNumber){
        return RegExUtils.replaceAll(phoneNumber,
                "(?<=.)[^-](?=[^-]*?-)|(?:(?<=-.)|(?!^)\\\\G(?=[^-]*$)).(?=.*\\\\.)", "*");
    }

    public static boolean isValidEmailId(String emailId){
        return RegExUtils.dotAllMatcher("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                emailId).matches();
    }

}
