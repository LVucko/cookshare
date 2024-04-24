package com.lvucko.cookshare.models;

import lombok.*;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    private long id;
    private long userId;
    private String title;
    private String shortDescription;
    private String longDescription;
}
