package com.matheusfelixr.scm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "COMPANY" )
@SequenceGenerator(name = "SEQ_COMPANY", sequenceName = "SEQ_COMPANY", allocationSize = 1)
public class Company  implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPANY")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "FULL_ADDRESS", nullable = true)
    private String fullAddress;

    @Column(name = "ROAD", nullable = true)
    private String road;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "CEP", nullable = true)
    private String cep;

}
