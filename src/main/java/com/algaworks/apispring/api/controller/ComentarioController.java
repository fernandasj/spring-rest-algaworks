package com.algaworks.apispring.api.controller;

import com.algaworks.apispring.api.model.ComentarioInput;
import com.algaworks.apispring.api.model.ComentarioModel;
import com.algaworks.apispring.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.apispring.domain.model.Comentario;
import com.algaworks.apispring.domain.model.OrdemServico;
import com.algaworks.apispring.domain.repository.ComentarioRepository;
import com.algaworks.apispring.domain.repository.OrdemServicoRepository;
import com.algaworks.apispring.domain.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço nao encontrada"));
        return toCollectionModel(ordemServico.getComentarios());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
                                     @Valid @RequestBody ComentarioInput comentarioInput){
        Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId,
                comentarioInput.getDescricao());

        return toModel(comentario);
    }

    private ComentarioModel toModel(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioModel.class);
    }

    private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
        return comentarios.stream()
                .map(comentario -> toModel(comentario))
                .collect(Collectors.toList());
    }

}
