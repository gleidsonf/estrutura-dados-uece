package br.uece.hashing.halfopen;

import br.uece.avl.structures.AVLArvore;
import br.uece.avl.structures.AVLNode;
import br.uece.hashing.util.Primo;

public class HalfHashingAvl {
	private final int SIZE = 5;
	AVLArvore[] vetor;

	public HalfHashingAvl() {
		vetor = new AVLArvore[SIZE];
	}
	/*
	 * A posicao eh criada de acordo com o valor e o tamanho do vetor,
	 * Caso nao seja nulo ou na posicao nao exista o valor ou o fator
	 * de carga seja menor que 10 ele eh adicionado. 
	 * 
	 * Caso for nulo, eh criada uma instancia da arvore.
	 * 
	 * Caso a posicao ja contenha o valor, eh aplicado o metodo 
	 * quadratico para adicionar o elemento. 
	 */
	public void add(int value) {
//		if (larguraOcupada() >= ((vetor.length / 2) + 1)) {
//			System.out.println("Dobrou de tamanho => "
//					+ ((vetor.length / 2) + 1));
//			rehashing();
//		}
		System.out.println("\n\n\n fator de carga do elemento " + value + " eh " + fatorCarga());
		int posicao = geraPosicao(value);
		if (vetor[posicao] != null && !vetor[posicao].find(value) && fatorCarga() <= 3 ) {
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao " + posicao + "\n");

		} else if (vetor[posicao] == null) {
			vetor[posicao] = new AVLArvore();
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao " + posicao + "\n");

		} else if (vetor[posicao].find(value)) {
			addMetodoQuadratico(value);
		}
	}

	/*
	 * Caso a largura do vetor contenha metade do seu tamanho + 1 elemento,
	 * eh chamado o metodo rehashing, que criara outro vetor com um numero
	 * primo que seja maior que o dobro do tamanho atual.
	 * 
	 * Para isso o vetor atual eh adicionado num temporario.
	 * Eh calculado o novo tamanho do vetor e logo apos 
	 * cada posicao do vetor eh adicionada ao metodo percorreArvore, que 
	 * sera a responsavel por fazer o rehashing efetivamente.
	 */
	
	private void rehashing() {
		System.out.println("Metodo rehashing. A arvore ate este momento e': ");
		imprime();
		AVLArvore[] aux = new AVLArvore[vetor.length];
		/*
		 * Salva o vetor principal num temporario
		 */
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] != null) {
				aux[i] = vetor[i];
			}
		}
		System.out.println("\n");
		
		int novoValor = Primo.proximoPrimo(vetor.length);
		vetor = new AVLArvore[novoValor];
		for (int i = 0; i < aux.length; i++) {

			if (aux[i] != null) {
				percorreArvore(aux[i].raiz);

			}
		}
		System.out.println("----Fim do Rehashing----");
	}

	/*
	 * Um no eh recebido e recursivamente readicionada ao vetor
	 * que ja contem um novo tamanho primo 2x maior que o antigo.
	 */
	public void percorreArvore(AVLNode node) {
		System.out.println("Readicionando o elemento " + node.valor);
		add(node.valor);
			if (node.direito != null) {
				percorreArvore(node.direito);
			} else if (node.esquerdo != null) {
				percorreArvore(node.esquerdo);
			}
	}

 /*
  * Abstracao ao metodo gerarPosicaoQuadratica.
  * O metodo addMetodoQuadratico envia o valor para gerar a nova posicao
  * com base na funcao quadratica e faz uma nova verificacao para saber se 
  * a nova posicao gerada eh nula ou ja instanciada. E por fim o valor eh
  * adicionado 
  */
	private void addMetodoQuadratico(int value) {
		int posicao = gerarPosicaoQuadratica(value);

		if (vetor[posicao] == null) {
			vetor[posicao] = new AVLArvore();
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao " + posicao + "\n");
		} else if (!vetor[posicao].find(value)) {
			vetor[posicao].add(value);
			System.out.println("Elemento " + value + " adicionado na posicao " + posicao + "\n");
		} 
	}

	/*
	 * Primeiro metodo para gerar posicao com base no valor e tamanho do vetor
	 */
	private int geraPosicao(int value) {
		return value % vetor.length;
	}
	
	/*
	 * Recebe um novo valor que ja existe na primeira posicao relativa.
	 * E eh recalculado com a base (valor + x^x)% tamanhoVetor
	 * Serao 4 tentativas, caso o valor ja exista nas 4 posicoes criadas
	 * ele nao sera inserido.
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
	 * Percorre o vetor a procura da posicao. Caso encontre eh chamada o 
	 * metodo remover da arvore referente a posicao. Caso contrario eh
	 * feita uma nova tentativa com a posicao na funcao quadratica.
	 */
	public void remove(int value) {
		int posicao = gerarPosicaoQuadratica(value);
		for (int i = 0; i < vetor.length; i++) {
			System.out.println("Procurando na posicao " + posicao + "o elemento " + value);
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
			if(aux[i] != null){
				
				cont = Math.max(cont, aux[i].getAlturaArvore()+1);	
				System.out.println("Carga do indice " + i + " é " + cont);
			}
		}
		return cont;
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
		HalfHashingAvl c = new HalfHashingAvl();

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
		
/*		c.add(6);
		c.add(15);
		c.add(24);
		c.add(13);
		c.add(23);
		c.add(25);
		c.add(15);
		c.add(19);
*/		c.imprime();
		System.out.println("Fator de carga " + c.fatorCarga());
		System.out.println("Largura Ocupada " + c.larguraOcupada());
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
