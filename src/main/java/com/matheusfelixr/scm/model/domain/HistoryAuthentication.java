package com.matheusfelixr.scm.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "HISTORY_AUTHENTICATION" )
@SequenceGenerator(name = "SEQ_HISTORY_AUTHENTICATION", sequenceName = "SEQ_HISTORY_AUTHENTICATION", allocationSize = 1)
public class HistoryAuthentication implements Serializable {

    private static final long serialVersionUID = -1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORY_AUTHENTICATION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_AUTHENTICATION", referencedColumnName = "ID")
    private UserAuthentication userAuthentication;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "IP")
    private String ip;

    @Column(name = "OBSERVATION")
    private String observation;

    @Column(name = "USER_NAME")
    private String UserName;


}
