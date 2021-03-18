package com.matheusfelixr.scm.controller;


import com.matheusfelixr.scm.model.dto.captureMailing.ResponseCaptureMailingByExampleDto;
import com.matheusfelixr.scm.model.dto.config.ResponseApi;
import com.matheusfelixr.scm.service.CaptureMailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/capture-mailing")
public class CaptureMailingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptureMailingController.class);

	@Autowired
	private CaptureMailingService captureMailingService;

	@GetMapping(value  = "/by-example/{example}")
	public ResponseEntity<ResponseApi<ResponseCaptureMailingByExampleDto>> captureMailingByExample(@PathVariable(value = "example") String example, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("Inicio processo de captura de mailing para o exemplo: "+ example);
		ResponseApi<ResponseCaptureMailingByExampleDto> response = new ResponseApi<>();
		try {
			response.setData(this.captureMailingService.captureMailingByExample(example));
			LOGGER.info("Sucesso no processo de cpatura de mailing para o exemplo: "+ example);
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao capturar mailing");
			List<String> errors = Arrays.asList("Erro inesperado ao capturar mailing");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping(value="/download/{fileName}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		try {
			File file = new File("csv/"+ fileName);

			if(!file.exists()){
				throw new ValidationException("Nome de arquivo não encontrado.");
			}

			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

			return ResponseEntity.ok()
					.header("Content-Disposition", "inline; filename=" + fileName)
					.contentType(MediaType.APPLICATION_CBOR)
					.body(resource.getByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}