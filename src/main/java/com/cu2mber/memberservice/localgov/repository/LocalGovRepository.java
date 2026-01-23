package com.cu2mber.memberservice.localgov.repository;

import com.cu2mber.memberservice.localgov.domain.LocalGov;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalGovRepository extends JpaRepository<LocalGov, Integer>, CustomLocalGovRepository {

}