package com.example.atp_back.portfolio.repository;

import com.example.atp_back.portfolio.model.entity.Acquisition;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
    @Query("SELECT a FROM Acquisition a JOIN FETCH a.stock WHERE a.idx = :idx")
    Optional<Acquisition> findWithStockById(@Param("idx") Long idx);

    @Query("SELECT a.portfolio.idx, s.name, a.price, a.quantity " +
            "FROM Acquisition a " +
            "JOIN a.stock s " +
            "GROUP BY a.portfolio.idx, s.name")
    List<Acquisition> findAcquisitionSummary();
}