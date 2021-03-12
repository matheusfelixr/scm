package com.matheusfelixr.scm.controller;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.model.dto.MessageDTO;
import com.matheusfelixr.scm.model.dto.captureMailing.CaptureMailingByExampleRequestDTO;
import com.matheusfelixr.scm.model.dto.config.ResponseApi;
import com.matheusfelixr.scm.service.CaptureMailingService;
import com.matheusfelixr.scm.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/capture-mailing")
public class CaptureMailingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaptureMailingController.class);

	@Autowired
	private SecurityService securityService;

	@Autowired
	private CaptureMailingService captureMailingService;

	@PostMapping(value  = "/by-example")
	public ResponseEntity<ResponseApi<MessageDTO>> captureMailingByExample(@RequestBody CaptureMailingByExampleRequestDTO captureMailingByExampleRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("Inicio processo de captura de mailing para o exemplo: "+ captureMailingByExampleRequestDTO.getExample());
		ResponseApi<MessageDTO> response = new ResponseApi<>();
		try {
			UserAuthentication currentUser = securityService.getCurrentUser();

			response.setData(this.captureMailingService.captureMailingByExample(captureMailingByExampleRequestDTO.getExample(),currentUser));
			LOGGER.info("Sucesso no processo de cpatura de mailing para o exemplo: "+ captureMailingByExampleRequestDTO.getExample());
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
}