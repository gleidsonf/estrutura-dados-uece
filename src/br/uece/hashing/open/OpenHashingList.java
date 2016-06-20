package br.uece.hashing.open;

import br.uece.avl.structures.Node;

public class OpenHashingList {
	private Node[] vetor;

	public OpenHashingList() {
		vetor = new Node[11];
	}

	public void add(int value) {

		int posicao = geraPosicao(value);

		if (vetor[posicao] == null) {
			vetor[posicao] = new Node(value, null);
		} else { // se tiver nao estiver vazio

			Node aux = vetor[posicao];

			while (aux.getNext() != null) {
				aux = aux.getNext();
				System.out.println("aux = " + aux.getValue());
			}
			aux.setNext(new Node(value, null));
		}

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
		int posicao = value % vetor.length;
		Node aux = vetor[posicao];
		if (vetor[posicao].getValue() == value) {
			vetor[posicao] = vetor[posicao].getNext();
		} else {
			while (aux != null) {
				System.out
						.println("\n\n entrou na posicao " + posicao + "\n\n");
				if (aux.getNext().getValue() == value) {
					System.out.println("\n\n achou \n\n");
					System.out.println(aux.getValue());
					aux.setNext(aux.getNext().getNext());
					// vetor[i] = aux.getNext();
					break;
				}
				aux = aux.getNext();
			}
		}

	}

	public int fatorCarga() {
		Node[] aux = vetor;
		int cont = 0;
		for (int i = 0; i < aux.length; i++) {
			cont = carga(vetor[i], cont);
		}
		return cont;
	}

	private int carga(Node node, int maximo) {
		int cont = 0;
		while (node != null) {
			cont++;
			node = node.getNext();
		}
		return Math.max(cont, maximo);
	}

	public int larguraOcupada() {
		int largura = 0;
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null) {
				largura += 1;
			}
		}
		return largura;
	}

	public int qtdElementos() {
		int qtd = 0;
		for (int i = 0; i < vetor.length; i++) {
			Node aux = vetor[i];
			while (aux != null) {
				qtd += 1;
				aux = aux.getNext();
			}
		}
		return qtd;
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
		OpenHashingList c = new OpenHashingList();
//		Ex: S= {0,1,85,6,36,46,89,112,44}
		c.add(0);
		c.add(1);
		c.add(85);
		c.add(6);
		c.add(36);
		c.add(46);
		c.add(89);
		c.add(112);
		c.add(44);
		c.imprime();
		System.out.println("Fator de carga: " + c.fatorCarga());

	}
}
