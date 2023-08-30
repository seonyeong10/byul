package com.byul.web.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartRequestDto {

    private List<Long> cartIds = new ArrayList<>();

}
