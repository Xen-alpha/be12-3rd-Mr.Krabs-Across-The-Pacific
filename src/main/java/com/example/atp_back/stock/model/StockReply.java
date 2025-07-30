package com.example.atp_back.stock.model;

import com.example.atp_back.common.BaseReply;
import com.example.atp_back.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockReply extends BaseReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="stock_id", nullable=false)
    private Stock stock;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ColumnDefault("0")
    @Column(nullable=false)
    @Builder.Default
    private Integer likesCount=0;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "reply")
    private List<StockReplyLikes> likes;

    public void addLikesCount() {
        this.likesCount++;
    }

    public void subLikesCount() {
        this.likesCount--;
    }
}
