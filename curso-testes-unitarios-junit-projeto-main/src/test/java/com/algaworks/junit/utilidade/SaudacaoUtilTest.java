package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*; //Utilizamos o "Assertions" para realiar uma auto-comparação

class SaudacaoUtilTest {

    @Test
    public void saudarTest(){
        String saudacao = SaudacaoUtil.saudar(9);
        assertEquals("Bom dia", saudacao, "Saudação incorreta"); //1: Valor esperado | 2: Valor atual | 3: Mensagem de erro
    }

    @Test
    public void deveLancarException(){
        assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-10));
    }

}