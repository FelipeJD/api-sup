package br.com.sup.api.models;

import br.com.sup.api.controllers.EmpresaController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sup_tb_empresa")
public class Empresa {

    @Column(name = "id_empresa")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_empresa")
    @NotBlank
    private String nome;

    @Column(name = "ds_email")
    @NotBlank
    private String email;

    @Column(name = "ds_cargo", length = 80)
    private String cargo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public void updateInfo(EmpresaDto empresaDto) {
        if (empresaDto.nome() != null) {
            this.nome = empresaDto.nome();
        }
        if (empresaDto.email() != null) {
            this.email = empresaDto.email();
        }
        if (empresaDto.cargo() != null) {
            this.cargo = empresaDto.cargo();
        }
    }

    public EntityModel<Empresa> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(EmpresaController.class).listAll(null)).withRel("all"),
                linkTo(methodOn(EmpresaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(EmpresaController.class).delete(id)).withRel("delete")
        );
    }

}
