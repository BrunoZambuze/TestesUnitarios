package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData(){}

    public static Editor.Builder editorNovo(){
       return Editor.builder()
               .withId(null)
               .withNome("Alex")
               .withEmail("alex@gmail.com")
               .withValorPagoPorPalavra(BigDecimal.TEN)
               .withPremium(true);
    }

    public static Editor.Builder editorExistente(){
        return editorNovo().withId(1L);
    }

}
