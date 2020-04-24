package com.algaworks.apispring.domain.service;

import com.algaworks.apispring.domain.exception.NegocioException;
import com.algaworks.apispring.domain.model.Cliente;
import com.algaworks.apispring.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteExistente != null && !clienteExistente.equals(cliente)){
            throw new NegocioException("Ja existe um cliente cadastrado com este e-mail");
        }
        return clienteRepository.save(cliente);
    }

    public void excluir(Long clienteId){
        clienteRepository.deleteById(clienteId);
    }
}
