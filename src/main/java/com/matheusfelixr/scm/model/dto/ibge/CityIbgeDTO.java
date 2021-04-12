package com.matheusfelixr.scm.model.dto.ibge;

import com.matheusfelixr.scm.model.domain.City;
import com.matheusfelixr.scm.model.domain.State;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CityIbgeDTO {

    private Long id;

    private String nome;

    private State state;

    public static City convertToEntity(CityIbgeDTO cityIbgeDTO){
       City ret = new City();
        ret.setIdIbge(cityIbgeDTO.getId());
        ret.setName(cityIbgeDTO.getNome());
        ret.setState(cityIbgeDTO.getState());
        return ret;
    }
}
