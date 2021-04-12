package com.matheusfelixr.scm.controller;


import com.matheusfelixr.scm.model.dto.captureMailing.ResponseCaptureMailingByExampleDto;
import com.matheusfelixr.scm.model.dto.config.ResponseApi;
import com.matheusfelixr.scm.service.CaptureMailingService;
import com.matheusfelixr.scm.service.CityService;
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
@RequestMapping(path = "/city")
public class CityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	@GetMapping(value  = "/get-all-City")
	public ResponseEntity<ResponseApi<String>> getAllCityByStates(HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("Buscando todas as cidades por estados");
		ResponseApi<String> response = new ResponseApi<>();
		try {
			this.cityService.getAllCityInIbgeApiByState();
			response.setData("opaopa");
			LOGGER.info("Sucesso no processo de buscar todas as cidades por estados");
			return ResponseEntity.ok(response);
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
			response.setErrors(Arrays.asList(e.getMessage()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Erro inesperado ao buscar todas as cidades por estados");
			List<String> errors = Arrays.asList("Erro inesperado ao buscar todas as cidades por estados");
			response.setErrors(errors);
			return ResponseEntity.ok(response);
		}
	}
}