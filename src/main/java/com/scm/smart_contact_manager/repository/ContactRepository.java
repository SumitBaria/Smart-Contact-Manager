package com.scm.smart_contact_manager.repository;

import com.scm.smart_contact_manager.entity.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findByUserId(UUID userId, Pageable pageable);

    List<Contact> findByUserIdAndIsFavourite(UUID userId, boolean favourite, Pageable pageable);

    Integer countByUserId(UUID userId);

    Integer countByUserIdAndIsFavourite(UUID userId, boolean favourite);
}