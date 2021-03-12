package com.matheusfelixr.scm.model.dto.dataControl;

import com.matheusfelixr.scm.model.domain.DataControlImpl;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DataControlDTO {

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

;


    public static DataControlImpl convertToEntity(DataControlDTO dto) {
        DataControlImpl ret = new DataControlImpl();
        ret.setCreateDate(dto.getCreateDate());
        ret.setUpdateDate(dto.getUpdateDate());
        return ret;
    }

    public static DataControlDTO convertToDTO(DataControlImpl entity) {
        DataControlDTO ret = new DataControlDTO();
        ret.setCreateDate(entity.getCreateDate());
        ret.setCreateUser(entity.getCreateUser().getUserName());
        ret.setUpdateDate(entity.getUpdateDate());
        ret.setUpdateUser(entity.getUpdateUser().getUserName());
        return ret;
    }

    public static List<DataControlDTO> convertToListDTO(List<DataControlImpl> entitys) {
        List<DataControlDTO> ret = new ArrayList<DataControlDTO>();
        for (DataControlImpl entity : entitys) {
            ret.add(DataControlDTO.convertToDTO(entity));
        }
        return ret;
    }

    public static List<DataControlImpl> convertToListEntity(List<DataControlDTO> DTOs) {
        List<DataControlImpl> ret = new ArrayList<DataControlImpl>();
        for (DataControlDTO dto : DTOs) {
            ret.add(DataControlDTO.convertToEntity(dto));
        }
        return ret;
    }

}
