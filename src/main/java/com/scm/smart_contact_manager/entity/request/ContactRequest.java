package com.scm.smart_contact_manager.entity.request;

import com.scm.smart_contact_manager.annotation.MobileNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class ContactRequest {

    private String id;

    private String name;
    @NotNull(message = "Mobile Number Should Be Not Null")
    @MobileNumber
    private String mobileNumber;
    @Email(message = "Invalid E-Mail Address")
    private String email;

    private String address;

    private boolean favourite;

}
