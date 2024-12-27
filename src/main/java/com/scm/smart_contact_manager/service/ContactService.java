package com.scm.smart_contact_manager.service;

import com.scm.smart_contact_manager.entity.request.ContactRequest;
import com.scm.smart_contact_manager.entity.response.ContactListResponse;
import com.scm.smart_contact_manager.entity.response.ContactResponse;

public interface ContactService {

    ContactResponse saveContact(ContactRequest contactRequest, String userId);

    ContactListResponse getContactsList(String userId, Integer pageNo, Integer pageSize);

    ContactResponse getContactDetail(String contactId);

    ContactResponse toggleFavourite(String contactId, boolean favourite);

    ContactListResponse getFavouriteContactsList(String userId, Integer pageNo, Integer pageSize);

    void deleteContact(String contactId);

}
