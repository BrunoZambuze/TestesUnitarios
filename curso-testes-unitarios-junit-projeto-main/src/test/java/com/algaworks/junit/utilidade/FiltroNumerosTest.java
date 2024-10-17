package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroNumerosTest {

    @Test
    public void deveRetornarNumerosPares(){
        List<Integer> listaNumeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosEsperados = Arrays.asList(2, 4);
        List<Integer> resultado = FiltroNumeros.numerosPares(listaNumeros);
        assertIterableEquals(numerosEsperados, resultado); //Irá comparar os objetos através equals/hashcode e irá comparar a ordem desses objetos
    }

}