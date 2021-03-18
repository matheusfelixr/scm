package com.matheusfelixr.scm.repository;

import com.matheusfelixr.scm.model.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>  {

}
