package com.navishkadarshana.smileygame.repository;

import com.navishkadarshana.smileygame.entity.ScoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDetailRepository extends JpaRepository<ScoreDetail, Long> {
}
