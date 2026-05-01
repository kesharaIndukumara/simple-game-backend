package com.navishkadarshana.smileygame.dto.game;

import com.navishkadarshana.smileygame.enums.common.GameEndType;
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
@ToString
public class GameEndReqDto {
    private Long score_id;
    private Long score_details_id;
    private GameEndType end_type;
}
