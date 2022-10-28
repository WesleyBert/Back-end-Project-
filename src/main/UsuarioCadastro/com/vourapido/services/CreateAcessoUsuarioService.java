package com.vourapido.services;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vourapido.dto.CreateUsuarioAcessoDTO;
import com.vourapido.model.entity.Acesso;
import com.vourapido.model.entity.Usuario;
import com.vourapido.repositories.UsuarioRepository;

@Service
public class CreateAcessoUsuarioService {

  @Autowired
  UsuarioRepository userRepository;
  
  Usuario usuario;
  
  Acesso acesso;

  public Usuario execute(CreateUsuarioAcessoDTO createUserRoleDTO) {

    Optional<Usuario> userExists = userRepository.findById(createUserRoleDTO.idUser);
    List<Acesso> acesso = new ArrayList<>();

    if (userExists.isEmpty()) {
      throw new Error("Usuario existente!");
    }

    acesso = createUserRoleDTO.getIdsAcesso().stream().map(acessos -> {
        return new Acesso(acessos);
      }).collect(Collectors.toList());

    Usuario user = userExists.get();

    usuario.setAcesso(acesso);

    userRepository.save(usuario);

    return usuario;
  }
}
