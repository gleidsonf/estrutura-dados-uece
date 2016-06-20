package br.uece.avl.structures;

public class Util {

	public static AVLNode pai = null;

	public static AVLNode[] ancestrais = new AVLNode[10];
	public static int limiteAncestrais = -1;

	public static boolean[] filhoEsquerdo = new boolean[10];

	public static AVLNode noEncontrado = null;

	/**
	 * 
	 * @param nodeAVL é no da arvore a ser pesquisado
	 * @param valor é o valor da chave a ser encontrada
	 * @return true se a chave for encontrado e false se a chave nao for encontrado
	 */
	
	public static boolean procurarChaveComAncestrais(AVLNode nodeAVL,
			String valor) {

		if (limiteAncestrais >= ancestrais.length - 2) {
			ancestrais = duplicaVetorNodeAVL(ancestrais);
			filhoEsquerdo = duplicaVetorBooleano(filhoEsquerdo);
		}

		limiteAncestrais++;
		ancestrais[limiteAncestrais] = nodeAVL;

		String chave = String.valueOf(nodeAVL.valor);
		int comparacao = chave.compareTo(valor);

		if (comparacao == 0) {
			noEncontrado = nodeAVL;
			return true;
		}

		pai = nodeAVL;

		if (comparacao > 0) {
		
			if (nodeAVL.esquerdo != null) {
				filhoEsquerdo[limiteAncestrais] = true;
				return procurarChaveComAncestrais(nodeAVL.esquerdo, valor);
			} else {
				return false;
			}
		} else {
			if (nodeAVL.direito != null) {
				filhoEsquerdo[limiteAncestrais] = false;
				return procurarChaveComAncestrais(nodeAVL.direito, valor);
			} else {
				return false;
			}
		}

	}
/**
 * Este método duplica o tamanho do vetor de No AVL e retorna o novo vetor
 * 
 */
	private static AVLNode[] duplicaVetorNodeAVL(AVLNode[] vetor) {
		
		AVLNode[] retorno = new AVLNode[vetor.length * 2];

		for (int i = 0; i < vetor.length; i++) {
			retorno[i] = vetor[i];
		}

		return retorno;
	}

	private static boolean[] duplicaVetorBooleano(boolean[] vetor) {
		boolean[] retorno = new boolean[vetor.length * 2];

		for (int i = 0; i < vetor.length; i++) {
			retorno[i] = vetor[i];
		}

		return retorno;
	}
	
//Método para zerar as variáveis de busca
	public static void zerarVariaveisDeBusca() {
		for (int i = 0; i < ancestrais.length; i++) {
			ancestrais[i] = null;
		}

		limiteAncestrais = -1;

		noEncontrado = null;

		pai = null;

	}

}