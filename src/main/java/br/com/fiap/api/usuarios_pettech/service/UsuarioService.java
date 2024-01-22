package br.com.fiap.api.usuarios_pettech.service;

import br.com.fiap.api.usuarios_pettech.controller.exception.ControllerNotFoundException;
import br.com.fiap.api.usuarios_pettech.dto.UsuarioDTO;
import br.com.fiap.api.usuarios_pettech.entities.Usuario;
import br.com.fiap.api.usuarios_pettech.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Page<UsuarioDTO> findAll(Pageable pageable){
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::toDTO);
    }

    public UsuarioDTO findById(Long id){
        return toDTO(usuarioRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado com o ID: " + id)));
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO){
        return toDTO(usuarioRepository.save(toEntity(usuarioDTO)));
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO){
        try {
            Usuario usuario = usuarioRepository.getReferenceById(id);
            usuario.setNome(usuarioDTO.nome());
            usuario.setEmail(usuarioDTO.email());
            usuario.setCPF(usuarioDTO.CPF());
            usuario.setDataDeNascimento(usuarioDTO.dataDeNascimento());

            return toDTO(usuarioRepository.save(usuario));
        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Usuário não encontrado com o ID: " + id);
        }

        // this code does not work because the method toEntity will create
        // a new user with same atribute as passed in body
        // but with a new ID will.
        // return toDTO(usuarioRepository.save(toEntity(usuarioDTO)));
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario usuario){
        return new UsuarioDTO(usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCPF(),
                usuario.getDataDeNascimento());
    }

    private Usuario toEntity(UsuarioDTO usuarioDTO){
        return new Usuario(
                usuarioDTO.nome(),
                usuarioDTO.email(),
                usuarioDTO.CPF(),
                usuarioDTO.dataDeNascimento());
    }

}
