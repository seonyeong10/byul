package com.byul.web.dto.response;

import com.byul.domain.order.Order;
import com.byul.domain.order.OrderItem;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class OrderHistoryResDto {

    private String[] status;

    private List<OrderItemResDto> orderItems = new ArrayList<>();

    private String createdDate; //주문 일자

    private String acceptedDate; //주문 수령일자

    private String finishedDate; //준비완료 일자

    private String modifiedDate; //픽업일자

    private int totalCount;

    private int totalPrice;

    public OrderHistoryResDto(Order entity) {
        if(entity.getStatus() != null) {
            status = new String[]{entity.getStatus().getKey(), entity.getStatus().getValue()};
        }
        entity.getOrderItems().forEach(item -> addOrderItem(item));
        createdDate = parseString(entity.getCreatedDate());
        acceptedDate = parseString(entity.getAcceptedDate());
        finishedDate = parseString(entity.getFinishedDate());
        modifiedDate = parseString(entity.getModifiedDate());
        totalCount = entity.getTotalCount();
        totalPrice = entity.getTotalPrice();
    }

    /**
     * orderItem 을 추가한다.
     */
    public void addOrderItem(OrderItem item) {
        orderItems.add(new OrderItemResDto(item));
    }

    /**
     * LocalDateTime 을 String 으로 변환한다.
     * @param dateTime
     * @return yyyy-MM-dd HH:mi 형태의 날짜 문자열
     */
    public String parseString(LocalDateTime dateTime) {
        if(dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
