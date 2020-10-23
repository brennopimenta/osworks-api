package com.pmgo.osworks.controller;

import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.model.OrdemServico;
import com.pmgo.osworks.domain.repository.ClienteRepository;
import com.pmgo.osworks.domain.repository.OrdemServicoRepository;
import com.pmgo.osworks.domain.service.ClienteService;
import com.pmgo.osworks.domain.service.OrdemServicoService;
import com.pmgo.osworks.modeldto.OrdemServicoDTO;
import com.pmgo.osworks.modeldto.OrdemServicoInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private OrdemServicoService ordemServicoService;

    // Deve incluir a depedencia no Modelmapper no Pom.xml para trabalhar com os DTO que são classes de representação sem a necessidade de usar os set de atributos.
    // Não eh componente Spring, então temos que criar uma classe de Configuração para criar o Bean para ser injetado,
    // Criamos a Classe ModelMapperConfig no package core que disponibiliza o Bean ModelMapper
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<OrdemServicoDTO> listar() {
//        return ordemServicoRepository.findAll();
        return toCollectionDTO(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
        if (ordemServico.isPresent()) {
//            OrdemServicoDTO ordemServicoDTO = modelMapper.map(ordemServico.get(), OrdemServicoDTO.class);
            OrdemServicoDTO ordemServicoDTO = toDTO(ordemServico.get());
            return ResponseEntity.ok(ordemServicoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoDTO criar(@Valid @RequestBody OrdemServicoInputDTO ordemServicoInputDTO) throws Exception {
        OrdemServico ordemServico = toEntity(ordemServicoInputDTO);
        return toDTO(ordemServicoService.criar(ordemServico));
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@Valid @PathVariable Long ordemServicoId) throws Exception {
        ordemServicoService.finalizar(ordemServicoId);
    }

    // Converte um model em um DTO
    private OrdemServicoDTO toDTO(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoDTO.class);
    }

    // Converte uma lista de model em uma lista DTO
    private List<OrdemServicoDTO> toCollectionDTO(List<OrdemServico> ordemServicos) {
        return ordemServicos.stream()
                .map(ordemServico -> toDTO(ordemServico))
                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInputDTO ordemServicoInputDTO) {
        return modelMapper.map(ordemServicoInputDTO, OrdemServico.class);
    }

}
