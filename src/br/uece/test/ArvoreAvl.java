package br.uece.test;

public class ArvoreAvl {

	protected AvlNode root;

	/*
	 * Metodo inserir eh uma abstracao para o metodo inserirAVL, para que o
	 * usuario nao necessite trabalhar diretamente com um No.
	 */
	public void inserir(int k) {
		AvlNode no = new AvlNode(k);
		inserirAvl(this.root, no);
	}

	/*
	 * No metodo inserirAVL eh feita as comparacoes para balancear a arvore, ou
	 * seja, distribuir os elementos da arvore de forma igual
	 */
	public void inserirAvl(AvlNode noComparar, AvlNode noInserir) {

		if (noComparar == null) {
			this.root = noInserir;

		} else {

			if (noInserir.getChave() < noComparar.getChave()) {

				if (noComparar.getEsquerda() == null) {
					noComparar.setEsquerda(noInserir);
					noInserir.setPai(noComparar);
					verificarBalanceamento(noComparar);

				} else {
					inserirAvl(noComparar.getEsquerda(), noInserir);
				}

			} else if (noInserir.getChave() > noComparar.getChave()) {

				if (noComparar.getDireita() == null) {
					noComparar.setDireita(noInserir);
					noInserir.setPai(noComparar);
					verificarBalanceamento(noComparar);

				} else {
					inserirAvl(noComparar.getDireita(), noInserir);
				}

			}
		}
	}

	/*
	 * O metodo verificarBalanceamento faz um comparacao afim de manter todas as
	 * folhas com aproximadamente mesma altura
	 */
	public void verificarBalanceamento(AvlNode no) {
		setBalanceamento(no);
		int balanceamento = no.getBalanceamento();

		if (balanceamento == -2) {

			if (altura(no.getEsquerda().getEsquerda()) >= altura(no
					.getEsquerda().getDireita())) {
				no = rotacaoDireita(no);

			} else {
				no = rotacaoDuplaEsquerda(no);
			}

		} else if (balanceamento == 2) {

			if (altura(no.getDireita().getDireita()) >= altura(no.getDireita()
					.getEsquerda())) {
				no = rotacaoEsquerda(no);

			} else {
				no = rotacaaoDuplaDireita(no);
			}
		}

		if (no.getPai() != null) {
			verificarBalanceamento(no.getPai());
		} else {
			this.root = no;
		}
	}

	/*
	 * Novamente um metodo que permite a abstracao, evitando que o usuario
	 * trabalha diretamente com o No
	 */
	public void remover(int k) {
		removerAvl(this.root, k);
	}

	/**
	 * o Metodo removeAVL percorre a arvore recursivamente ate achar o no,
	 * quando isso acontece eh passado para o metodo removerNo
	 */
	public void removerAvl(AvlNode no, int k) {
		if (no == null) {
			return;

		} else {

			if (no.getChave() > k) {
				removerAvl(no.getEsquerda(), k);

			} else if (no.getChave() < k) {
				removerAvl(no.getDireita(), k);

			} else if (no.getChave() == k) {
				removerNo(no);
			}
		}
	}

	/*
	 * No metodo removerNo eh recebido um No e feito as ligacoes necessarias
	 * para isolar tal No e realocar seus no's folha
	 */
	public void removerNo(AvlNode no) {
		AvlNode aux;
		/*
		 * Se o no passado nao tiver nenhum folha ele ja eh desligado da arvore
		 */
		if (no.getEsquerda() == null || no.getDireita() == null) {

			if (no.getPai() == null) {
				this.root = null;
				no = null;
				return;
			}
			aux = no;
			/*
			 * Se tiver algum folha, o No aux pega o sucessor desse No
			 */
		} else {
			aux = sucessor(no);
			no.setChave(aux.getChave());
		}
		/*
		 * O No aux2 anda pelos folhas e faz o processo de desligamento do No da
		 * arvore, ligando o pai do No passado por parametro ao aux2. Por fim eh
		 * feito um balanceamento da arvore apos a remocao
		 */
		AvlNode aux2;
		if (aux.getEsquerda() != null) {
			aux2 = aux.getEsquerda();
		} else {
			aux2 = aux.getDireita();
		}

		if (aux2 != null) {
			aux2.setPai(aux.getPai());
		}

		if (aux.getPai() == null) {
			this.root = aux2;
		} else {
			if (aux == aux.getPai().getEsquerda()) {
				aux.getPai().setEsquerda(aux2);
			} else {
				aux.getPai().setDireita(aux2);
			}
			verificarBalanceamento(aux.getPai());
		}
		aux = null;
	}

	/*
	 * O metodo rotacaoEsquerda faz uma rotacao a esquerda de um No, para
	 * organizar o balanceamento dos folhas. Usado quando o fator de
	 * balanceamento for -2
	 */
	public AvlNode rotacaoEsquerda(AvlNode no) {

		AvlNode direita = no.getDireita();
		direita.setPai(no.getPai());

		no.setDireita(direita.getEsquerda());

		if (no.getDireita() != null) {
			no.getDireita().setPai(no);
		}

		direita.setEsquerda(no);
		no.setPai(direita);

		if (direita.getPai() != null) {

			if (direita.getPai().getDireita() == no) {
				direita.getPai().setDireita(direita);

			} else if (direita.getPai().getEsquerda() == no) {
				direita.getPai().setEsquerda(direita);
			}
		}

		setBalanceamento(no);
		setBalanceamento(direita);

		return direita;
	}

	/*
	 * O metodo rotacaoDireita faz uma rotacao a direita de um No, para
	 * organizar o balanceamento dos folhas. Usado quando o fator de
	 * balanceamento for 2
	 */
	public AvlNode rotacaoDireita(AvlNode no) {

		AvlNode esquerda = no.getEsquerda();
		esquerda.setPai(no.getPai());

		no.setEsquerda(esquerda.getDireita());

		if (no.getEsquerda() != null) {
			no.getEsquerda().setPai(no);
		}

		esquerda.setDireita(no);
		no.setPai(esquerda);

		if (esquerda.getPai() != null) {

			if (esquerda.getPai().getDireita() == no) {
				esquerda.getPai().setDireita(esquerda);

			} else if (esquerda.getPai().getEsquerda() == no) {
				esquerda.getPai().setEsquerda(esquerda);
			}
		}

		setBalanceamento(no);
		setBalanceamento(esquerda);

		return esquerda;
	}

	/*
	 * Efetua uma rotacao a esquerda e a direita do No
	 */
	public AvlNode rotacaoDuplaEsquerda(AvlNode no) {
		no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
		return rotacaoDireita(no);
	}

	/*
	 * Efetua uma rotacao a direita e a esquerda do No
	 */
	public AvlNode rotacaaoDuplaDireita(AvlNode no) {
		no.setDireita(rotacaoDireita(no.getDireita()));
		return rotacaoEsquerda(no);
	}

	public AvlNode sucessor(AvlNode no) {
		if (no.getDireita() != null) {
			AvlNode aux = no.getDireita();
			while (aux.getEsquerda() != null) {
				aux = aux.getEsquerda();
			}
			return aux;
		} else {
			AvlNode aux2 = no.getPai();
			while (aux2 != null && no == aux2.getDireita()) {
				no = aux2;
				aux2 = no.getPai();
			}
			return aux2;
		}
	}

	/*
	 * Conta recursivamente a altura da arvore, percorrendo os folhas e
	 * retornando o valor maximo dentre a altura direita e esquerda
	 */
	public int altura(AvlNode no) {
		if (no == null) {
			return -1;
		}

		if (no.getEsquerda() == null && no.getDireita() == null) {
			return 0;

		} else if (no.getEsquerda() == null) {
			return 1 + altura(no.getDireita());

		} else if (no.getDireita() == null) {
			return 1 + altura(no.getEsquerda());

		} else {
			return 1 + Math.max(altura(no.getEsquerda()),
					altura(no.getDireita()));
		}
	}

	/*
	 * O metodo setBalanceamento define o valor do balanceamento de um No com
	 * base na diferenca de altura dos no's
	 */
	private void setBalanceamento(AvlNode no) {
		no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
	}

	/*
	 * O metodo get eh uma abstracao ao metodo getAvl, permitindo ao usuario que
	 * nao trabalhe diretamente com o No
	 */
	public AvlNode get(int k) {
		return getAvl(k, root);
	}

	/*
	 * O metodo getAvl percorre recursivamente a arvore a procura de uma chave,
	 * quando encontrada retorna o no referente a essa chave
	 */
	private AvlNode getAvl(int k, AvlNode no) {
		if (k == no.getChave()) {
			return no;
		} else if (k < no.getChave()) {
			no = getAvl(k, no.getEsquerda());
		} else if (k > no.getChave()) {
			no = getAvl(k, no.getDireita());
		}
		return no;
	}

	public boolean contains(int value) {

		return contain(value, root);

	}

	private boolean contain(int value, AvlNode root) {
		boolean find = false;
		if (root != null) {
			if (root.getChave() == value) {
				return true;
			}
			find = contain(value, root.getEsquerda());
			find = contain(value, root.getDireita());
		}
		return find || false;
	}

	/*
	 * public boolean contains(int k) { boolean find = false; if (k ==
	 * root.getChave()) { find = true; } else if (k < root.getChave()) { find =
	 * contains(root.getEsquerda().getChave()); } else if(k> root.getChave()) {
	 * find = contains(root.getDireita().getChave()); } return find; }
	 */

	/*
	 * O metodo qtdElementos eh uma abstracao ao metodo qtdElementos(No no) e
	 * retorna a quantidade de elementos da arvore
	 */
	public int qtdElementos() {
		return qtdElementos(root);
	}

	/*
	 * Conta recursivamente a quantidade de no's na arvore
	 */
	private int qtdElementos(AvlNode no) {
		int qtd = 0;

		if (no.getEsquerda() != null) {
			qtd += qtdElementos(no.getEsquerda());
		}
		if (no.getDireita() != null) {
			qtd += qtdElementos(no.getDireita());
		}
		return qtd + 1;
	}

	public void imprime() {
		imprimeAvl(root);
	}

	private void imprimeAvl(AvlNode root) {
		if (root != null) {
			System.out.println(root.getChave());
			imprimeAvl(root.getEsquerda());
			imprimeAvl(root.getDireita());
		}
	}
}