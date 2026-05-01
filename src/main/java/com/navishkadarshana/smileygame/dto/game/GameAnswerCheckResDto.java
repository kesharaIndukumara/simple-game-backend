package com.navishkadarshana.smileygame.dto.game;

import lombok.*;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 12/03/2024 - 11.10
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GameAnswerCheckResDto {
    private Long score_id;
    private Long score_details_id;
    private Boolean is_true;
    private double point;
    private String question;
}
