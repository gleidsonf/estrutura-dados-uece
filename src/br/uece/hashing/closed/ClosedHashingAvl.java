package br.uece.hashing.closed;

import br.uece.avl.structures.AVLArvore;
import br.uece.hashing.util.Primo;

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
			System.out.println("Elemento " + value + " adicionado na arvore de posicao " + posicao);
		} else if (vetor[posicao].find(value)) {
			System.out.println("Arvore ja possui o elemento "+ value + ", redirecionando para metodo quadratico");
			addMetodoQuadratico(value);
		} else if (vetor[posicao].getAlturaArvore() < 3) {
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na arvore de posicao " + posicao);

		}

	}

	public void remove(int value) {
		int posicao = geraPosicao(value);
		if(vetor[posicao].find(value)){
			vetor[posicao].remove(value);
		}else{
			posicao = funcaoQuadratica(value);
			if(vetor[posicao].find(value))
				vetor[posicao].remove(value);
			else
				System.out.println("Elemento "+value+" não econtrado");
		}

	}

	private void addMetodoQuadratico(int value) {
		int novaPosicao = funcaoQuadratica(value);
		vetor[novaPosicao].add(value);
		System.out.println("Elemento " + value + " adicionado na arvore de posicao " + novaPosicao);
	}

	private int funcaoQuadratica(int value) {
		int tentativa = 1;
		int posicao = -1;
		for (int i = 0; i < 4; i++) {
			posicao = (value + (tentativa * tentativa)) % vetor.length;

			if (vetor[posicao] == null) {
				vetor[posicao] = new AVLArvore();
				return posicao;

				// TODO && vetor[posicao].qtdElementos() < 3) deve ser usado?

			} else if (!vetor[posicao].find(value)) {
				return posicao;

			} else {
				tentativa++;
			}

		}
		return posicao;
	}

	private void addMetodoLinear(int value) {
		int posicao = funcaoLinear(value);
		vetor[posicao].add(value);

	}

	private int funcaoLinear(int value) {
		int posicao = 0;
		for (int i = 0; i < vetor.length; i++) {
			posicao = (value + i) % vetor.length;
			if (vetor[posicao] == null) {
				vetor[posicao] = new AVLArvore();
				return posicao;
			} else if (!vetor[posicao].find(value)) {
				vetor[posicao].add(value);
				return posicao;
			}
		}
		return posicao;
	}

	private void addMetodoDuploHashing(int value) {
		int posicao = duploHashing(value);
		vetor[posicao].add(value);
	}

	private int duploHashing(int value) {
		int primo = Primo.proximoPrimo(geraPosicao(value));
		int hd = primo - value & primo;
		int posicao = -1;
		for (int i = 0; i < vetor.length; i++) {
			posicao = (i * hd) % vetor.length;
			if(vetor[posicao] == null){
				vetor[posicao] = new AVLArvore();
				return posicao;
			}
			if(vetor[posicao].find(value)){
				return posicao;
			}
		}
		return posicao;
	}

	private boolean isEmptyPosition(int posicao) {
		return vetor[posicao] == null;
	}

	private int geraPosicao(int value) {
		return value % vetor.length;
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
		c.add(11);
		c.add(16);
		c.add(19);
		c.add(37);
		c.add(44);
		c.add(38);
		c.add(22);
		c.add(61);
		c.remove(423);
		c.imprimir();

	}
}
