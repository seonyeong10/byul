package com.byul.domain.item;

import com.byul.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Specialty extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Specialty(Long id, Item item) {
        this.id = id;
        this.item = item;
    }

    //== 연관관계 메서드 ==//
    public void addItem(Item item) {
        this.item = item;
    }
}
