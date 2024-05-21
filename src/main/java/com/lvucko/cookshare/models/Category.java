package com.lvucko.cookshare.models;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    //fali private na poljima
    long id;
    String name;
}
