package com.lvucko.cookshare.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RatingUserRecipeDto {
    private long userId;
    private long recipeId;
}
