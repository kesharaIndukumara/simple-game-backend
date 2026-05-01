package com.navishkadarshana.smileygame.service;

import com.navishkadarshana.smileygame.dto.game.*;

import java.util.List;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 15/02/2024 - 10.09
 */
public interface GameService {
    GameDetailsResDto startGame(Long player_id);
    GameEndResDto endGame(Long player_id, GameEndReqDto gameEndType);
    GameAnswerCheckResDto checkAnswer(Long player_id, GameAnswerCheckReqDto gameAnswerCheckReqDto);
    List<TopScoreResDto> getTopScore();
}
