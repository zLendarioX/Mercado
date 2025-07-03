package model;

public class VendaProduto {

	private String idVendaProduto;
	private String idVenda;
	private String idProduto;
	private String precoUn;

	private String quantidade;
	private String valorTotal;
	
	//metodo construtor
	public VendaProduto(String idVendaProduto, String idVenda, String idProduto, String quantidade, String valorTotal, String precoUn) {
		super();
		this.idVendaProduto = idVendaProduto;
		this.idVenda = idVenda;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.precoUn = precoUn;
	}
	public VendaProduto() {
		super();
	}
	public String getIdVendaProduto() {
		return idVendaProduto;
	}
	public void setIdVendaProduto(String idVendaProduto) {
		this.idVendaProduto = idVendaProduto;
	}
	public String getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(String idVenda) {
		this.idVenda = idVenda;
	}
	public String getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public String getPrecoUn() {
		return precoUn;
	}
	public void setPrecoUn(String precoUn) {
		this.precoUn = precoUn;
	}
	
}