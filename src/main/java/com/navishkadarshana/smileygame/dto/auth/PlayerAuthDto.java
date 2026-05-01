package com.navishkadarshana.smileygame.dto.auth;

import com.navishkadarshana.smileygame.dto.player.PlayerResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 19/01/2024 - 19.54
 */

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class PlayerAuthDto extends PlayerResDto implements CommonUserAuth {
}
