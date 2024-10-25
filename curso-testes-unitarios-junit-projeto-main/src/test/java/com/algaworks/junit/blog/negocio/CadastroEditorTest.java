package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cadastro Editor")
@ExtendWith(MockitoExtension.class)
class CadastroEditorTest {

    @Nested
    @DisplayName("Dado um cadastro de editor")
    class CadastrarEditor{

        Editor editor;

        @Mock //O Mockito vai instanciar a classe "dublê" a cada teste, como se fosse o BeforeEach
        ArmazenamentoEditor armazenamentoEditor;

        @Mock
        GerenciadorEnvioEmail gerenciadorEnvioEmail;

        @InjectMocks //Vamos injetar os 2 Mocks existentes como parâmetro
                    //O Mockito vai instanciar a classe "dublê" a cada teste, como se fosse o BeforeEach
        CadastroEditor cadastroEditor;

        @BeforeEach
        void beforeEach(){
            editor = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);

            //.lenient():
            Mockito.lenient().when(armazenamentoEditor.salvar(Mockito.any(Editor.class))) //Em vez de passar a variável "editor" com o valor fixo, podemos passar qualquer objeto
                                                            //da classe Editor como parâmetro
                   .thenAnswer(chamadaDoMetodo -> { //Vamos usar o thenAnswer para poder retornar a manipulção do objeto passado como parâmetro (editor)
                       Editor editorPassado = chamadaDoMetodo.getArgument(0, Editor.class); //Pegar o parâmetro da posição 0 da classe Editor
                       editorPassado.setId(1L);
                       return editorPassado;
                   });
        }

        @Nested
        @DisplayName("Quando for cadastrar um editor válido")
        class CadastrarEditorValido{

            @Test
            @DisplayName("Entao retornar um id de cadastro")
            public void cadastrarEditorValido(){
                Editor editorCadastrado = cadastroEditor.criar(editor);
                assertEquals(1L, editorCadastrado.getId());
            }

            @Test
            @DisplayName("Então verificar se o método 'salvar' do Mock ArmazenamentoEditor está funcionando corretamente")
            public void verificarMockDoMetodoSalvar(){
                cadastroEditor.criar(editor);
                Mockito.verify(armazenamentoEditor, Mockito.times(1)) //1 argumento: Mock que queremos validar
                                                                                            // 2 argumento: Quantiade de vezes que ele será executado
                //.verify retorna o próprio Mock, por isso podemos utilizar o método que queremos validar
                       .salvar(Mockito.eq(editor)); //Vou utilizar o Mockito.eq(Editor), pois quero que o objeto passado como parâmetro dessa verificação seja
                                                   //o mesmo valor passado pelo objeto editor para p método criar. Também poderíamos passar diretamente o
                                                  //editor como parâmetro, assim estaríamos comparando o espaço na memória dos objetos
            }

            @Test
            @DisplayName("Então deve enviar um email com a mensagem possuindo o mesmo email cadastrado pelo editor")
            public void enviarMensagemCertaAoEditor(){
                ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);
                Editor editorCadastrado = cadastroEditor.criar(editor);
                Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());
                Mensagem mensagemCapturada = mensagemArgumentCaptor.getValue();
                assertEquals(editorCadastrado.getEmail(), mensagemCapturada.getDestinatario());
            }

        }

        @Nested
        @DisplayName("Quando for cadastrar um editor valido Quando criar")
        class cadastrarEditorValidoQuandoCriar{

            @Test
            @DisplayName("Então lançar exception")
            public void lancarException(){
                Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class))).thenThrow(new RuntimeException());
                assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor));
                Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any());
            }

        }

        @Nested
        @DisplayName("Quando for cadastrar um editor com email já existente")
        class cadastrarComEmailJaExistente{

            @Test
            @DisplayName("Então deve lançar exception")
            public void exceptionEmailExistente(){
                Mockito.when(armazenamentoEditor.encontrarPorEmail("alex@gmail.com"))
                        .thenReturn(Optional.empty())
                        .thenReturn(Optional.of(editor));
                cadastroEditor.criar(editor);
                Editor editorComEmailExistente = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
                assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
            }

            @Nested
            @DisplayName("Quando for cadastrar um editor válido")
            class cadastrarEditorValidor{

                @Test
                @DisplayName("Então enviar email após salvar (primeiro o método de salvar depois o de enviar email")
                public void salvarPrimeiroEnviarEmailDepois(){
                    cadastroEditor.criar(editor);
                    Mockito.inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
                    Mockito.verify(armazenamentoEditor, Mockito.times(1)).salvar(editor);
                    Mockito.verify(gerenciadorEnvioEmail, Mockito.times(1)).enviarEmail(Mockito.any(Mensagem.class));
                }

            }

        }

    }

}