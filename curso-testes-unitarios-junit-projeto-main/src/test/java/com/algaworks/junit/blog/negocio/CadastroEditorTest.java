package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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

            Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class))) //Em vez de passar a variável "editor" com o valor fixo, podemos passar qualquer objeto
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
                                                   //o mesmo objeto passado pelo método criar
            }

        }

//        @Nested
//        @DisplayName("Quando for cadastrar um editor valido mas com ")

    }

}