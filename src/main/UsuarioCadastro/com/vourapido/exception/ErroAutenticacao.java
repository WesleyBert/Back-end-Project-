package com.vourapido.exception;

public class ErroAutenticacao extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ErroAutenticacao(String menssage) {
		super(menssage);
	}
}