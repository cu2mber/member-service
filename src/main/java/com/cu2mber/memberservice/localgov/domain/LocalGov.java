package com.cu2mber.memberservice.localgov.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지자체(Local Government) 정보를 표현하는 엔티티 클래스입니다.
 * <p>
 * 시/도 단위의 구분 값과
 * 시·군·구 단위의 지자체 이름을 관리하며,
 * 지자체 조회 및 선택 기능에 활용됩니다.
 * </p>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "local_govs")
public class LocalGov {

    /**
     * 지자체 고유 식별자입니다.
     * <p>
     * 데이터베이스에서 자동 증가되는 값이며,
     * 내부 식별 용도로만 사용됩니다.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SMALLINT")
    private Integer localNo;

    /**
     * 지자체가 속한 시/도 또는 광역 단위의 이름입니다.
     */
    @Column(length = 50, nullable = false)
    private String localDistrict;

    /**
     * 시/군/구 단위의 지자체 이름입니다.
     * <p>
     * 광역 단위만 필요한 경우 null이 될 수 있습니다.
     * </p>
     */
    @Column(length = 50)
    private String localName;

    /**
     * LocalGov 엔티티 생성을 위한 내부 생성자입니다.
     * <p>
     * 정적 팩토리 메서드를 통해서만 객체 생성을 허용하여
     * 생성 시점의 의미를 명확히 합니다.
     * </p>
     *
     * @param localDistrict 지자체의 시/도 또는 광역 단위 이름
     * @param localName 시/군/구 단위의 지자체 이름
     */
    private LocalGov(String localDistrict, String localName) {
        this.localDistrict = localDistrict;
        this.localName = localName;
    }

    /**
     * 신규 지자체 생성을 위한 정적 팩토리 메서드입니다.
     * <p>
     * 외부에서 생성 의도를 명확히 표현하기 위해 제공되며,
     * 영속화 이전의 LocalGov 객체를 생성합니다.
     * </p>
     *
     * @param localDistrict 지자체의 시/도 또는 광역 단위 이름
     * @param localName 시/군/구 단위의 지자체 이름
     * @return 새로 생성된 LocalGov 엔티티
     */
    public static LocalGov ofNewGov(String localDistrict, String localName) {
        return new LocalGov(
                localDistrict,
                localName
        );
    }

    /**
     * 지자체의 전체 이름을 반환하는 편의 메서드입니다.
     * <p>
     * 데이터베이스에는 구분된 값으로 저장되지만,
     * 조회 시에는 "경상남도 창원시"와 같은
     * 결합된 형태의 문자열이 필요한 경우를 위해 제공됩니다.
     * </p>
     *
     * @return 광역 단위와 지자체 이름을 결합한 전체 지자체 명
     */
    // 매핑 테이블에 "경상남도 창원시" 형태로 저장하기 위한 편의 메서드
    public String getFullLocalName() {
        if (this.localName == null || this.localName.isBlank()) {
            return this.localDistrict;
        }
        return this.localDistrict + " " + this.localName;
    }
}