package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public UserAuthentication create(UserAuthentication userAuthentication) throws Exception {

        validateNewUser(userAuthentication);
        String password = userAuthentication.getPassword();
        userAuthentication.setPassword(this.passwordEncoder.encode(userAuthentication.getPassword()));

        UserAuthentication ret = userAuthenticationRepository.save(userAuthentication);
        emailService.newUser(userAuthentication, password);
        return ret;
    }

    public Optional<UserAuthentication> findByUserName(String userName){
        return Optional.ofNullable(userAuthenticationRepository.findByUserNameAndCancellationCancellationDateIsNull(userName));
    }

    public Optional<UserAuthentication> findByEmail(String Email){
        return Optional.ofNullable(userAuthenticationRepository.findByEmailAndCancellationCancellationDateIsNull(Email));
    }

    public UserAuthentication modifyPassword(String userName, String password, Boolean changePassword) throws Exception {
        UserAuthentication userAuthentication = this.validateModifyPassword(userName);
        userAuthentication.setPassword(this.passwordEncoder.encode(password));
        userAuthentication.setChangePassword(changePassword);
        userAuthenticationRepository.save(userAuthentication);
        return userAuthentication;
    }

    private void validateNewUser(UserAuthentication userAuthentication) throws Exception {
        Optional<UserAuthentication> userResName = this.findByUserName(userAuthentication.getUserName());
        if(userResName.isPresent()) {
            throw new ValidationException("Usu??rio j?? existente");
        }

        Optional<UserAuthentication> userResEmail = this.findByEmail(userAuthentication.getEmail());
        if(userResEmail.isPresent()) {
            throw new ValidationException("E-mail j?? cadastrado para outro usu??rio");
        }
    }

    private UserAuthentication validateModifyPassword(String userName) throws Exception {
        Optional<UserAuthentication> userResName = this.findByUserName(userName);
        if(!userResName.isPresent()) {
            throw new ValidationException("Usu??rio n??o encontrado");
        }
        return userResName.get();
    }
}
