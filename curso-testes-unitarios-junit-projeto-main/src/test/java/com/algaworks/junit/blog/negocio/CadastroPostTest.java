package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cadastro de Post")
@ExtendWith(MockitoExtension.class)
class CadastroPostTest {

    Editor editor;

    Post post;

    @Mock
    ArmazenamentoPost armazenamentoPost;

    @Mock
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Nested
    @DisplayName("Dado um post válido")
    class PostValido{

        @BeforeEach
        void beforeEach(){
            editor = new Editor(1L, "Alex", "alex@email.com", BigDecimal.TEN, true);
            post = new Post("Olá mundo Java", "Olá Java com System.out.println", editor, true, true);

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .thenAnswer(invocationOnMock -> {
                        Post postCadastrado = invocationOnMock.getArgument(0, Post.class);
                        postCadastrado.setId(1L);
                        return postCadastrado;
                    });
        }

        @Nested
        @DisplayName("Quando criar")
        class QuandoCriar{

            @Test
            @DisplayName("Então deve retornar o id criado")
            public void criarPostRetornandoIdCriado(){

                Post postCadastrado = cadastroPost.criar(post);

                Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(post);

                assertEquals(1L, postCadastrado.getId());

            }

            @Test
            @DisplayName("Então deve criar o slug")
            public void deveCriarSlug(){

                Post postCadastrado = cadastroPost.criar(post);

                assertEquals("ola-mundo-java-3fe3d8e2c0af6d52", postCadastrado.getSlug());
            }

        }

    }

}