package com.springboot.model;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@Builder
public class User {

    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    private String imageUrl;
    private Boolean emailVerified = false;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    private AuthProvider provider;
    private String providerId;
}
