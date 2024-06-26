package com.lvucko.cookshare.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeUpdateDto {
    private long id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private List<Long> categories;
    private List<Long> pictureIds;
}
