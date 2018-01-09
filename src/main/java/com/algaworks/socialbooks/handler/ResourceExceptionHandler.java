package com.algaworks.socialbooks.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.socialbooks.domain.DetalhesErro;
import com.algaworks.socialbooks.services.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

// Permite que nós interceptemos todos os tipos de exceções que possam acontecer no nosso código 
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException
	(LivroNaoEncontradoException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(Long.valueOf(HttpStatus.NOT_FOUND.value()));
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("Localização das informações referentes a esse erro.");
		erro.setTimestamp(System.currentTimeMillis());
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	
	@ExceptionHandler(AutorExistenteException.class)
	public ResponseEntity<DetalhesErro> handleAutorExistenteException
	(AutorExistenteException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(Long.valueOf(HttpStatus.CONFLICT.value()));
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("Localização das informações referentes a esse erro.");
		erro.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException
	(AutorNaoEncontradoException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(Long.valueOf(HttpStatus.NOT_FOUND.value()));
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("Localização das informações referentes a esse erro.");
		erro.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException
	(DataIntegrityViolationException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(Long.valueOf(HttpStatus.BAD_REQUEST.value()));
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("Localização das informações referentes a esse erro.");
		erro.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
