package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Post;

public class PostTestData {

    private PostTestData(){}

    public static Post.Builder postNovo(Editor editor){
        return Post.builder()
                   .withId(null)
                   .withTitulo("Olá mundo Java")
                   .withConteudo("Olá Java com System.out.println")
                   .withAutor(editor)
                   .withPago(true)
                   .withPublicado(true);
    }

}
