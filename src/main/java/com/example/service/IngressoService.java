package com.example.service;

import com.example.model.Ingresso;
import com.example.repository.IngressoRepository;
import java.util.List;
import java.util.Optional;

public class IngressoService {
    private IngressoRepository ingressoRepository = new IngressoRepository();

    // CREATE
    public Ingresso criarIngresso(String tipo, double preco, int quantidade) {
        if (ingressoRepository.findByTipo(tipo).isPresent()) {
            throw new IllegalArgumentException("Ingresso com este tipo já existe.");
        }
        Ingresso novo = new Ingresso(tipo, preco, quantidade);
        return ingressoRepository.save(novo);
    }

    // READ (Single)
    public Optional<Ingresso> getIngresso(String tipo) {
        return ingressoRepository.findByTipo(tipo);
    }

    // READ (All)
    public List<Ingresso> listarIngressos() {
        return ingressoRepository.findAll();
    }

    // UPDATE
    public Ingresso atualizarIngresso(String tipo, double novoPreco, int novaQtd) {
        Ingresso ingresso = ingressoRepository.findByTipo(tipo)
                .orElseThrow(() -> new RuntimeException("Ingresso não encontrado: " + tipo));
        
        ingresso.setPreco(novoPreco); // Requer setter no modelo
        ingresso.setQuantidadeDisponivel(novaQtd); // Requer setter no modelo
        
        return ingressoRepository.save(ingresso);
    }

    // DELETE
    public void deletarIngresso(String tipo) {
        if (ingressoRepository.findByTipo(tipo).isEmpty()) {
            throw new RuntimeException("Ingresso não encontrado: " + tipo);
        }
        ingressoRepository.deleteByTipo(tipo);
    }
}
