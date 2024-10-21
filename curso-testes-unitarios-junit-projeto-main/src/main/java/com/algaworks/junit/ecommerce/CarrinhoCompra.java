package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		//TODO deve retornar uma nova lista para que a antiga não seja alterada
		ArrayList<ItemCarrinhoCompra> copiaArray = new ArrayList<>();
		for(ItemCarrinhoCompra item : itens){
			ItemCarrinhoCompra novoItem = new ItemCarrinhoCompra(item.getProduto(), item.getQuantidade());
			copiaArray.add(novoItem);
		}
		return copiaArray;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {
		//TODO parâmetros não podem ser nulos, deve retornar uma exception
		//TODO quantidade não pode ser menor que 1
		//TODO deve incrementar a quantidade caso o produto já exista
		if(produto.getNome().isEmpty() || produto.getDescricao().isEmpty() || produto.getValor() == null){
			throw new NullPointerException("Produo possui atributos vazios!");
		}
		if(quantidade < 1){
			throw new IllegalArgumentException("Quantidade precisa ser maior que 1!");
		}
		Optional<ItemCarrinhoCompra> itemFiltro = itens.stream()
								   .filter(item -> item.getProduto()
										   .equals(produto))
								   .findFirst();
		if(!itemFiltro.isEmpty()){
			itemFiltro.get().adicionarQuantidade(1);
		} else{
			itens.add(new ItemCarrinhoCompra(produto, quantidade));
		}
	}

	public void removerProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		//TODO caso o produto não exista, deve retornar uma exception
		//TODO deve remover o produto independente da quantidade
		if(produto.getNome().isEmpty() || produto.getDescricao().isEmpty() || produto.getValor() == null){
			throw new NullPointerException("Produto possui atributos vazios!");
		}
		Optional<ItemCarrinhoCompra>filtro = itens.stream().filter(item -> item.getProduto().equals(produto)).findFirst();
		if(filtro.isEmpty()){
			throw new IllegalArgumentException("Produto não encontrado!");
		}
		this.itens.remove(filtro.get());
	}

//	public void aumentarQuantidadeProduto(Produto produto) {
//		//TODO parâmetro não pode ser nulo, deve retornar uma exception
//		//TODO caso o produto não exista, deve retornar uma exception
//		//TODO deve aumentar em um quantidade do produto
//	}
//
//	NÃO VEJO NECESSIDADE DE MANTER ESSES MÉTODOS

//    public void diminuirQuantidadeProduto(Produto produto) {
//		//TODO parâmetro não pode ser nulo, deve retornar uma exception
//		//TODO caso o produto não exista, deve retornar uma exception
//		//TODO deve diminuir em um quantidade do produto, caso tenha apenas um produto, deve remover da lista
//	}

    public BigDecimal getValorTotal() {
		//TODO implementar soma do valor total de todos itens
		BigDecimal valorTotal = BigDecimal.ZERO;
		for(ItemCarrinhoCompra item : itens){
			valorTotal = valorTotal.add(item.getProduto().getValor());
		}
		return valorTotal;
    }

	public int getQuantidadeTotalDeProdutos() {
		//TODO retorna quantidade total de itens no carrinho
		//TODO Exemplo em um carrinho com 2 itens, com a quantidade 2 e 3 para cada item respectivamente, deve retornar 5
		int quantidadeTotal = 0;
		for(ItemCarrinhoCompra item : itens){
			quantidadeTotal += item.getQuantidade();
		}
		return quantidadeTotal;
	}

	public void esvaziar() {
		//TODO deve remover todos os itens
//		itens.clear();
		itens.removeAll(itens);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}