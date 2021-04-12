package com.matheusfelixr.scm.repository;

import com.matheusfelixr.scm.model.domain.City;
import com.matheusfelixr.scm.model.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
