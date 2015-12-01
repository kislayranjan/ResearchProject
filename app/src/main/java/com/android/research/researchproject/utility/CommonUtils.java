package com.android.research.researchproject.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by B0089798 on 11/21/2015.
 */
public class CommonUtils {



    /**
     * @param data
     * @return
     */
    public static boolean checkForNull(String data) {
        boolean success = Boolean.FALSE;

        if (null != data && !data.isEmpty()) {
            success = Boolean.TRUE;
        }

        return success;
    }



    static String REGEX_PHONE_NUMBER = "^[7-9][0-9]{9}$";

    public static boolean isValidPhoneNumber(String number) {
        boolean isValid = false;
        if (number != null && number.matches(REGEX_PHONE_NUMBER)) {
            return true;
        }
        return isValid;
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
