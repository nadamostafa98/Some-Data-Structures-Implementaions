package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node<T extends Comparable<T>, V> implements INode<T, V> {

	private T key = null;
	private V value = null;
	public Node<T, V> parent = null;
	public Node<T, V> left = null;
	public Node<T, V> right = null;
	public int numLeft = 0;
	public int numRight = 0;
	public boolean color = false;

	Node() {
		color = false;
		numLeft = 0;
		numRight = 0;
		parent = null;
		left = null;
		right = null;
	}

	@Override
	public void setParent(INode<T, V> parent) {
		this.parent = (Node<T, V>) parent;

	}

	@Override
	public INode<T, V> getParent() {
		return parent;
	}

	@Override
	public void setLeftChild(INode<T, V> leftChild) {
		this.left = (Node<T, V>) leftChild;

	}

	@Override
	public INode<T, V> getLeftChild() {
		return left;
	}

	@Override
	public void setRightChild(INode<T, V> rightChild) {
		this.right = (Node<T, V>) rightChild;

	}

	@Override
	public INode<T, V> getRightChild() {
		return right;
	}

	@Override
	public T getKey() {
		return key;
	}

	@Override
	public void setKey(T key) {
		this.key = key;

	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;

	}

	@Override
	public boolean getColor() {
		return color;
	}

	@Override
	public void setColor(boolean color) {
		this.color = color;

	}

	@Override
	public boolean isNull() {
		if (key == null) {
			return true;
		} else {
			return false;
		}
	}
}
