package com.lvucko.cookshare.models;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {
    //fali private na poljima
    long recipeId;
    String pathToPicture;
}
