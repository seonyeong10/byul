package com.byul.web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuListRequestDto {

    private int page;

    private String sort = "default";

}
