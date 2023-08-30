package com.byul.domain.staff;

import com.byul.domain.AttachFile;
import com.byul.domain.BaseTimeEntity;
import com.byul.domain.Period;
import com.byul.domain.Role;
import com.byul.domain.converter.PositionConverter;
import com.byul.domain.converter.ResponsibilityConverter;
import com.byul.domain.converter.RoleConverter;
import com.byul.domain.converter.WorkConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(name = "staff_id", nullable = false, length = 10)
    private String id; // 사번

    private String name; // 이름

    private String engName; // 영문이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team; // 소속 팀

    @Convert(converter = PositionConverter.class)
    private Position position; // 직급

    @Convert(converter = ResponsibilityConverter.class)
    private Responsibility res; // 직책

    @Convert(converter = WorkConverter.class)
    private Work work;

    private String extension;

    @Embedded
    private Period period; // 근무 기간

    @Convert(converter = RoleConverter.class)
    private Role role;

    private String password;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachFile> attachFiles = new ArrayList<>();

    @Builder
    public Staff(String id, String name, String engName, Team team, Position position, Responsibility res, Work work, String extension, Period period, Role role, String password) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.team = team;
        this.position = position;
        this.res = res;
        this.work = work;
        this.extension = extension;
        this.period = period;
        this.role = role;
        this.password = password;
    }

    //==Persistable 상속 메서드==//
    @Override
    public boolean isNew() {
        // 등록일자가 null 이 아니면 insert
        return getCreatedDate() == null;
    }
}
