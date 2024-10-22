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

            Mockito.when(armazenamentoEditor.salvar(editor))
                   .thenReturn(new Editor(1L, "Alex", "alex@gmail.com", BigDecimal.TEN, true));
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

        }

    }

}