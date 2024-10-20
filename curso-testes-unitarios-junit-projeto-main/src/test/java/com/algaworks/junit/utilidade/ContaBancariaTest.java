package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ContaBancariaTest {

    ContaBancaria contaBancaria;
    static BigDecimal saldoConta;

    @BeforeAll
    static void beforeAll(){
        saldoConta = new BigDecimal("100.0");
    }

    @BeforeEach
    void beforeEach(){
        contaBancaria = new ContaBancaria(saldoConta);
    }

    @Nested
    class ConstrutorSaldo{
        @Test
        public void Dado_um_valor_nulo_Quando_adicionar_no_contrutor_do_saldo_na_conta_Entao_lance_exception(){
            Executable chamadaDeMetodoValida = () -> new ContaBancaria(null);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodoValida);
        }

        @Test
        public void Dado_um_valor_zero_Quando_adicionar_no_construtor_do_saldo_na_conta_Entao_retornar_o_saldo_zerado(){
            BigDecimal saldoZerado = new BigDecimal("0.0");
            ContaBancaria contaBancaria = new ContaBancaria(saldoZerado);
            assertEquals(new BigDecimal("0.0"), contaBancaria.getSaldo());
        }

        @Test
        public void Dado_um_valor_negativo_Quando_adicionar_no_construtor_do_saldo_na_conta_Entao_retornar_o_saldo_negativo(){
            BigDecimal saldoZerado = new BigDecimal("-10.0");
            BigDecimal valorEsperado = new BigDecimal("-10.0");
            ContaBancaria contaBancaria = new ContaBancaria(saldoZerado);
            assertEquals(valorEsperado, contaBancaria.getSaldo());
        }
    }

    @Nested
    class Saque{
        @Test
        public void Dado_um_valor_nulo_Quando_sacar_lance_exception(){
            //Asc
            Executable chamadaDeMetodoValida = () -> contaBancaria.saque(null);

            //Assert
            assertThrows(IllegalArgumentException.class, chamadaDeMetodoValida);
        }

        @Test
        public void Dado_um_valor_zero_Quando_sacar_lance_exception(){
            Executable chamadaDeMetodo = () -> contaBancaria.saque(BigDecimal.ZERO);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
        }

        @Test
        public void Dado_um_valor_negativo_Quando_sacar_Entao_lance_exception(){
            BigDecimal valorSaque = new BigDecimal("-10.0");
            Executable chamadaDeMetodo = () -> contaBancaria.saque(valorSaque);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
        }

        @Test
        public void Dado_um_valor_que_deixa_o_saldo_negativo_Quando_sacar_Entao_lance_exception(){
            BigDecimal valorSaque = new BigDecimal("1000.0");
            Executable chamadaDeMetodo = () -> contaBancaria.saque(valorSaque);
            assertThrows(RuntimeException.class, chamadaDeMetodo);
        }
    }

    @Nested
    class Deposito{
        @Test
        public void Dado_um_valor_nulo_Quando_depositar_Entao_deve_lancar_exception(){
            Executable chamadaDeMetodo = () -> contaBancaria.deposito(null);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
        }

        @Test
        public void Dado_um_valor_zero_Quando_depositar_Entao_deve_lancar_exception(){
            Executable chamadaDeMetodo = () -> contaBancaria.deposito(BigDecimal.ZERO);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
        }

        @Test
        public void Dado_um_valor_negativo_Quando_depositar_Entao_deve_lancar_exception(){
            BigDecimal valorDepositoInvalido = new BigDecimal("-10");
            Executable chamadaDeMetodo = () -> contaBancaria.deposito(valorDepositoInvalido);
            assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
        }
    }

}