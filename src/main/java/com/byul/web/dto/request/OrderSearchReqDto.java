package com.byul.web.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Getter
public class OrderSearchReqDto {

    private LocalDateTime startDate;

    private LocalDate endDate;

    @Builder
    public OrderSearchReqDto(String startDate, String endDate) {
        this.startDate = toLocalDateTime(startDate);
        this.endDate = toLocalDate(endDate);
    }

    public static LocalDateTime toLocalDateTime(String date) {
        if(date == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public LocalDate toLocalDate(String date) {
        if(date == null) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
