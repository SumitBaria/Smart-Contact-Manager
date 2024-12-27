package com.scm.smart_contact_manager.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class ContactListResponse {
    Integer totalContacts;
    List<ContactResponse> contacts;
}
