package br.com.sup.api.models;

import br.com.sup.api.controllers.UsuarioController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sup_tb_usuarios")
@Builder
public class Usuario implements UserDetails {

    @Column(name = "id_usuario")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_usuario", length = 80, nullable = false)
    @NotBlank @Size(max = 80)
    private String nome;

    @Column(name = "ds_email", length = 150, nullable = false)
    @Email
    private String email;

    @Column(name = "ds_senha", length = 255, nullable = false)
    @NotBlank
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empresa> empresas;

    public void addEmpresa(Empresa empresa) {
        empresas.add(empresa);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public EntityModel<Usuario> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(UsuarioController.class).listAll(null)).withRel("all"),
                linkTo(methodOn(UsuarioController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).delete(id)).withRel("delete")
        );
    }
}
