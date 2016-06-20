package br.uece.test;

import br.uece.avl.structures.AVLArvore;

public class test {
	public static void main(String[] args) {
		AVLArvore arvore = new AVLArvore();
		
		arvore.add(12);
		arvore.add(16);
		arvore.add(17);
		arvore.add(27);
		arvore.add(37);
		arvore.add(42);
		arvore.add(92);

		System.out.println("----------------------");
		arvore.mostraArvore();
		System.out.println("Altura: " +arvore.getAlturaArvore());
		



	}
}
