package com.lvucko.cookshare.models;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {
    private long id;
    private String pathToPicture;
}
