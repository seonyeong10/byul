package com.byul.config.enums;

import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.NoSuchElementException;

@NoArgsConstructor
public final class EnumUtils {
    /**
     * enum 의 key 값을 DB에 저장한다.
     */
    public static <T extends Enum<T> & EnumField> String toDBCode(T enumClass) {
        return enumClass == null ? "" : enumClass.getKey();
    }

    /**
     * DB 코드를 enum 으로 변환한다.
     */
    public static <T extends Enum<T> & EnumField> T toEntityCode(Class<T> enumClass, String dbCode) {
        if(dbCode.isEmpty() || dbCode == null) {
            return null;
        }

        return EnumSet.allOf(enumClass).stream()
                .filter(c -> c.getKey().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("enum=[%s], code=[%s]는 존재하지 않는 코드입니다.", enumClass.getName(), dbCode)));
    }
}
