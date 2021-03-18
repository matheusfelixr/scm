package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.Company;
import com.matheusfelixr.scm.model.domain.HistoryAuthentication;
import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.repository.CompanyRepository;
import com.matheusfelixr.scm.repository.HistoryAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company company){
        return companyRepository.save(company);
    }

    public List<Company> saveAll(List<Company> companies){
        return companyRepository.saveAll(companies);
    }
}
