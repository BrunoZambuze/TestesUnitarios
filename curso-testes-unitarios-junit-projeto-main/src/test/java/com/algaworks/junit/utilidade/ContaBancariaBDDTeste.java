package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta Bancária")
public class ContaBancariaBDDTeste {

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$10,00")
    class ContaBancariaComSaldo{

        ContaBancaria conta;

        @BeforeEach
        void beforeEach(){
            conta = new ContaBancaria(BigDecimal.TEN);
        }

        @Nested
        @DisplayName("Quando efeturar o saque com valor menor que o saldo e maior que zero")
        class SaqueValorMenor{

            private final BigDecimal valorSaque = new BigDecimal("9.0");

            @Test
            @DisplayName("Então não deve lançar exception")
            void deveFazerSaqueSemException(){
                Executable chamadaDeMetodo = () -> conta.saque(valorSaque);
                assertDoesNotThrow(chamadaDeMetodo);
            }

            @Test
            @DisplayName("E deve subtrair o saldo")
            void deveSubtrairSaldo(){
                conta.saque(valorSaque);
                assertEquals(new BigDecimal("1.0"), conta.getSaldo());
            }

        }

        @Nested
        @DisplayName("Quando executar o saque com um valor igual a zero")
        class SaqueValorZerado{

            @Test
            @DisplayName("Então deve lançar uma exceção")
            void saqueZeradoDeveLancarException(){
                Executable metodoComExeption = () -> conta.saque(BigDecimal.ZERO);
                assertThrows(IllegalArgumentException.class, metodoComExeption);
            }
        }

        @Nested
        @DisplayName("Quando executar um saque com valor menor que zero")
        class SaqueValorMenorQueZero{

            @Test
            @DisplayName("Então deve lançar uma exceção")
            void saqueValoMenorQueZeroDeveLancarException(){
                Executable metodoComException = () -> conta.saque(new BigDecimal("-10"));
                assertThrows(IllegalArgumentException.class, metodoComException);
            }

        }

        @Nested
        @DisplayName("Quando executar o saque com um valor maior que o saldo")
        class SaqueValorMaior{

            private final BigDecimal valorSaque = new BigDecimal("100.0");

            @Test
            @DisplayName("Então deve lançar um exception")
            void deveLancarException(){
                Executable metodoComException = () -> conta.saque(valorSaque);
                assertThrows(RuntimeException.class, metodoComException);
            }

            @Test
            @DisplayName("E não deve alterar o saldo")
            void naoDeveAlterarSaldo(){
                try{
                    conta.saque(valorSaque);
                } catch (RuntimeException e){ }

                assertEquals(BigDecimal.TEN, conta.saldo());
            }

        }

        @Nested
        @DisplayName("Quando executar um depósito com um valor zerado")
        class DepositoComValorZerado{

            private final BigDecimal valorDeposito = BigDecimal.ZERO;

            @Test
            @DisplayName("Então deve lançar um exception")
            void deveLancarException(){
                Executable metodoComException = () -> conta.deposito(valorDeposito);
                assertThrows(IllegalArgumentException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando executar um depósito com um valor negativo")
        class DepositoComValorNegativo{

            private final BigDecimal valorDeposito = new BigDecimal("-10");

            @Test
            @DisplayName("Então deve lançar uma exception")
            void deveLancarExceptoin(){
                Executable metodoComException = () -> conta.deposito(valorDeposito);
                assertThrows(IllegalArgumentException.class, metodoComException);
            }

        }

        @Nested
        @DisplayName("Quando executar um depósito com um valor maior que zero")
        class DepositoValorMaiorQueZero{

            private final BigDecimal valorDeposito = BigDecimal.TEN;

            @Test
            @DisplayName("Então não deve lançar uma exception")
            void naoDeveLancarException(){
                Executable metodoSemException = () -> conta.deposito(valorDeposito);
                assertDoesNotThrow(metodoSemException);
            }

            @Test
            @DisplayName("E deve adicionar o valor no saldo")
            void adicionarValorSaldo(){
                conta.deposito(valorDeposito);
                assertEquals(new BigDecimal("20"), conta.getSaldo());
            }

        }

    }

}
