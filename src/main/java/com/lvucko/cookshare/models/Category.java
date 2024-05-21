package com.lvucko.cookshare.models;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private long id;
    private String name;
}
