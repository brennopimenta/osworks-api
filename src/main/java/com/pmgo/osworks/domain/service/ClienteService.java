package com.pmgo.osworks.domain.service;

import com.pmgo.osworks.domain.exception.NegocioException;
import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) throws Exception {
        Cliente clienteExistnte = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteExistnte != null && !clienteExistnte.equals(cliente)) {
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail!");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente){
        Cliente clienteSalvo = clienteRepository.getOne(id);
        //BeanUtils do springFramework copia os atributos de um objeto para o outro ignorando o codigo, ótimo para PUT. alteração.
        BeanUtils.copyProperties(cliente, clienteSalvo, "id");
        return clienteRepository.save(clienteSalvo);
    }

    public void excluir(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
