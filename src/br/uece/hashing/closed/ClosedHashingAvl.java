package br.uece.hashing.closed;

import br.uece.avl.structures.AVLArvore;

public class ClosedHashingAvl {
	private final int SIZE = 5;
	private AVLArvore[] vetor;

	public ClosedHashingAvl() {
		vetor = new AVLArvore[SIZE];
	}

	public void add(int value) {
		int posicao = geraPosicao(value);
		if (isEmptyPosition(posicao)) {
			vetor[posicao] = new AVLArvore();
			vetor[posicao].add(value);
		} else if (vetor[posicao].find(value)) {
			funcaoQuadratica(value);
		} else if(vetor[posicao].getAlturaArvore() <2){
			vetor[posicao].add(value);
		}

	}

	public void remove(int value) {
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null) {
				if (vetor[i].find(value)) {
					vetor[i].remove(value);
				}

			}
		}

	}

	private void funcaoQuadratica(int value) {
		int tentativa = 1;

		for (int i = 0; i < 4; i++) {
			int posicao = (value + (tentativa * tentativa)) % vetor.length;
			System.out.println("Entrando na posicao " + posicao);

			if (vetor[posicao] == null) {
				vetor[posicao] = new AVLArvore();
				vetor[posicao].add(value);
				break;
			} else if (!vetor[posicao].find(value) && vetor[posicao].qtdElementos() < 3) {
				vetor[posicao].add(value);
				break;
			} else {
				tentativa++;
				// System.out.println("Impossível inserir o elemento " + value);
			}

		}

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

	public void imprimir() {
		for (int i = 0; i < vetor.length; i++) {
			System.out.println("Posicao " + i + " :");
			if (!isEmptyPosition(i)) {
				vetor[i].mostraArvore();
				System.out.println("");
			}
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		ClosedHashingAvl c = new ClosedHashingAvl();

		c.add(18);
		c.add(9);
		c.add(91);
		c.add(17);
		c.add(25);
		c.add(16);
		c.add(42);
		c.add(39);
		c.add(27);
		c.add(13);
		c.add(12);
		c.add(92);
		c.add(11);
		c.add(21);
		c.add(18);
		c.add(3);
		c.add(16);
		c.add(19);
		c.add(37);
		c.add(44);
		c.add(38);
		c.add(22);
		c.add(61);

		c.imprimir();

	}
}
