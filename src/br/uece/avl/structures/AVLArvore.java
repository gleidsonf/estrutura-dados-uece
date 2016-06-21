package br.uece.avl.structures;


public class AVLArvore {
	public AVLNode raiz;

	// Construtor da Arvore AVL
	public AVLArvore() {
		raiz = null;
	}

	// Método que chama uma inserção na árvore AVL
	public void add(int valor) {
		raiz = add(raiz, valor);
	}

	/**
	 * Método de inserção na árvore propriamente dita. Insere um novo nó na
	 * árvore tratando todas as possíveis situações existentes num método de
	 * inserção. Após cada inserção há uma verificação se a Árvore não ficou
	 * desbalanceada, caso haja, será feita as devidas rotações necessárias para
	 * retomar o balanceamento
	 * 
	 * @param no
	 *            No a ser inserido
	 * @param x
	 *            valor do no a ser inserido
	 * @return novo no na arvore
	 */
	public AVLNode add(AVLNode no, int x) {
		if (no == null) {
			return new AVLNode(x);
		} else if (x < no.valor) {
			no.esquerdo = add(no.esquerdo, x);
			if (verificaAltura(no.esquerdo) - verificaAltura(no.direito) == 2)
				if (x < no.esquerdo.valor)
					no = rotacaoFilhoEsquerdo(no);
				else
					no = rotacaoDuplaFilhoEsquerdo(no);
		} else if (x > no.valor) {
			no.direito = add(no.direito, x);
			if (verificaAltura(no.direito) - verificaAltura(no.esquerdo) == 2)
				if (x > no.direito.valor)
					no = rotacaoFilhoDireito(no);
				else
					no = rotacaoDuplaFilhoDireito(no);
		} else
			// não faz nada
			no.altura = Math.max(verificaAltura(no.esquerdo),
					verificaAltura(no.direito)) + 1;
		return no;

	}

	/**
	 * Remove o elemento da arvore analisando os caos do no a ser removido ser
	 * raiz maior q a raiz ou menor q a raiz.
	 * 
	 * @param valor
	 *            valor a ser removido
	 * @return a nova arvore
	 */
	public AVLNode remove(int valor) {
		AVLNode temp = new AVLNode();
		if (valor == raiz.valor) {
			raiz = null;
			//			raiz = remove(raiz, valor);
			return raiz;
		}
		if (valor < raiz.valor) {

			temp = remove(raiz.esquerdo, valor);

		}
		temp = remove(raiz.direito, valor);
		return temp;

	}

	/**
	 * Remove a raiz da arvore
	 */
	public void remove() {
		raiz = remove(raiz.valor);
	}

	/**
	 * Metodo interno para remover o elemento, após remoção de elemento é feita
	 * uma verificação para saber se a árvore está balanceada, caso seja
	 * necessário serão feitas rotações simples ou duplas na árvore.
	 * 
	 * @param p
	 *            raiz da arvore
	 * @param x
	 *            valor a ser removido
	 * @return a nova arvore
	 */
	public AVLNode remove(AVLNode p, int x) {
		AVLNode child = null;

		if (p == null)
			return null; // não existe

		if (x < p.valor) {
			p.esquerdo = remove(p.esquerdo, x);
			p.altura = Math.max(verificaAltura(p.esquerdo) - 1,
					verificaAltura(p.direito) - 1);
			if (Math.abs(verificaAltura(p.esquerdo) - verificaAltura(p.direito)) == 2) {
				if (verificaAltura(p.direito.esquerdo) <= verificaAltura(p.direito.direito))
					p = rotacaoFilhoDireito(p);
				else
					p = rotacaoDuplaFilhoDireito(p);
			}

		} else if (x > p.valor) {
			p.direito = remove(p.direito, x);
			p.altura = Math.max(verificaAltura(p.esquerdo),
					verificaAltura(p.direito) - 1);
			if (Math.abs(verificaAltura(p.esquerdo) - verificaAltura(p.direito)) == 2)
				if (verificaAltura(p.esquerdo.direito) <= verificaAltura(p.esquerdo.esquerdo))
					p = rotacaoFilhoEsquerdo(p);
				else
					p = rotacaoDuplaFilhoEsquerdo(p);
		} else {
			if (p.esquerdo != null && p.direito != null) { // nó tem 2 filhos
				p.valor = findMin(p.direito).valor;
				p.direito = remove(p.direito, p.valor);
				p.altura = Math.max(verificaAltura(p.esquerdo),
						verificaAltura(p.direito) - 1);
				if (Math.abs(verificaAltura(p.esquerdo)
						- verificaAltura(p.direito)) == 2)
					if (verificaAltura(p.esquerdo.direito) <= verificaAltura(p.esquerdo.esquerdo))
						p = rotacaoFilhoEsquerdo(p);
					else
						p = rotacaoDuplaFilhoEsquerdo(p);
			} else {
				if (p.esquerdo == null) {
					child = p.direito;
				}
				if (p.direito == null) {
					child = p.esquerdo;
				}
				return child;
			}
		}
		return p;
	}

	/**
	 * Rotaciona a arvore com filho esquerdo, caso da rotacao simples
	 */
	private AVLNode rotacaoFilhoEsquerdo(AVLNode k2) {
		AVLNode k1 = k2.esquerdo;
		k2.esquerdo = k1.direito;
		k1.direito = k2;
		k2.altura = Math.max(verificaAltura(k2.esquerdo),
				verificaAltura(k2.direito)) + 1;
		k1.altura = Math.max(verificaAltura(k1.esquerdo), k2.altura) + 1;
		return k1;
	}

	/**
	 * Rotaciona a arvore com filho direito, caso da rotacao simples
	 */
	private AVLNode rotacaoFilhoDireito(AVLNode k1) {
		AVLNode k2 = k1.direito;
		k1.direito = k2.esquerdo;
		k2.esquerdo = k1;
		k1.altura = Math.max(verificaAltura(k1.esquerdo),
				verificaAltura(k1.direito)) + 1;
		k2.altura = Math.max(verificaAltura(k2.direito), k1.altura) + 1;
		return k2;
	}

	/**
	 * Rotacao dupla na arvore com filho esquerdo: primeiro o filho esquerdo com
	 * o filho direito, depois k3 com o novo filho esquerdo
	 */
	private AVLNode rotacaoDuplaFilhoEsquerdo(AVLNode k3) {
		AVLNode k1 = k3.esquerdo;
		AVLNode k2 = k1.direito;
		k3.esquerdo = k2.direito;
		k1.direito = k2.esquerdo;
		k2.direito = k3;
		k2.esquerdo = k1;
		k3 = k2;
		k1.altura = Math.max(verificaAltura(k1.esquerdo),
				verificaAltura(k1.direito)) + 1;
		k2.altura = Math.max(verificaAltura(k2.direito), k1.altura) + 1;
		k3.altura = Math.max(verificaAltura(k3.esquerdo), k2.altura) + 1;

		return k3;
	}

	/**
	 * Rotacao dupla na arvore com filho direito: primeiro o filho direito com o
	 * filho esquerdo, depois k1 com o novo filho direito
	 */

	private AVLNode rotacaoDuplaFilhoDireito(AVLNode k1) {
		AVLNode k2 = k1.direito;
		AVLNode k3 = k2.esquerdo;
		k1.direito = k3.esquerdo;
		k2.esquerdo = k3.direito;
		k3.direito = k2;
		k3.esquerdo = k1;
		k1 = k3;
		k1.altura = Math.max(verificaAltura(k1.esquerdo),
				verificaAltura(k1.direito)) + 1;
		k2.altura = Math.max(verificaAltura(k2.direito), k1.altura) + 1;
		k3.altura = Math.max(verificaAltura(k3.esquerdo), k2.altura) + 1;

		return k3;

	}

	/**
	 * Retorna a altura do node t, ou -1, caso null.
	 */
	public int altura(AVLNode t, int a) {
		int altura = -1;
		if (t != null) {
			altura = a;
			altura = Math.max(altura, altura(t.esquerdo, a + 1));
			altura = Math.max(altura, altura(t.direito, a + 1));
		}
		return altura;

	}

	public int getAlturaArvore() {
		return verificaAltura(raiz);
	}

	public int verificaAltura(AVLNode node) {
		return altura(node, 0);
	}

	/**
	 * @param no
	 */
	public void percorreArvorePosOrdem(AVLNode no) {
		if (no != null) {
			// System.out.print(no.valor + " ");//pre-order :)
			percorreArvorePosOrdem(no.esquerdo);
			System.out.print(no.valor + " "); // in-order :)
			percorreArvorePosOrdem(no.direito);
			// System.out.print(no.valor + " ");//pos-order :)
		}
	}

	/**
	 * Imprime o conteudo da arvore
	 */
	public void mostraArvore() {
		if (isEmpty())
			System.out.println("arvore vazia");
		else
			percorreArvorePosOrdem(raiz);
	}

	/**
	 * Testa se a arvore esta logicamente vazia
	 */
	public boolean isEmpty() {
		return raiz == null;
	}

	/**
	 * Procura pelo menor elemento na arvore
	 */
	public int findMin() {
		if (isEmpty())
			System.out.println("UnderflowException");
		return findMin(raiz).valor;
	}

	/**
	 * Procura pelo menor elemento na subarvore t
	 */
	public AVLNode findMin(AVLNode t) {
		if (t == null)
			return t;
		while (t.esquerdo != null)
			t = t.esquerdo;
		return t;
	}

	/**
	 * Procura pelo maior elemento na arvore
	 */
	public int findMax() {
		if (isEmpty())
			System.out.println("UnderflowException");
		return findMax(raiz).valor;
	}

	/**
	 * Procura pelo maior elemento na subarvore t
	 */
	public AVLNode findMax(AVLNode t) {
		if (t == null)
			return t;

		while (t.direito != null)
			t = t.direito;
		return t;
	}

	/**
	 * Procura o elemento x em toda a arvore
	 */
	public boolean find(int x) {
		return find(x, raiz);
	}

	/**
	 * Procura o elemento x em toda a arvore t
	 * 
	 * @return <code>true</code> se encontrar <code>false</code>
	 */
	private boolean find(int x, AVLNode t) {
		while (t != null) {
			if (x < t.valor)
				t = t.esquerdo;
			else if (x > t.valor)
				t = t.direito;
			else
				return true; // encontrou
		}

		return false; // nao encontrou
	}

	public int qtdElementos() {
		return qtd(raiz);
	}

	private int qtd(AVLNode node) {
		int cont = 0;
		if (node.esquerdo != null) {
			cont += qtd(node.esquerdo);
		}
		if (node.direito != null) {
			cont += qtd(node.direito);
		}
		return cont + 1;
	}

	public boolean isFull() {
		int qtdNodes = (int) Math.pow(2, getAlturaArvore());
		if (qtdNodes == contaFolhas(raiz) / 2) {
			return true;
		}
		return false;
	}
	public int contaFolhas(AVLNode node) {
		int cont = 0;

		if (node == null) {
			cont += 1;
		} else {
			if(node != null){
				cont += contaFolhas(node.direito);
				cont += contaFolhas(node.esquerdo);
			}
				
		}
		return cont;
	}
	

}