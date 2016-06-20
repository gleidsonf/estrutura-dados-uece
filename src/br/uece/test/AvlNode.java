package br.uece.test;

public class AvlNode {

	private AvlNode esquerda;
	private AvlNode direita;
	private AvlNode pai;
	private int chave;
	private int balanceamento;

	public AvlNode(int k) {
		setEsquerda(setDireita(setPai(null)));
		setBalanceamento(0);
		setChave(k);
	}

	public String toString() {
		return getChave() + " ";
	}

	public int getChave() {
		return chave;
	}

	public void setChave(int chave) {
		this.chave = chave;
	}

	public int getBalanceamento() {
		return balanceamento;
	}

	public void setBalanceamento(int balanceamento) {
		this.balanceamento = balanceamento;
	}

	public AvlNode getPai() {
		return pai;
	}

	public AvlNode setPai(AvlNode pai) {
		this.pai = pai;
		return pai;
	}

	public AvlNode getDireita() {
		return direita;
	}

	public AvlNode setDireita(AvlNode direita) {
		this.direita = direita;
		return direita;
	}

	public AvlNode getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(AvlNode esquerda) {
		this.esquerda = esquerda;
	}
}