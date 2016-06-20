package br.uece.avl.structures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class AVLNode {
	public int valor;
	public int altura;
	public AVLNode esquerdo;
	public AVLNode direito;

	private int positionX;
	private int positionY;
	private int id;
	private int radius;
	private boolean selected;

	public AVLNode() {
	}

	public AVLNode(int valor) {
		this(valor, null, null);
	}

	public AVLNode(int valor, AVLNode esquerdo, AVLNode direito) {
		this.valor = valor;
		this.esquerdo = esquerdo;
		this.direito = direito;
		this.altura = 0;
		this.radius = 15;
		this.positionX = 0;
		this.positionY = 0;
		this.id = 0;
		this.selected = false;
	}

	public String toString() {
		return " " + this.valor + " ";
	}

	public void draw(Graphics g) {
		if (selected) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillOval(positionX, positionY, 2 * radius, 2 * radius);

		if (selected) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawOval(positionX, positionY, 2 * radius, 2 * radius);

		g.setColor(Color.black);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		String valorRecebido = String.valueOf(id);
		g.drawString(valorRecebido, positionX
				+ (20 - g.getFontMetrics().stringWidth(valorRecebido)),
				positionY + 20);
	}

}
