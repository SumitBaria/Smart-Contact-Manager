package com.scm.smart_contact_manager.service.impl;

import com.scm.smart_contact_manager.entity.Contact;
import com.scm.smart_contact_manager.entity.request.ContactRequest;
import com.scm.smart_contact_manager.entity.response.ContactListResponse;
import com.scm.smart_contact_manager.entity.response.ContactResponse;
import com.scm.smart_contact_manager.exception.ContactNotFoundException;
import com.scm.smart_contact_manager.repository.ContactRepository;
import com.scm.smart_contact_manager.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.scm.smart_contact_manager.constant.CommonConstants.CONTACT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ModelMapper modelMapper;
    private final ContactRepository contactRepository;

    private static Contact buildContact(ContactRequest contactRequest, String userId) {
        Contact contact = new Contact();
        contact.setId(Objects.nonNull(contactRequest.getId()) ? UUID.fromString(contactRequest.getId()) : null);
        contact.setMobileNumber(contactRequest.getMobileNumber());
        contact.setName(contactRequest.getName());
        contact.setEmail(contactRequest.getEmail());
        contact.setAddress(contactRequest.getAddress());
        contact.setFavourite(contactRequest.isFavourite());
        contact.setUserId(UUID.fromString(userId));
        return contact;
    }

    @Override
    public ContactResponse saveContact(ContactRequest contactRequest, String userId) {
        Contact contact = contactRepository.save(buildContact(contactRequest, userId));
        return modelMapper.map(contact, ContactResponse.class);
    }

    @Override
    public ContactListResponse getContactsList(String userIdString, Integer pageNo, Integer pageSize) {
        UUID userId = UUID.fromString(userIdString);
        List<ContactResponse> contactResponseList = contactRepository.findByUserId(userId, PageRequest.of(pageNo, pageSize))
                .stream()
                .map(contact -> modelMapper.map(contact, ContactResponse.class))
                .toList();

        Integer totalContacts = contactRepository.countByUserId(userId);
        return buildContactListResponse(contactResponseList, totalContacts);
    }

    private ContactListResponse buildContactListResponse(List<ContactResponse> contactResponseList, Integer totalContacts) {
        ContactListResponse contactListResponse = new ContactListResponse();
        contactListResponse.setContacts(contactResponseList);
        contactListResponse.setTotalContacts(totalContacts);
        return contactListResponse;
    }

    @Override
    public ContactResponse getContactDetail(String contactId) {

        return contactRepository.findById(UUID.fromString(contactId))
                .map(contact -> modelMapper.map(contact, ContactResponse.class))
                .orElseThrow(() -> new ContactNotFoundException(CONTACT_NOT_FOUND));
    }

    @Override
    public ContactResponse toggleFavourite(String contactId, boolean favourite) {
        Contact contact = contactRepository.findById(UUID.fromString(contactId)).orElseThrow(() -> new ContactNotFoundException(CONTACT_NOT_FOUND));
        contact.setFavourite(favourite);
        contact = contactRepository.save(contact);
        return modelMapper.map(contact, ContactResponse.class);
    }

    @Override
    public ContactListResponse getFavouriteContactsList(String userIdString, Integer pageNo, Integer pageSize) {
        UUID userId = UUID.fromString(userIdString);
        List<ContactResponse> contactResponseList = contactRepository.findByUserIdAndIsFavourite(userId, Boolean.TRUE, PageRequest.of(pageNo, pageSize))
                .stream()
                .map(contact -> modelMapper.map(contact, ContactResponse.class))
                .toList();

        Integer totalContacts = contactRepository.countByUserIdAndIsFavourite(userId, Boolean.TRUE);
        return buildContactListResponse(contactResponseList, totalContacts);
    }

    @Override
    public void deleteContact(String contactId) {
        Contact contact = contactRepository.findById(UUID.fromString(contactId)).orElseThrow(() -> new ContactNotFoundException(CONTACT_NOT_FOUND));
        contactRepository.delete(contact);
    }


}
