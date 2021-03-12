package com.matheusfelixr.scm.repository;

import com.matheusfelixr.scm.model.domain.HistoryResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryResetPasswordRepository extends JpaRepository<HistoryResetPassword, Long>  {

}
