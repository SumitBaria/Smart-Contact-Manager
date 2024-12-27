package com.scm.smart_contact_manager.entity.request;

import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class AuthenticationRequest {

    private String token;
}
