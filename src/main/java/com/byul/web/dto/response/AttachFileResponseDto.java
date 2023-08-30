package com.byul.web.dto.response;

import com.byul.domain.AttachFile;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class AttachFileResponseDto {

    private Long id;

    private String name;

    private String dir;

    public AttachFileResponseDto(AttachFile entity) {
        id = entity.getId();
        name = entity.getName();
        dir = entity.getDir();
    }

}
