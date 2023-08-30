package com.byul.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    private String name;

    private String engName;

    @Builder
    public Category(Long id, Category parent, List<Category> children, String name, String engName) {
        this.id = id;
        this.parent = parent;
        this.children = children;
        this.name = name;
        this.engName = engName;
    }

    //==연관관계 메서드==//
    /**
     * 상위 카테고리를 저장한다.
     */
    public void addParent (Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }
}
