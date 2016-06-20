package br.uece.hashing.closed;

import br.uece.avl.structures.Node;

public class ClosedHashingList {
	private Node[] vetor;

	public ClosedHashingList() {
		vetor = new Node[13];
	}

	public void add(int value) {

		int posicao = geraPosicao(value);
		System.out.println("Status: posicao " + posicao + " livre? "
				+ isEmptyPosition(posicao));
		if (isEmptyPosition(posicao)) {
			System.out.println("posicao " + posicao + " - Valor - " + value);
			vetor[posicao] = new Node(value, null);
		} else if (qtdElementos() == vetor.length) {
			System.out.println("Impossível inserir o elemento " + value
					+ ", hash completo.");
		} else {
			funcaoQuadratica(value);
		}
	}

	private int funcaoQuadratica(int value) {
		int tentativa = 1;

		int posicao = geraPosicao(value);

		for (int i = 0; i < qtdElementos(); i++) {

			if (!isEmptyPosition(posicao) && tentativa <= 3) {

				posicao = (value + (tentativa * tentativa)) % vetor.length;
				tentativa++;

			} else if (tentativa > 3) {
				System.out.println("Impossível inserir o elemento " + value);
				break;
			} else {
				vetor[posicao] = new Node(value, null);
			}
		}
		return posicao;
	}

	private boolean isEmptyPosition(int posicao) {
		return vetor[posicao] == null;
	}

	private int geraPosicao(int value) {
		int position = -1;
		// TODO verificar viabilidade de inserir numeros negativos
		if (value < 0)
			position = value * (-1);
		else
			position = value;

		return position % vetor.length;
	}

	public void remove(int value) {
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null && vetor[i].getValue() == value) {
				vetor[i] = null;
			}
		}
	}

	public int larguraOcupada() {
		int largura = 0;
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null) {
				largura++;
			}
		}
		return largura;
	}

	public int qtdElementos() {
		int quantidade = 0;
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null) {
				quantidade++;
			}
		}
		return quantidade;
	}

	public void imprime() {
		for (int i = 0; i < vetor.length; i++) {
			System.out.printf("Indice " + i + "=> ");
			Node aux = vetor[i];
			while (aux != null) {
				System.out.printf(aux.toString());
				aux = aux.getNext();
			}
			System.out.println("");
		}

	}

	public static void main(String[] args) {
		ClosedHashingList c = new ClosedHashingList();
		
		
		c.add(46);
		c.add(8);
		c.add(74);
		c.add(15);
		c.add(23);
		c.add(51);
		c.add(83);
		c.add(69);
		c.add(9);
		c.add(24);
		c.add(33);
		c.add(12);
//		c.add(89);
//		c.add(18);
//		c.add(49);
//		c.add(60);
//		c.add(69);
//		c.add(11);
//		c.add(34);
//		c.add(99);
//		c.add(72);
		c.imprime();

		c.remove(60);
		System.out.println("-------Apos a exclusao do 72-------");
		c.imprime();
	}

}
