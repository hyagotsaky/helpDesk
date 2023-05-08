package com.helpdesk.resources.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectnotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = localDateTime.format(formatter);

		StandarError error = new StandarError(dataFormatada, HttpStatus.NOT_FOUND.value(), "Object not found",
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandarError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = localDateTime.format(formatter);

		StandarError error = new StandarError(dataFormatada, HttpStatus.BAD_REQUEST.value(), "Violação de dados",
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validationsErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = localDateTime.format(formatter);

		ValidationError errors = new ValidationError(dataFormatada,HttpStatus.BAD_REQUEST.value(),
				"Validation error","Erro na validacao dos campos",request.getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addErros(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandarError> constraintViolationException(ConstraintViolationException ex,
			HttpServletRequest request) {

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = localDateTime.format(formatter);
		
		String mensagemErro = ex.getConstraintViolations().stream().findFirst().get().getMessage();



		StandarError error = new StandarError(dataFormatada, HttpStatus.BAD_REQUEST.value(), "Violação de dados2",
				mensagemErro, request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
