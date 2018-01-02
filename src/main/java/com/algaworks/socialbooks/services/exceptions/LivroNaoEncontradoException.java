package com.algaworks.socialbooks.services.exceptions;

// RuntimeException = unchecked exception
public class LivroNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1869300553614629710L;
	
	public LivroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	// Throwable = superclasse de todas os erros/exceções do java
	public LivroNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
