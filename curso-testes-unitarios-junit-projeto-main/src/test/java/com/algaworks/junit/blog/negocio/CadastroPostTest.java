package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import org.junit.jupiter.api.extension.ExtendWith;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cadastro de Post")
@ExtendWith(MockitoExtension.class)
class CadastroPostTest {

    @Spy
    Editor editor = EditorTestData.editorExistente().build();

    @Spy
    Post post = PostTestData.postNovo(editor).build();

    @Captor
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

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
            @DisplayName("Então deve verificar se está salvando o post")
            public void verificarSeEstaSalvando(){

                cadastroPost.criar(post);

                Mockito.verify(armazenamentoPost, Mockito.times(1))
                        .salvar(post);
            }

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
                Post postCriado = cadastroPost.criar(post);

                Mockito.verify(post, Mockito.times(1))
                       .setSlug(Mockito.anyString());

                assertNotNull(postCriado.getSlug());
            }

            @Test
            @DisplayName("Então deve retornar post com ganhos")
            public void retornarPostComGanhos(){

                Mockito.when(calculadoraGanhos.calcular(Mockito.any(Post.class)))
                        .thenReturn(new Ganhos((BigDecimal.TEN), 4, BigDecimal.valueOf(40)));

                Post postCriado = cadastroPost.criar(post);

                Mockito.verify(post, Mockito.times(1))
                       .setGanhos(Mockito.any(Ganhos.class));
                assertNotNull(postCriado.getGanhos());
            }

        }

        @Test
        @DisplayName("Então verificar se está enviando notificação")
        public void verificarSeEstaEnviandoNotificacao(){

            cadastroPost.criar(post);

            Mockito.verify(gerenciadorNotificacao, Mockito.times(1))
                   .enviar(Mockito.any(Notificacao.class));
        }

        @Test
        @DisplayName("E verificar o título da mensagem da notificação")
        public void verificarTituloDaMensagemNotificacao(){

            cadastroPost.criar(post);

            Mockito.verify(gerenciadorNotificacao).enviar(notificacaoArgumentCaptor.capture());

            Notificacao notificacaoCapturada = notificacaoArgumentCaptor.getValue();

            notificacaoCapturada.getConteudo().contains(post.getTitulo());

            assertEquals("Novo post criado -> " + post.getTitulo(), notificacaoCapturada.getConteudo());

        }

        @Test
        @DisplayName("Então deve verificar as ordens dos métodos")
        public void ordemDosMetodos(){

            cadastroPost.criar(post);

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost, gerenciadorNotificacao);

            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(Mockito.any(Post.class));
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
            inOrder.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(Mockito.any(Notificacao.class));

        }

    }

    @Nested
    @DisplayName("Dado um post nulo")
    class PostNulo{

        @Nested
        @DisplayName("Quando criar")
        class CadastrarPostNulo{

            @Test
            @DisplayName("Então deve lançar exception, não salvar e não enviar notificação")
            public void lanarExceptionAoCadastarNulo(){

                Executable metodoComException = () -> cadastroPost.criar(null);

                assertThrows(NullPointerException.class, metodoComException);

                Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
                Mockito.verify(gerenciadorNotificacao, Mockito.never()).enviar(Mockito.any(Notificacao.class));

            }

        }

    }

}