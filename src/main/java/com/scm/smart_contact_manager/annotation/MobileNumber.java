package com.scm.smart_contact_manager.annotation;

import com.scm.smart_contact_manager.validator.MobileNumberWithCountryCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MobileNumberWithCountryCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber  {
    String message() default "Invalid mobile number format. Use: +<country_code> <10-digit-number>";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
