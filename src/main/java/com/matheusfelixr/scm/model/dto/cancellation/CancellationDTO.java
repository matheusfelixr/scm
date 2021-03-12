package com.matheusfelixr.scm.model.dto.cancellation;

import com.matheusfelixr.scm.model.domain.CancellationImpl;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CancellationDTO {

    private Date cancellationDate;

    private String cancellationObservation;

    private String cancellationUser;


    public static CancellationImpl convertToEntity(CancellationDTO dto) {
        CancellationImpl ret = new CancellationImpl();
        ret.setCancellationDate(dto.getCancellationDate());
        ret.setCancellationObservation(dto.getCancellationObservation());
        return ret;
    }

    public static CancellationDTO convertToDTO(CancellationImpl entity) {
        CancellationDTO ret = new CancellationDTO();
        ret.setCancellationDate(entity.getCancellationDate());
        ret.setCancellationObservation(entity.getCancellationObservation());
        ret.setCancellationUser(entity.getCancellationUser().getUserName());

        return ret;
    }

    public static List<CancellationDTO> convertToListDTO(List<CancellationImpl> entitys) {
        List<CancellationDTO> ret = new ArrayList<CancellationDTO>();
        for (CancellationImpl entity : entitys) {
            ret.add(CancellationDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<CancellationImpl> convertToListEntity(List<CancellationDTO> DTOs) {
        List<CancellationImpl> ret = new ArrayList<CancellationImpl>();
        for (CancellationDTO dto : DTOs) {
            ret.add(CancellationDTO.convertToEntity(dto));
        }
        return ret;
    }
}
