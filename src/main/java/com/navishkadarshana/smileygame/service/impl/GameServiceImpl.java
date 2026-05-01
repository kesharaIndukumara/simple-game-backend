package com.navishkadarshana.smileygame.service.impl;


import com.navishkadarshana.smileygame.dto.game.*;
import com.navishkadarshana.smileygame.entity.Player;
import com.navishkadarshana.smileygame.entity.Score;
import com.navishkadarshana.smileygame.entity.ScoreDetail;
import com.navishkadarshana.smileygame.enums.common.GameEndType;
import com.navishkadarshana.smileygame.enums.common.Level;
import com.navishkadarshana.smileygame.exception.dto.CustomServiceException;
import com.navishkadarshana.smileygame.repository.PlayerRepository;
import com.navishkadarshana.smileygame.repository.ScoreDetailRepository;
import com.navishkadarshana.smileygame.repository.ScoreRepository;
import com.navishkadarshana.smileygame.service.GameService;
import com.navishkadarshana.smileygame.utilities.SmileyAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 15/02/2024 - 10.09
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final PlayerRepository playerRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreDetailRepository scoreDetailRepository;
    private final SmileyAPI smileyAPI;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameDetailsResDto startGame(Long player_id) {
        Player player = playerRepository.findById(player_id).orElseThrow(() -> new CustomServiceException("User Not Found!"));
        Score score = Score.builder()
                .player(player)
                .start_date_time(new Date())
                .point(0.0)
                .spend_time(0.0)
                .build();
        Score savedScore = scoreRepository.save(score);

        GameResDto resDto = smileyAPI.getNewQuestion();
        resDto.setScore_id(savedScore.getId());

        ScoreDetail scoreDetail = ScoreDetail.builder()
                .solution(resDto.getSolution())
                .question_link(resDto.getQuestion())
                .score(score)
                .answer(0.0)
                .is_correct(false)
                .build();

        ScoreDetail saveScoreDetails = scoreDetailRepository.save(scoreDetail);
        return new GameDetailsResDto(savedScore.getId(), saveScoreDetails.getId(), resDto.getQuestion());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameEndResDto endGame(Long player_id, GameEndReqDto dto) {
        Score score = scoreRepository.findByIdAndPlayerId(dto.getScore_id(), player_id).orElseThrow(() -> new CustomServiceException("Cannot find a record!"));
        score.setEnd_date_time(new Date());
        score.setGame_end_type(dto.getEnd_type());
        scoreRepository.save(score);
        return new GameEndResDto(score.getId(), score.getPoint());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameAnswerCheckResDto checkAnswer(Long player_id, GameAnswerCheckReqDto dto) {
        Score score = scoreRepository.findByIdAndPlayerId(dto.getScore_id(), player_id).orElseThrow(() -> new CustomServiceException("Cannot find a record!"));
        ScoreDetail scoreDetail = scoreDetailRepository.findById(dto.getScore_details_id()).orElseThrow(() -> new CustomServiceException("Cannot find a record!"));
        scoreDetail.setAnswer(dto.getAnswer());
        boolean isTrue = false;

        if (dto.getAnswer()==scoreDetail.getSolution()){
            score.setPoint(score.getPoint()+10);
            isTrue=true;
            Player player = playerRepository.findById(player_id).orElseThrow(() -> new CustomServiceException("Cannot find a user!"));
            player.setLevel(player.getLevel()+10);
            if (player.getLevel()>10000){
                player.setLevel_eum(Level.Gold);
            }else if (player.getLevel()>1000){
                player.setLevel_eum(Level.Platinum);
            }
            playerRepository.save(player);
        }

        scoreDetail.set_correct(isTrue);
        scoreDetail.setUpdatedTimestamp(new Date());
        scoreDetailRepository.save(scoreDetail);
        scoreRepository.save(score);


        GameResDto resDto = smileyAPI.getNewQuestion();

        ScoreDetail newScoreDetail = ScoreDetail.builder()
                .solution(resDto.getSolution())
                .score(score)
                .question_link(resDto.getQuestion())
                .answer(0.0)
                .is_correct(false)
                .build();

        ScoreDetail saveNewScoreDetails = scoreDetailRepository.save(newScoreDetail);


        return  GameAnswerCheckResDto.builder()
                .question(resDto.getQuestion())
                .score_details_id(saveNewScoreDetails.getId())
                .score_id(score.getId())
                .point(score.getPoint())
                .is_true(isTrue)
                .build();
    }

    @Override
    public List<TopScoreResDto> getTopScore() {
        List<Score> allScore = scoreRepository.getAllScore();
        return allScore.stream()
                .map(score -> {
                 return TopScoreResDto.builder()
                         .id(score.getId())
                         .userName(score.getPlayer().getUserName())
                         .point(score.getPoint())
                         .date(score.getStart_date_time())
                         .level_eum(score.getPlayer().getLevel_eum())
                         .build();
                }).collect(Collectors.toList());
    }


}
