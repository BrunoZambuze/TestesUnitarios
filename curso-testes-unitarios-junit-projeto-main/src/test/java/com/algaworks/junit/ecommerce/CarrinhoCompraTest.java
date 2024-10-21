package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.function.Executable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compras")
class CarrinhoCompraTest {

    @Nested
    @DisplayName("Dado um carrinho de compras")
    class CarrinhoDeCompra{

        private static Cliente cliente;
        Produto produto;
        CarrinhoCompra carrinhoCompra;

        @BeforeAll
        static void beforeAll(){
            cliente = new Cliente(1L, "Bruno");
        }

        @BeforeEach
        void beforeEach(){
            produto = new Produto(1L, "Escova de Dente", "Usa pra escovar os dentes", new BigDecimal("5.90"));
            carrinhoCompra = new CarrinhoCompra(cliente, new ArrayList<>());
            carrinhoCompra.adicionarProduto(produto, 1);
        }

        @Nested
        @DisplayName("Quando for retornar os itens")
        class retornarItens{

            @Test
            @DisplayName("Então deve retornar uma nova cópia da lista original")
            void retornarCopiaDaListaOriginal(){
                List<ItemCarrinhoCompra> lista = Arrays.asList(new ItemCarrinhoCompra(produto, 1));
                assertIterableEquals(lista, carrinhoCompra.getItens());
            }

        }

        @Nested
        @DisplayName("Quando for adicionar um produto que não tenha nome")
        class AdicionarUmProdutoSemNome{

            @Test
            @DisplayName("Então deve lançar exception")
            void nomeNuloLancarException(){
                produto.setNome(null);
                Executable metodoComException = () -> carrinhoCompra.adicionarProduto(produto, 1);
                assertThrows(NullPointerException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando for adicionar um produto que não tenha descrição")
        class AdicionarProdutoSemDescricao{

            @Test
            @DisplayName("Então deve lançar exception")
            public void descricaoNuloLancarException(){
                produto.setDescricao(null);
                Executable metodoComException = () -> carrinhoCompra.adicionarProduto(produto, 1);
                assertThrows(NullPointerException.class, metodoComException);
            }

        }

        @Nested
        @DisplayName("Quando for adicionar um produto que não tenha valor")
        class AdicionarProdutoSemValor{

            @Test
            @DisplayName("Então deve lançar exception")
            public void valorNuloLancarException(){
                produto.setValor(null);
                Executable metodoComException = () -> carrinhoCompra.adicionarProduto(produto, 1);
                assertThrows(NullPointerException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando for adicionar um produto válido mas com quantidade menor que 1")
        class AdicionarProdutoValidoMasQuantidadeMenorQueUm{

            @Test
            @DisplayName("Então deve lançar exception")
            public void quantidadeMenorQueUmLanceException(){
                Executable metodoComException = () -> carrinhoCompra.adicionarProduto(produto, 0);
                assertThrows(IllegalArgumentException.class, metodoComException);
            }

        }

        @Nested
        @DisplayName("Quando for adicionar um produto que já existe na lista")
        class AdicionarProdutoQueJaExiste{

            @Test
            @DisplayName("Então deve incrementar a quantidade")
            public void adicionarProdutoJaExistente(){
                carrinhoCompra.adicionarProduto(produto, 1);
                carrinhoCompra.adicionarProduto(produto, 1);
                assertAll("Validação de adicionar produto",
                        () -> assertEquals(1, carrinhoCompra.getItens().size()),
                        () -> assertEquals(3, carrinhoCompra.getItens().stream().findFirst().get().getQuantidade()));
            }
        }

        @Nested
        @DisplayName("Quando for adicionar um produto valido")
        class AdicionarProdutoValido{

            @Test
            @DisplayName("Então não deve lançar nenhuma exception")
            public void adicionarProdutoValido(){
                Produto produto2 = new Produto(2L, "Pasta de Dente", "Usa pra escovar os dentes", new BigDecimal("7.90"));
                Executable metodoValido = () -> carrinhoCompra.adicionarProduto(produto2, 1);
                assertAll("Adicionar carrinho com produtos válidos",
                        () -> assertDoesNotThrow(metodoValido),
                        () -> assertEquals(2, carrinhoCompra.getItens().size()));
            }
        }

        @Nested
        @DisplayName("Quando for remover um produto que não tenha nome")
        class RemoverUmProdutoSemNome{

            @Test
            @DisplayName("Então deve lançar exception")
            void nomeNuloLancarException(){
                produto.setNome(null);
                Executable metodoComException = () -> carrinhoCompra.removerProduto(produto);
                assertThrows(NullPointerException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando for remover um produto que não tenha descrição")
        class RemoverProdutoSemDescricao{

            @Test
            @DisplayName("Então deve lançar exception")
            public void descricaoNuloLancarException(){
                produto.setDescricao(null);
                Executable metodoComException = () -> carrinhoCompra.removerProduto(produto);
                assertThrows(NullPointerException.class, metodoComException);
            }

        }

        @Nested
        @DisplayName("Quando for remover um produto que não tenha valor")
        class RemoverProdutoSemValor{

            @Test
            @DisplayName("Então deve lançar exception")
            public void valorNuloLancarException(){
                produto.setValor(null);
                Executable metodoComException = () -> carrinhoCompra.removerProduto(produto);
                assertThrows(NullPointerException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando for remover um produto e esse mesmo produto não existir")
        class RemoverUmProdutoQueNaoExiste{
            @Test
            @DisplayName("Então deve lançar exception")
            public void removerProdutoQueNaoExiste(){
                Produto produtoNovo = new Produto(2L, "Escova de cabelo", "Usa pra escovar os cabelos", new BigDecimal("10.90"));
                Executable metodoComException = () -> carrinhoCompra.removerProduto(produtoNovo);
                assertThrows(IllegalArgumentException.class, metodoComException);
            }
        }

        @Nested
        @DisplayName("Quando for remover um produto valido")
        class RemoverProdutoValido{
            @Test
            @DisplayName("Então deve remover o produto da lista")
            public void removerProdutoDaLista(){
                carrinhoCompra.removerProduto(produto);
                assertEquals(0, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E não lançar nenhuma exception")
            public void removerProdutoDaListaSemException(){
                Executable metodoValido = () -> carrinhoCompra.removerProduto(produto);
                assertDoesNotThrow(metodoValido);
            }
        }

        @Nested
        @DisplayName("Quando for pegar o valor tottal dos produtos")
        class PegarValorTotalDosProdutos{
            @Test
            @DisplayName("Então não lance exception")
            public void PegarValorTotalDosProdutosSemException(){
                Produto produtoNovo = new Produto(2L, "Escova de cabelo", "Usa pra escovar os cabelos", new BigDecimal("10.90"));
                carrinhoCompra.adicionarProduto(produtoNovo, 1);
                BigDecimal valorTotal = carrinhoCompra.getValorTotal();
                assertEquals(new BigDecimal("16.80"), valorTotal);
            }
        }

        @Nested
        @DisplayName("Quando for pegar a quantidade total de todos os produtos")
        class PegarQuantidadeTotalDeTodosOsProdutos{
            @Test
            @DisplayName("Então não deve lançar exception")
            public void pegarQuantidadeTotalDosProdutos(){
                Produto produtoNovo = new Produto(2L, "Escova de cabelo", "Usa pra escovar os cabelos", new BigDecimal("10.90"));
                carrinhoCompra.adicionarProduto(produtoNovo, 2);
                int quantidadeTotal = carrinhoCompra.getQuantidadeTotalDeProdutos();
                assertEquals(3, quantidadeTotal);
            }
        }

        @Nested
        @DisplayName("Quando esvaziar o carrinho de compras")
        class EsvaziarCarrinhoDeCompras{
            @Test
            @DisplayName("Então deve retornar nulo")
            public void esvaziarCarrinhoCompras(){
                carrinhoCompra.esvaziar();
                List<ItemCarrinhoCompra> listaVazia = new ArrayList<>();
                assertEquals(listaVazia, carrinhoCompra.getItens());
            }
        }

    }

}