package com.example.atp_back.common;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
public class BaseReply extends BaseEntity {
    @Column(nullable = false, length = 10000)
    private String contents;
}
