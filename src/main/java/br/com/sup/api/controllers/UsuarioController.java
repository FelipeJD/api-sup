package br.com.sup.api.controllers;

import br.com.sup.api.exception.RestNotFoundException;
import br.com.sup.api.models.Credencial;
import br.com.sup.api.models.Empresa;
import br.com.sup.api.models.Usuario;
import br.com.sup.api.repository.UsuarioRepository;
import br.com.sup.api.service.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/usuarios")
public class UsuarioController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<EntityModel<Usuario>> register(@RequestBody @Valid Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario.toEntityModel());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial) {
        manager.authenticate(credencial.toAuthentication());
        var user = usuarioRepository.findByEmail(credencial.email()).orElseThrow();
        var token = tokenService.generateToken(credencial, user.getId());
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public List<Usuario> listAll(@RequestParam(required = false) String nome) {
        if (nome == null) {
            return usuarioRepository.findAll();
        } else {
            return usuarioRepository.findByNomeContaining(nome);
        }
    }

    @GetMapping("{id}")
    public EntityModel<Usuario> getById(@PathVariable Long id) {
        logger.info("Buscando usuario com id: " + id );
        return getUsuario(id).toEntityModel();
    }

    @PostMapping("/addCompany/{userId}")
    public ResponseEntity<Empresa> addCompany(@PathVariable Long userId, @RequestBody @Valid Empresa empresa) {
        try {
            // Update the user and return the updated user object
            logger.info("Adicionando uma empresa no usuario Id: " + userId);
            Usuario user = getUsuario(userId);
            empresa.setUsuario(user);
            user.addEmpresa(empresa);
            usuarioRepository.save(user);

            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            // Handle the exception and send an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        try {
            // Update the user and return the updated user object
            logger.info("alterando usuario com id " + id);
            getUsuario(id);
            usuario.setId(id);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            // Handle the exception and send an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        logger.info("apagando usuario com id " + id);
        usuarioRepository.delete(getUsuario(id));
        return ResponseEntity.noContent().build();
    }

    private Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RestNotFoundException("Usuario nao encontrado"));
    }
}
