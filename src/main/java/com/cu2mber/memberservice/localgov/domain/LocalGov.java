package com.cu2mber.memberservice.localgov.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "local_govs")
public class LocalGov {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SMALLINT")
    private Integer localNo;

    @Column(length = 50, nullable = false)
    private String localDistrict;

    @Column(length = 50)
    private String localName;

    private LocalGov(String localDistrict, String localName) {
        this.localDistrict = localDistrict;
        this.localName = localName;
    }

    public static LocalGov ofNewGov(String localDistrict, String localName) {
        return new LocalGov(
                localDistrict,
                localName
        );
    }

    // 매핑 테이블에 "경상남도 창원시" 형태로 저장하기 위한 편의 메서드
    public String getFullLocalName() {
        if (this.localName == null || this.localName.isBlank()) {
            return this.localDistrict;
        }
        return this.localDistrict + " " + this.localName;
    }
}