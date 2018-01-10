package com.algaworks.algamoney.api.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LancamentoFilter {

    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoInicial;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoFinal;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimentoInicial() {
        return dataVencimentoInicial;
    }

    public void setDataVencimentoInicial(LocalDate dataVencimentoInicial) {
        this.dataVencimentoInicial = dataVencimentoInicial;
    }

    public LocalDate getDataVencimentoFinal() {
        return dataVencimentoFinal;
    }

    public void setDataVencimentoFinal(LocalDate dataVencimentoFinal) {
        this.dataVencimentoFinal = dataVencimentoFinal;
    }
}
