package com.matheusfelixr.scm.repository;

import com.matheusfelixr.scm.model.domain.HistoryAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryAuthenticationRepository extends JpaRepository<HistoryAuthentication, Long>  {

}
