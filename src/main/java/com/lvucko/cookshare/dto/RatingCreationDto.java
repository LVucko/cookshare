package com.lvucko.cookshare.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCreationDto {
    private long userId;
    private long recipeId;
    private long rating;
}
