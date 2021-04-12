package com.matheusfelixr.scm.service;

import com.matheusfelixr.scm.model.domain.City;
import com.matheusfelixr.scm.model.domain.State;
import com.matheusfelixr.scm.model.dto.ibge.CityIbgeDTO;
import com.matheusfelixr.scm.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    public City save(City city) {
        return this.cityRepository.save(city);
    }

    public List<City> saveAll(List<City> cityList) {
        return this.cityRepository.saveAll(cityList);
    }

    public List<City> getAllCityInIbgeApiByState() throws Exception {
        List<City> ret = new ArrayList<>();
        List<State> states = stateService.findAll();
        for (State state : states) {
            try {

                List<String> urls = new ArrayList<>();
                urls.add("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + state.getIdIbge() + "/municipios");
                urls.add("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + state.getIdIbge() + "/distritos");


                for (String url : urls) {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<CityIbgeDTO[]> response = restTemplate.getForEntity(url, CityIbgeDTO[].class);

                    CityIbgeDTO[] CityIbgeDTOs = response.getBody();

                    for (CityIbgeDTO cityIbgeDTO : CityIbgeDTOs) {
                        cityIbgeDTO.setState(state);
                        if(!ret.contains(cityIbgeDTO)){
                            ret.add(CityIbgeDTO.convertToEntity(cityIbgeDTO));
                        }

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return this.saveAll(ret);
    }
}
