package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    public void assercaoAgrupada(){
        Pessoa pessoa = new Pessoa("Alex", "Silva");
        assertAll("Asserções de pessoas",
                () -> assertEquals("Alex", pessoa.getNome(), "Nome não é igual"),
                () -> assertEquals("Silva", pessoa.getSobrenome(), "Sobrenome não é igual"));
    }

}