package com.algaworks.junit.utilidade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*; //Utilizamos o "Assertions" para realizar uma auto-comparação

class SaudacaoUtilTest {

    @Test
//  @Disabled("Não está mais sendo utilizado!") Caso seja necessário desabilitar esse método
    public void saudarTestBomdia(){
        String saudacao = SaudacaoUtil.saudar(9);
//        assertEquals("Bom dia", saudacao, "Saudação incorreta"); //1: Valor esperado | 2: Valor atual | 3: Mensagem de erro

        assertThat(saudacao)
                  .withFailMessage("Saudação incorreta")
                  .isEqualTo("Bom dia");
    }

    @Test
    public void saudarTestBoaTarde(){
        String saudacao = SaudacaoUtil.saudar(13);
        assertEquals("Boa tarde", saudacao, "Saudação incorreta");
    }

    @Test
    public void saudarTestBoaNoite(){
        String saudacao = SaudacaoUtil.saudar(4);
        assertEquals("Boa noite", saudacao, "Saudação incorreta");
    }

    @Test
    public void deveLancarException(){
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                                                        () -> SaudacaoUtil.saudar(-10));
//        assertEquals("Hora inválida", exception.getMessage());

        assertThatThrownBy(() -> SaudacaoUtil.saudar(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora inválida");
    }

    @Test
    public void naoDeveLancarException(){
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(9));
    }

}