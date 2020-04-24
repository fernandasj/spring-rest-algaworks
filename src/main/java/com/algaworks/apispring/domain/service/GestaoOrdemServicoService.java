package com.algaworks.apispring.domain.service;

import com.algaworks.apispring.domain.exception.NegocioException;
import com.algaworks.apispring.domain.model.Cliente;
import com.algaworks.apispring.domain.model.OrdemServico;
import com.algaworks.apispring.domain.model.StatusOrdemServico;
import com.algaworks.apispring.domain.repository.ClienteRepository;
import com.algaworks.apispring.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public OrdemServico salvar(OrdemServico ordemServico){
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente nao encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }
}
