package com.pmgo.osworks.controller;


import com.pmgo.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.model.Comentario;
import com.pmgo.osworks.domain.model.OrdemServico;
import com.pmgo.osworks.domain.repository.OrdemServicoRepository;
import com.pmgo.osworks.domain.service.OrdemServicoService;
import com.pmgo.osworks.modeldto.ClienteIdInputDTO;
import com.pmgo.osworks.modeldto.ClienteResumoDTO;
import com.pmgo.osworks.modeldto.ComentarioDTO;
import com.pmgo.osworks.modeldto.ComentarioInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordem-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ComentarioDTO> listar(@PathVariable Long ordemServicoId) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
        return toCollectionDTO(ordemServico.getComentarios());
    }

//    @GetMapping("/{clienteId}")
//    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
//        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
//        //return cliente.orElse(null); Retorna o cliente Opetional ou se não tiver nada retorna null
//        if (cliente.isPresent()) {
//            return ResponseEntity.ok(cliente.get());
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioDTO adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInputDTO comentarioInputDTO) {
        Comentario comentario = ordemServicoService.adicionarComentario(ordemServicoId, comentarioInputDTO.getDescricao());
        return toDTO(comentario);

    }

    // Converte um model em um DTO
    private ComentarioDTO toDTO(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    // Converte uma lista de model em uma lista DTO
    private List<ComentarioDTO> toCollectionDTO(List<Comentario> comentarios) {
        return comentarios.stream()
                .map(comentario -> toDTO(comentario))
                .collect(Collectors.toList());
    }

    private Comentario toEntity(ComentarioInputDTO comentarioInputDTO) {
        return modelMapper.map(comentarioInputDTO, Comentario.class);
    }

}
