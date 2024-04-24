package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDetailsDto {
    private long id;
    private long userId;
    private long username;
    private String title;
    private String shortDescription;
    private String longDescription;
    private List<String> categories;
    private List<String> pathToPictures;
}
