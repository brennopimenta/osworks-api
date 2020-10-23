package com.pmgo.osworks.domain.service;

import com.pmgo.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.pmgo.osworks.domain.exception.NegocioException;
import com.pmgo.osworks.domain.model.Cliente;
import com.pmgo.osworks.domain.model.Comentario;
import com.pmgo.osworks.domain.model.OrdemServico;
import com.pmgo.osworks.domain.model.StatusOrdemServico;
import com.pmgo.osworks.domain.repository.ClienteRepository;
import com.pmgo.osworks.domain.repository.ComentarioRepository;
import com.pmgo.osworks.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OrdemServicoService {

    @Autowired
    public OrdemServicoRepository ordemServicoRepository;

    @Autowired
    public ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado!")); // retorna 400
        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        return ordemServicoRepository.save(ordemServico);
    }

    public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);
        return comentarioRepository.save(comentario);
    }

    public void finalizar(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        ordemServico.finalizar();

    }

    private OrdemServico buscar(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Servico Não Encontrada!"));
    }
}
