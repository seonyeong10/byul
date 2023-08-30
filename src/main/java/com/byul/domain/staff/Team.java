package com.byul.domain.staff;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId; // 상위 부서 id

    private String name;

    private int sort; // 정렬 순서

    @Builder
    public Team(Long id, Long parentId, String name, int sort) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
    }
}
