package com.byul.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ItemParam {
    private boolean isAdvised; //추천메뉴 찾기
    private boolean isLatest; //신메뉴 찾기
}