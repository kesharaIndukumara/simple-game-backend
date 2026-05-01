package com.navishkadarshana.smileygame.controller.PlayerController;

import com.navishkadarshana.smileygame.config.security.custom.CustomUserAuthenticator;
import com.navishkadarshana.smileygame.dto.common.CommonResponse;
import com.navishkadarshana.smileygame.dto.game.*;

import com.navishkadarshana.smileygame.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.navishkadarshana.smileygame.constants.AppConstants.DetailConstants.HEADER_AUTH;


/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/01/2024 - 14.08
 */

@RestController
@RequestMapping(value = "/game")
@RequiredArgsConstructor
@Log4j2
public class GameController {

    private final GameService gameService;

    @PostMapping(value = "/start")
    public ResponseEntity<?> startGame(@RequestHeader(value = HEADER_AUTH) String token) {

        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        GameDetailsResDto resDto = gameService.startGame(user_id);
        return ResponseEntity.ok(new CommonResponse<>(true, "Game started", resDto));
    }


    @PostMapping(value = "/end")
    public ResponseEntity<?> endGame(@RequestHeader(value = HEADER_AUTH, required = true) String token, @RequestBody GameEndReqDto dto) {

        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        GameEndResDto resDto = gameService.endGame(user_id, dto);
        return ResponseEntity.ok(new CommonResponse<>(true, resDto));
    }


    @PostMapping(value = "/answer/check")
    public ResponseEntity<?> checkGameAnswer(@RequestHeader(value = HEADER_AUTH, required = true) String token, @RequestBody GameAnswerCheckReqDto dto) {

        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        GameAnswerCheckResDto resDto = gameService.checkAnswer(user_id, dto);
        return ResponseEntity.ok(new CommonResponse<>(true, resDto));
    }


    @GetMapping(value = "/top-score")
    public ResponseEntity<?> getTopScore(@RequestHeader(value = HEADER_AUTH, required = true) String token) {

        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        List<TopScoreResDto> topScore = gameService.getTopScore();
        return ResponseEntity.ok(new CommonResponse<>(true, topScore));
    }

}
