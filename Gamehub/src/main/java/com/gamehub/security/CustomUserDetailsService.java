package com.gamehub.security;

import com.gamehub.model.Usuario;
import com.gamehub.repository.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByCorreo(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"));

        return new User(

                usuario.getCorreo(),

                usuario.getPassword(),

                usuario.isActivo(),

                true,
                true,
                true,

                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + usuario.getRol()
                        )
                )
        );
    }
}