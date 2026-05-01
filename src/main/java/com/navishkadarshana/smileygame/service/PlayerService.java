package com.navishkadarshana.smileygame.service;

import com.navishkadarshana.smileygame.dto.player.PlayerReqDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 15/02/2024 - 08.50
 */
public interface PlayerService {
    void saveNewPlayer(PlayerReqDto playerReqDto);

    void verifyAccountAndEmail(String token);

}
