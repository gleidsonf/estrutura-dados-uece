package br.uece.test;

import br.uece.avl.structures.AVLArvore;

public class test {
	public static void main(String[] args) {
		AVLArvore arvore = new AVLArvore();
		
arvore.add(16);
arvore.add(11);
arvore.add(21);
arvore.add(91);
//arvore.add(1);
//arvore.add(17);
arvore.add(13);
arvore.add(18);
//arvore.add(4);
//arvore.add(6);
//arvore.add(8);
//arvore.add(10);
//arvore.add(12);
//arvore.add(14);
arvore.add(3);
System.out.println(arvore.isFull());
System.out.println(arvore.contaFolhas(arvore.raiz)/2);
System.out.println("Calculo: " + (int)Math.pow(2, arvore.getAlturaArvore()));


		System.out.println("----------------------");
		arvore.mostraArvore();
		System.out.println("Altura: " +arvore.getAlturaArvore());
		



	}
}
