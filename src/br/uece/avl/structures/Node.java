package br.uece.avl.structures;

public class Node {
	private Integer value;
	private Node next;
	
	public Node(Integer value, Node next) {
		super();
		this.value = value;
	}

	public Node(Node node){
		super();
		this.value = node.value;
		this.next = node.next;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return value+"; ";
	}
}
