package com.matheusfelixr.scm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "STATE" )
@SequenceGenerator(name = "SEQ_STATE", sequenceName = "SEQ_STATE", allocationSize = 1)
public class State implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STATE")
    private Long id;

    @Column(name = "ID_IBGE", nullable = false, unique = true)
    private Long idIbge;

    @Column(name = "UF", nullable = false, unique = true, length = 2)
    private String uf;

    @OneToMany( fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="state", orphanRemoval = true)
    private Set<City> city;

}
