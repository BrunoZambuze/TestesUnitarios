package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste utilitário métodos da conta bancaria")
class ContaBancariaTest {

    @Test
    @DisplayName("Deve lançar uma exceção caso saldo seja nulo")
    public void deveLancarExcecaoSaldoNulo(){
        Executable chamadaDeMetodoValida = () -> new ContaBancaria(null);
        assertThrows(IllegalArgumentException.class, chamadaDeMetodoValida);
    }

    @Test
    @DisplayName("Valida se o saldo da conta é zero")
    public void podeZero(){
        BigDecimal saldoZerado = new BigDecimal("0.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoZerado);
        assertEquals(new BigDecimal("0.0"), contaBancaria.getSaldo());
    }

    @Test
    @DisplayName("Valida se o saldo da conta é negativo")
    public void podeNegativo(){
        BigDecimal saldoZerado = new BigDecimal("-10.0");
        BigDecimal valorEsperado = new BigDecimal("-10.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoZerado);
        assertEquals(valorEsperado, contaBancaria.getSaldo());
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do saque for nulo")
    public void saqueNaoPodeNull(){
        //Arrange
        BigDecimal saldoConta = new BigDecimal("100.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);

        //Asc
        Executable chamadaDeMetodoValida = () -> contaBancaria.saque(null);

        //Assert
        assertThrows(IllegalArgumentException.class, chamadaDeMetodoValida);
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do saque for 0")
    public void saqueNaoPodeZero(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        Executable chamadaDeMetodo = () -> contaBancaria.saque(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do saque for negativo")
    public void saqueNaoPodeNegativo(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        BigDecimal valorSaque = new BigDecimal("-10.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        Executable chamadaDeMetodo = () -> contaBancaria.saque(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(valorSaque));
    }

    @Test
    @DisplayName("Lança uma exceção se o valor saldo cliente ficar negativo após o saque")
    public void saqueSaldoNaoPodeNegativo(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        BigDecimal valorSaque = new BigDecimal("1000.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        Executable chamadaDeMetodo = () -> contaBancaria.saque(BigDecimal.ZERO);
        assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorSaque));
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do depósito for nulo")
    public void depositoValorNaoPodeNull(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        Executable chamadaDeMetodo = () -> contaBancaria.deposito(null);
        assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do depósito for 0")
    public void depositoValorNaoPodeZero(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        Executable chamadaDeMetodo = () -> contaBancaria.deposito(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
    }

    @Test
    @DisplayName("Lança uma exceção se o valor do depósito for negativo")
    public void depositoValorNaoPodeNegativo(){
        BigDecimal saldoConta = new BigDecimal("100.0");
        ContaBancaria contaBancaria = new ContaBancaria(saldoConta);
        BigDecimal valorDepositoInvalido = new BigDecimal("-10");
        Executable chamadaDeMetodo = () -> contaBancaria.deposito(valorDepositoInvalido);
        assertThrows(IllegalArgumentException.class, chamadaDeMetodo);
    }

}