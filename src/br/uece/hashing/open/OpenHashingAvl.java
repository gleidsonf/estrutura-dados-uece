package br.uece.hashing.open;

import br.uece.avl.structures.AVLArvore;

public class OpenHashingAvl {

	private AVLArvore[] vetor;
	public final int SIZE = 7;

	public OpenHashingAvl() {
		vetor = new AVLArvore[SIZE];
	}

	/*
	 * A posicao eh criada de acordo com o valor e o tamanho do vetor, Caso nao
	 * seja nulo ou na posicao nao exista o valor ou o fator de carga seja menor
	 * que 10 ele eh adicionado.
	 * 
	 * Caso for nulo, eh criada uma instancia da arvore.
	 * 
	 * Caso a posicao ja contenha o valor, eh aplicado o metodo quadratico para
	 * adicionar o elemento.
	 */
	public void add(int value) {


		int posicao = geraPosicao(value);
		if (vetor[posicao] != null && !vetor[posicao].find(value)
				&& fatorCarga() <= 10) {
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao "
					+ posicao + "\n");

		} else if (vetor[posicao] == null) {
			vetor[posicao] = new AVLArvore();
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao "
					+ posicao + "\n");

		} else if (vetor[posicao].find(value)) {
			addMetodoQuadratico(value);
		}
	}
	/*
	 * Abstracao ao metodo gerarPosicaoQuadratica. O metodo addMetodoQuadratico
	 * envia o valor para gerar a nova posicao com base na funcao quadratica e
	 * faz uma nova verificacao para saber se a nova posicao gerada eh nula ou
	 * ja instanciada. E por fim o valor eh adicionado
	 */
	private void addMetodoQuadratico(int value) {
		int posicao = gerarPosicaoQuadratica(value);

		if (vetor[posicao] == null) {
			vetor[posicao] = new AVLArvore();
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao "
					+ posicao + "\n");
		} else if (!vetor[posicao].find(value)) {
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao "
					+ posicao + "\n");
		}
	}

	/*
	 * Primeiro metodo para gerar posicao com base no valor e tamanho do vetor
	 */
	private int geraPosicao(int value) {
		return value % vetor.length;
	}

	/*
	 * Recebe um novo valor que ja existe na primeira posicao relativa. E eh
	 * recalculado com a base (valor + x^x)% tamanhoVetor Serao 4 tentativas,
	 * caso o valor ja exista nas 4 posicoes criadas ele nao sera inserido.
	 */
	private int gerarPosicaoQuadratica(int value) {
		int tentativa = 1;
		int posicao = -1;
		for (int i = 0; i < 4; i++) {
			posicao = (value + (tentativa * tentativa)) % vetor.length;

			if (vetor[posicao] == null || !vetor[posicao].find(value)) {
				return posicao;
			} else {
				tentativa++;
			}
		}

		if (tentativa == 4) {
			System.out.println("Nao foi possivel inserir o elemento " + value);
		}
		return posicao;

	}

	/*
	 * Percorre o vetor a procura da posicao. Caso encontre eh chamada o metodo
	 * remover da arvore referente a posicao. Caso contrario eh feita uma nova
	 * tentativa com a posicao na funcao quadratica.
	 */
	public void remove(int value) {
		int posicao = gerarPosicaoQuadratica(value);
		for (int i = 0; i < vetor.length; i++) {
			System.out.println("Procurando na posicao " + posicao
					+ "o elemento " + value);
			if (vetor[posicao].find(value)) {
				vetor[posicao].remove(value);
				System.out.println("Elemento" + value + " removido");
				if (vetor[posicao].getAlturaArvore() < 0) {
					vetor[posicao] = null;
				}
				break;
			} else {
				posicao = gerarPosicaoQuadratica(value);
			}
		}
	}

	/*
	 * Refere-se a quantidade de elementos na maior arvore
	 */
	public int fatorCarga() {
		AVLArvore[] aux = vetor;
		int cont = 0;
		for (int i = 0; i < aux.length; i++) {
			if (aux[i] != null) {
				cont = Math.max(cont, (aux[i].getAlturaArvore() + 1));
			}
		}
		return cont;
	}

	public Double fatorBalanceamento() {
		int somatorioCarga = 0;
			for (int i = 0; i < vetor.length; i++) {
				if(vetor[i] != null){
					somatorioCarga += vetor[i].getAlturaArvore() + 1;					
				}
			}

			Double valor = (double) somatorioCarga / (vetor.length * fatorCarga());
			return valor;
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

	public void imprime() {
		for (int i = 0; i < vetor.length; i++) {
			System.out.printf("Indice " + i + "=> ");
			AVLArvore aux = vetor[i];
			if (aux != null) {
				aux.mostraArvore();
			}
			System.out.println("");
		}

	}

	public static void main(String[] args) {
		OpenHashingAvl c = new OpenHashingAvl();


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
		System.out.println("Fator de carga " + c.fatorCarga());
		System.out.println("Largura Ocupada " + c.larguraOcupada());
		System.out.println("Fator Balanceamento: " + c.fatorBalanceamento());
		// c.add(0);
		// c.add(1);
		// c.add(85);
		// c.add(6);
		// c.add(89);
		// // System.out.println("Largura "+c.largura());
		// // c.add(112);
		// // System.out.println("Largura "+c.largura());
		// c.add(44);
		// c.add(44);
		// c.add(44);
		// // c.add(453);
		// // c.add(13412);
		// // c.add(9131);
		// // c.add(43);
		// // c.add(44);
		// System.out.println("Largura " + c.larguraOcupada());
		// System.out.println("Quantidade de elementos: " + c.qtdElementos());
		// c.imprime();
		// c.remove(44);
		// System.out.println("\n\n\n\n");
		// c.imprime();
		// System.out.println(c.fatorCarga());
	}
}