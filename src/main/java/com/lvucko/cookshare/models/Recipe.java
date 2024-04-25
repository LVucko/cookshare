package com.lvucko.cookshare.models;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    private long id;
    private long userId;
    private Date creationDate;
    private String title;
    private String shortDescription;
    private String longDescription;
}
