package com.example.atp_back.portfolio;

import com.example.atp_back.portfolio.model.entity.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
}
