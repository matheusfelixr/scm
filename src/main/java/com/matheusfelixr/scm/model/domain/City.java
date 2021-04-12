package com.matheusfelixr.scm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "CITY" )
@SequenceGenerator(name = "SEQ_CITY", sequenceName = "SEQ_CITY", allocationSize = 1)
public class City implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITY")
    private Long id;

    @Column(name = "ID_IBGE", nullable = false, unique = true)
    private Long idIbge;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STATE", referencedColumnName="ID", nullable=false)
    private State state;

}
