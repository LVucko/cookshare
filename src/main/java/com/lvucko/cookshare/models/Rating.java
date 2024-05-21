package com.lvucko.cookshare.models;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private long id;
    private long userId;
    private long recipeId;
    private long rating;
}
