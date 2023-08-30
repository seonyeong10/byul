package com.byul.domain;

import com.byul.domain.item.Item;
import com.byul.domain.staff.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_file_id")
    private Long id;

    private String name;

    private String orgName;

    private String dir;

    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public AttachFile(Long id, String name, String orgName, String dir, int seq) {
        this.id = id;
        this.name = name;
        this.orgName = orgName;
        this.dir = dir;
        this.seq = seq;
    }

    //==연관관계 메서드==//

    /**
     * 직원을 저장한다.
     */
    public void addStaff (Staff staff) {
        this.staff = staff;
        staff.getAttachFiles().add(this);
    }

    /**
     * 상품을 저장한다.
     */
    public void addItem (Item item) {
        this.item = item;
        item.getAttachFiles().add(this);
    }
}
