package com.scm.smart_contact_manager.validator;

import com.scm.smart_contact_manager.annotation.MobileNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileNumberWithCountryCodeValidator implements ConstraintValidator<MobileNumber, String> {

    // Regex for mobile number with country code (e.g., +91 9876543210)
    private static final String MOBILE_NUMBER_REGEX = "^\\+\\d{1,3} [6-9]\\d{9}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            log.error("Got empty mobile number");
            return false;
        }
        return value.matches(MOBILE_NUMBER_REGEX);
    }
}
