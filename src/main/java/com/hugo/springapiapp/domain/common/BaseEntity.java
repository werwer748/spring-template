package com.hugo.springapiapp.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 추상 클래스
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass // 해당 클래스를 상속할 경우 이 클래스의 필드들을 컬럼으로 인식하도록 한다.(맵핑정보 제공)
@Getter
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
