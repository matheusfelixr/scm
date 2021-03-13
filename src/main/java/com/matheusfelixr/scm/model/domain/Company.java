package com.matheusfelixr.scm.model.domain;

import lombok.Data;

import javax.persistence.*;

@Data
public class Company {


    private String name;

    private String phone;

    private String fullAddress;

    private String road;

    private String city;

    private String cep;

}
