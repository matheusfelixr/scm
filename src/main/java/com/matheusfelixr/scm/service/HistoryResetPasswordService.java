package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.repository.HistoryResetPasswordRepository;
import com.matheusfelixr.scm.model.domain.HistoryResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class HistoryResetPasswordService {

    @Autowired
    private HistoryResetPasswordRepository historyResetPasswordRepository;

    public HistoryResetPassword generateHistory(UserAuthentication userAuthentication, HttpServletRequest httpServletRequest ){
        HistoryResetPassword historyResetPassword = new HistoryResetPassword();
        historyResetPassword.setUserAuthentication(userAuthentication);
        historyResetPassword.setDate(new Date());
        if(httpServletRequest != null){
            historyResetPassword.setIp(httpServletRequest.getRemoteAddr());
        }
        return this.save(historyResetPassword);
    }



    private HistoryResetPassword save(HistoryResetPassword historyResetPassword){
        return historyResetPasswordRepository.save(historyResetPassword);
    }

}
