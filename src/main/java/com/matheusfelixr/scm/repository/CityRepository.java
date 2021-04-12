package com.matheusfelixr.scm.repository;

import com.matheusfelixr.scm.model.domain.City;
import com.matheusfelixr.scm.model.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
