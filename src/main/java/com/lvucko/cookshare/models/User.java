package com.lvucko.cookshare.models;


import jdk.jshell.Snippet;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private long id;
    private String username;
    private String email;
    private String password;
    private String realName;
    private Date creationDate;
    private String phone;
    private String pathToPicture;

}
