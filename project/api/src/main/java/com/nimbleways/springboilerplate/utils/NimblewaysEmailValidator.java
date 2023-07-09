package com.nimbleways.springboilerplate.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NimblewaysEmailValidator implements ConstraintValidator<NimblewaysEmail, String> {
    final String suffix = "@nimbleways.com";

    private Pattern pattern = Pattern.compile(
            "^[\\w-_.+]*[\\w-_.]@(?:[\\w]+\\.)+[\\w]+[\\w]$"
    );

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email.isBlank()){
            return true;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && email.endsWith(suffix);
    }
}
