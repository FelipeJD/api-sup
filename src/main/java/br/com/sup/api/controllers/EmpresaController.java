package br.com.sup.api.controllers;

import br.com.sup.api.exception.RestNotFoundException;
import br.com.sup.api.models.Empresa;
import br.com.sup.api.models.EmpresaDto;
import br.com.sup.api.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    EmpresaRepository empresaRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping
    public List<Empresa> listAll(@RequestParam(required = false) String nome) {
        if (nome == null) {
            return empresaRepository.findAll();
        } else {
            return empresaRepository.findByNomeContaining(nome);
        }
    }

    @GetMapping("{id}")
    public EntityModel<Empresa> getById(@PathVariable Long id) {
        logger.info("Buscando empresa com id: " + id );
        return getEmpresa(id).toEntityModel();
    }

    @PutMapping("{id}")
    public ResponseEntity<Empresa> update(@PathVariable Long id, @RequestBody @Valid EmpresaDto empresaDto){
        logger.info("alterando empresa com id " + id);
        var empresa = getEmpresa(id);
        empresa.updateInfo(empresaDto);
        empresaRepository.save(empresa);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Empresa> delete(@PathVariable Long id){
        logger.info("apagando empresa com id " + id);
        empresaRepository.delete(getEmpresa(id));
        return ResponseEntity.noContent().build();
    }

    private Empresa getEmpresa(Long id) {
        return empresaRepository.findById(id).orElseThrow(() -> new RestNotFoundException("Empresa nao encontrada"));
    }
}
