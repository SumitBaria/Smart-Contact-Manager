package com.scm.smart_contact_manager.entity.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactResponse {

    private UUID id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private boolean isFavourite;
}
