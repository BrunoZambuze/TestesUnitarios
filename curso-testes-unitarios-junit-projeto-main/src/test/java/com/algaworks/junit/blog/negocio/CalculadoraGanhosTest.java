package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadora;
    Editor autor;
    Post post;

    @BeforeAll //Antes de todos os métodos será executado esse comando SOMENTE 1 VEZ em todo o momento
    static void createCalculadoraBeforeAll(){
        calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
    }

    @BeforeEach //Será executado esse comando antes de cada método
    void createAutorAndPost(){
        autor = new Editor(1L, "Alex", "alex@gmail.com", new BigDecimal("5"), true);
        post = new Post(1L, "Ecosistema Java",
                "O ecossistema do Java é muito maduro", autor, "ecossistema-java-abc123", null, false, false);
    }

    @Nested
    class calcularGanhos{
        @Test
        public void Dado_post_e_autor_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_com_bonus(){
            Ganhos ganhos = calculadora.calcular(post);
            assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
        }

        @Test
        public void Dado_post_e_autor_premium_Quando_calcular_ganhos_Entao_deve_retornar_quantidade_de_palavras_no_post(){
            Ganhos ganhos = calculadora.calcular(post);
            assertEquals(7, ganhos.getQuantidadePalavras());
        }

        @Test
        public void Dado_post_e_autor_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_pago_por_palavra_do_autor(){
            Ganhos ganhos = calculadora.calcular(post);
            assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
        }

        @Test
        public void Dado_um_post_e_autor_nao_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_sem_bonus(){
            autor.setPremium(false);
            Ganhos ganhos = calculadora.calcular(post);
            assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());

        }
    }

}