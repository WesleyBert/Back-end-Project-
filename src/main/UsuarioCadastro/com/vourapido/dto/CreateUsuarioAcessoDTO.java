package com.vourapido.dto;


	import java.util.List; 
	import lombok.Getter;
	import lombok.Setter;

	@Getter
	@Setter
	public class CreateUsuarioAcessoDTO {

	  public Long idUser;

	  public List<Long> idsAcesso;
}
