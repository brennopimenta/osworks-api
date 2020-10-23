package com.pmgo.osworks.domain.service;

import com.pmgo.osworks.domain.exception.NegocioException;
import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void excluir(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
