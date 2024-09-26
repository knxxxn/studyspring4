package study.data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    @CreatedDate //등록일
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate //수정일
    private LocalDateTime lastModifiedDate;

    @CreatedBy //등록자
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy //수정자
    private String lastModifiedBy;

    //BaseEntity 생성 후의 코드
//    @CreatedBy
//    @Column(updatable = false)
//    private String createdBy;
//    @LastModifiedBy
//    private String lastModifiedBy;
}
