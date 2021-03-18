package com.matheusfelixr.scm.model.dto.captureMailing;

import lombok.Data;

@Data
public class ResponseCaptureMailingByExampleDto {

    String nameFile;

    public ResponseCaptureMailingByExampleDto(String nameFile) {
        this.nameFile = nameFile;
    }
}
