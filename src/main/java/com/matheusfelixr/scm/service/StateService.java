package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.State;
import com.matheusfelixr.scm.repository.StateRepository;
import com.matheusfelixr.scm.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;
    
    public State save(State state){
        return this.stateRepository.save(state);
    }

    public List<State> saveAll(List<State> stateList){
        return this.stateRepository.saveAll(stateList);
    }

    public List<State> findAll(){
        return this.stateRepository.findAll();
    }
}
