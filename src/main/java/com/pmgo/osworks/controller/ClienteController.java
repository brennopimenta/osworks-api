package com.pmgo.osworks.controller;

import com.pmgo.osworks.domain.ValidationGroups;
import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.model.OrdemServico;
import com.pmgo.osworks.domain.repository.ClienteRepository;
import com.pmgo.osworks.domain.service.ClienteService;
import com.pmgo.osworks.modeldto.ClienteIdInputDTO;
import com.pmgo.osworks.modeldto.ClienteResumoDTO;
import com.pmgo.osworks.modeldto.OrdemServicoDTO;
import com.pmgo.osworks.modeldto.OrdemServicoInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        //return cliente.orElse(null); Retorna o cliente Opetional ou se não tiver nada retorna null
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) throws Exception {
        return clienteService.salvar(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) throws Exception {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        cliente = clienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }


    // Converte um model em um DTO
    private ClienteResumoDTO toDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResumoDTO.class);
    }

    // Converte uma lista de model em uma lista DTO
    private List<ClienteResumoDTO> toCollectionDTO(List<Cliente> clientes) {
        return clientes.stream()
                .map(cliente -> toDTO(cliente))
                .collect(Collectors.toList());
    }

    private Cliente toEntity(ClienteIdInputDTO clienteIdInputDTO) {
        return modelMapper.map(clienteIdInputDTO, Cliente.class);
    }

}
