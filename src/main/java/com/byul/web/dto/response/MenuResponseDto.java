package com.byul.web.dto.response;

import com.byul.domain.item.Drink;
import com.byul.domain.item.Food;
import com.byul.domain.item.MenuDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter @ToString
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;

    private CategoryResponseDto category;

    private String name;

    private String engName;

    private String temp;

    private int price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<AttachFileResponseDto> attachFiles = new ArrayList<>();

    private String etc;

    private List<MenuDetailResponseDto> menuDetail = new ArrayList<>();

    public MenuResponseDto(Drink entity) {
        id = entity.getId();
        category = new CategoryResponseDto(entity.getCategory());
        name = entity.getName();
        engName = entity.getEngName();
        temp = entity.getTemp();
        price = entity.getPrice();
        startDate = entity.getPeriod().getStartDate();
        endDate = entity.getPeriod().getEndDate();
        attachFiles = entity.getAttachFiles().stream()
                .map(AttachFileResponseDto::new)
                .toList();
        etc = entity.getEtc();
        menuDetail = sort(entity.getMenuDetails());
    }

    public MenuResponseDto(Food entity) {
        id = entity.getId();
        category = new CategoryResponseDto(entity.getCategory());
        name = entity.getName();
        engName = entity.getEngName();
        price = entity.getPrice();
        startDate = entity.getPeriod().getStartDate();
        endDate = entity.getPeriod().getEndDate();
        attachFiles = entity.getAttachFiles().stream()
                .map(AttachFileResponseDto::new)
                .toList();
        etc = entity.getEtc();
        menuDetail = sort(entity.getMenuDetails());
    }

    /**
     * MenuDetail 을 용량을 기준으로 오름차순으로 정렬한다.
     * @param entity
     * @return
     */
    public List<MenuDetailResponseDto> sort(List<MenuDetail> entity) {
        List<MenuDetailResponseDto> responseDtos = entity.stream()
                .map(MenuDetailResponseDto::new)
                .collect(Collectors.toList());

        Collections.sort(responseDtos);

        return responseDtos;
    }

}
