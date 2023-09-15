package com.byul.web.dto;

import com.byul.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemParam {
    private boolean isAdvised; //추천메뉴 찾기
    private boolean isLatest; //신메뉴 찾기
    private int limit; //Top N

    private int page;
    private String sort;
    private Category category;

    @Builder
    public ItemParam(boolean isAdvised, boolean isLatest, Integer limit, int page, String sort, Category category) {
        this.isAdvised = isAdvised;
        this.isLatest = isLatest;
        this.limit = (limit == null) ? 0 : limit;
        this.page = page;
        this.sort = sort;
        this.category = category;
    }

    public String getSort() {
        if (this.sort == null) {
            return "default";
        }
        return this.sort;
    }
}