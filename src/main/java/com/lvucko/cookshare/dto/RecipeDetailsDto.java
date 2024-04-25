package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDetailsDto {
    private long id;
    private long userId;
    private String username;
    private Date creationDate;
    private String title;
    private String shortDescription;
    private String longDescription;
    private List<String> categories;
    private List<String> pathToPictures;
}
