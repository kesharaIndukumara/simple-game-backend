package com.navishkadarshana.smileygame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.navishkadarshana.smileygame.enums.common.AccountVerifyStatus;
import com.navishkadarshana.smileygame.enums.common.GameEndType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 27/02/2024 - 23.02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date_time;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date_time;

    private double point;

    private double spend_time;

    @Enumerated(value = EnumType.STRING)
    private GameEndType game_end_type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Player player;

    @OneToMany(mappedBy = "score", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScoreDetail> scores = new ArrayList<>();
}
