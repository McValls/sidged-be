package com.mvalls.sidged.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Desertor implements Model {

    private Long id;
    private String text;
    private String language;
    private String code;
}
