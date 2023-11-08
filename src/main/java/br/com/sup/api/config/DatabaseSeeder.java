package br.com.sup.api.config;

import br.com.sup.api.models.Empresa;
import br.com.sup.api.models.Usuario;
import br.com.sup.api.repository.EmpresaRepository;
import br.com.sup.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public void run(String... args) throws Exception {

        Empresa empresa = Empresa.builder().nome("Acme Corp").email("email").cargo("Software Engineer").build();

        Usuario usuario = Usuario.builder()
                .nome("John Doe")
                .email("johndoe@example.com")
                .senha("password123")
                .build();

        usuarioRepository.save(usuario);

        List<Usuario> usuarios = Arrays.asList(
                Usuario.builder()
                        .nome("Jane Doe")
                        .email("janedoe@example.com")
                        .senha("password123")
                        .build(),
                Usuario.builder()
                        .nome("Peter Smith")
                        .email("petersmith@example.com")
                        .senha("password123")
                        .build()
        );

        usuarioRepository.saveAll(usuarios);
    }

}
