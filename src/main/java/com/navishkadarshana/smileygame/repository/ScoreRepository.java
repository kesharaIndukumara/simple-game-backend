package com.navishkadarshana.smileygame.repository;

import com.navishkadarshana.smileygame.entity.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByIdAndPlayerId(Long score_id, Long player_id);

    @Query(value = "select * from score ORDER BY point desc", nativeQuery = true)
    List<Score> getAllScore();
}
