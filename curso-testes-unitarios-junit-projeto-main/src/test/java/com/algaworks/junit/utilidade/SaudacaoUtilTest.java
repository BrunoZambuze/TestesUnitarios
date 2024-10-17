package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*; //Utilizamos o "Assertions" para realizar uma auto-comparação

class SaudacaoUtilTest {

    @Test
//  @Disabled("Não está mais sendo utilizado!") Caso seja necessário desabilitar esse método
    public void saudarTest(){
        String saudacao = SaudacaoUtil.saudar(9);
        assertEquals("Bom dia", saudacao, "Saudação incorreta"); //1: Valor esperado | 2: Valor atual | 3: Mensagem de erro
    }

    @Test
    public void deveLancarException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                        () -> SaudacaoUtil.saudar(-10));
        assertEquals("Hora inválida", exception.getMessage());
    }

    @Test
    public void naoDeveLancarException(){
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(9));
    }

}