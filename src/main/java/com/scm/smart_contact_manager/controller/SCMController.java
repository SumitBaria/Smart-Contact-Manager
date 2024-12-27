package com.scm.smart_contact_manager.controller;


import com.scm.smart_contact_manager.entity.request.ContactRequest;
import com.scm.smart_contact_manager.entity.response.ContactListResponse;
import com.scm.smart_contact_manager.entity.response.ContactResponse;
import com.scm.smart_contact_manager.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/scm")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(maxAge = 3600)
public class SCMController {

    private final ContactService contactService;

    @PostMapping("/{userId}")
    public ResponseEntity<ContactResponse> saveContact(@Valid @RequestBody ContactRequest contactRequest, @PathVariable("userId") String userId) {
        return ResponseEntity.ok(contactService.saveContact(contactRequest, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ContactListResponse> getContactList(@PathVariable("userId") String userId,
                                                              @RequestParam(name = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                              @RequestParam(name = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        return ResponseEntity.ok(contactService.getContactsList(userId, pageNo, pageSize));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable("contactId") String contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @GetMapping("/detail/{contactId}")
    public ResponseEntity<ContactResponse> getContactDetail(@PathVariable("contactId") String contactId) {
        return ResponseEntity.ok(contactService.getContactDetail(contactId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ContactResponse> editCustomerDetails(@PathVariable("userId") String userId, @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(contactService.saveContact(contactRequest, userId));
    }

    @PatchMapping("/favourite/add/{contactId}")
    public ResponseEntity<ContactResponse> addToFavourite(@PathVariable("contactId") String contactId) {
        return ResponseEntity.ok(contactService.toggleFavourite(contactId, Boolean.TRUE));
    }

    @PatchMapping("/favourite/remove/{contactId}")
    public ResponseEntity<ContactResponse> removeFromFavourite(@PathVariable("contactId") String contactId) {
        return ResponseEntity.ok(contactService.toggleFavourite(contactId, Boolean.FALSE));
    }

    @GetMapping("/favourite/{userId}")
    public ResponseEntity<ContactListResponse> getFavouriteContactList(@PathVariable("userId") String userId,
                                                                       @RequestParam(name = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                                       @RequestParam(name = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        return ResponseEntity.ok(contactService.getFavouriteContactsList(userId, pageNo, pageSize));
    }


}
