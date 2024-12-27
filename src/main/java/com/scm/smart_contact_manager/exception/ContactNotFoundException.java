package com.scm.smart_contact_manager.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContactNotFoundException extends RuntimeException {

    private final String message;

}
