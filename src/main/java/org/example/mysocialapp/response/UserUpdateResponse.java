package org.example.mysocialapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
}
