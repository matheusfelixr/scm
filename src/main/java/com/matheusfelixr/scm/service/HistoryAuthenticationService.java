package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.HistoryAuthentication;
import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.repository.HistoryAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class HistoryAuthenticationService {

    @Autowired
    private HistoryAuthenticationRepository historyAuthenticationRepository;

    public HistoryAuthentication generateHistorySucess(UserAuthentication userAuthentication, HttpServletRequest httpServletRequest ){
        HistoryAuthentication historyAuthentication = new HistoryAuthentication();
        historyAuthentication.setUserAuthentication(userAuthentication);
        historyAuthentication.setDate(new Date());
        historyAuthentication.setObservation("Login realizado com sucesso");
        historyAuthentication.setUserName(userAuthentication.getUserName());
        if(httpServletRequest != null){
            historyAuthentication.setIp(httpServletRequest.getRemoteAddr());
        }
        return this.save(historyAuthentication);
    }

    public HistoryAuthentication generateHistoryFail(String userName, HttpServletRequest httpServletRequest, String observation ){
        HistoryAuthentication historyAuthentication = new HistoryAuthentication();
        historyAuthentication.setDate(new Date());
        historyAuthentication.setObservation(observation);
        historyAuthentication.setUserName(userName);
        if(httpServletRequest != null){
            historyAuthentication.setIp(httpServletRequest.getRemoteAddr());
        }
        return this.save(historyAuthentication);
    }


    private HistoryAuthentication save(HistoryAuthentication historyAuthentication){
        return historyAuthenticationRepository.save(historyAuthentication);
    }

}
