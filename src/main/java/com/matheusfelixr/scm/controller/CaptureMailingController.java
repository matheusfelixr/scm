package com.matheusfelixr.scm.controller;

import com.matheusfelixr.scm.model.domain.UserAuthentication;
import com.matheusfelixr.scm.model.dto.MessageDTO;
import com.matheusfelixr.scm.model.dto.captureMailing.CaptureMailingByNameCityRequestDTO;
import com.matheusfelixr.scm.model.dto.config.ResponseApi;
import com.matheusfelixr.scm.model.dto.security.*;
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

	@PostMapping(value  = "/by-name-city")
	public ResponseEntity<ResponseApi<MessageDTO>> captureMailingByNameCity(@RequestBody CaptureMailingByNameCityRequestDTO captureMailingByNameCityRequestDTO, HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.debug("Inicio processo de autenticacao.");
		ResponseApi<MessageDTO> response = new ResponseApi<>();
		try {
			UserAuthentication currentUser = securityService.getCurrentUser();

			response.setData(this.captureMailingService.captureMailingByNameCity(captureMailingByNameCityRequestDTO.getNameCity(),currentUser));
			LOGGER.debug("Autenticacao realizada com sucesso.");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao tentar autenticar");
			List<String> errors = Arrays.asList("Erro inesperado ao tentar autenticar");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}
}