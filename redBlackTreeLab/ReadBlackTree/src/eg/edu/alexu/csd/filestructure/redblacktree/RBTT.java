package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RBTT<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T, V> nil = new Node<T, V>();
	private Node<T, V> root = new Node<T, V>();
	public int size = 0;

	@Override
	public INode<T, V> getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		if ((root).isNull()) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		size = 0;
		root = nil;
	}

	@Override
	public boolean contains(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}

		Node<T, V> searchNode = getNode(key);
		if (searchNode == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void insert(T key, V value) {
		if (key == null || value == null) {
			throw new RuntimeErrorException(null);
		}
		Node<T, V> node = getNode(key);
		if (node == null) {
			Node<T, V> nodeInsert = new Node<T, V>();
			nodeInsert.setKey(key);
			nodeInsert.setValue(value);
			size++;
			insertNode(nodeInsert);
		} else if (node.getValue().equals(value)) {
			return;
		} else {
			delete(key);
			Node<T, V> nodeInsert = new Node<T, V>();
			nodeInsert.setKey(key);
			nodeInsert.setValue(value);
			insertNode(nodeInsert);
		}
	}

	private void insertNode(Node<T, V> z) {
		Node<T, V> y = nil;
		Node<T, V> x = root;
		while (!(x).isNull()) {
			y = x;
			if (z.getKey().compareTo(x.getKey()) < 0) {
				x.numLeft++;
				x = (Node<T, V>) x.getLeftChild();
			} else {
				x.numRight++;
				x = (Node<T, V>) x.getRightChild();
			}
		}
		z.setParent(y);
		if ((y).isNull()) {
			root = z;
		} else if (z.getKey().compareTo(y.getKey()) < 0) {
			y.setLeftChild(z);
		} else {
			y.setRightChild(z);
		}
		z.setLeftChild(nil);
		z.setRightChild(nil);
		z.setColor(true);
		fixInsert(z);
	}

	private void fixInsert(Node<T, V> z) {
		Node<T, V> y = nil;
		while (z.getParent().getColor() == true) {
			if (z.getParent() == z.getParent().getParent().getLeftChild()) {
				y = (Node<T, V>) z.getParent().getParent().getRightChild();
				if (y.getColor() == true) {
					z.getParent().setColor(false);
					y.setColor(false);
					z.getParent().getParent().setColor(true);
					z = (Node<T, V>) z.getParent().getParent();
				} else if (z == z.getParent().getRightChild()) {
					z = (Node<T, V>) z.getParent();
					leftRotate(z);
				} else {
					z.getParent().setColor(false);
					z.getParent().getParent().setColor(true);
					rightRotate((Node<T, V>) z.getParent().getParent());
				}
			}

			else {
				y = (Node<T, V>) z.getParent().getParent().getLeftChild();
				if (y.getColor() == true) {
					z.getParent().setColor(false);
					y.setColor(false);
					z.getParent().getParent().setColor(true);
					z = (Node<T, V>) z.getParent().getParent();
				} else if (z == z.getParent().getLeftChild()) {
					z = (Node<T, V>) z.getParent();
					rightRotate(z);
				} else {
					z.getParent().setColor(false);
					z.getParent().getParent().setColor(true);
					leftRotate((Node<T, V>) z.getParent().getParent());
				}
			}
		}
		root.setColor(false);

	}

	@Override
	public boolean delete(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		Node<T, V> nodeDelete = getNode(key);
		if (nodeDelete == null) {
			return false;
		} else {
			deleteRBT(nodeDelete);
			size--;
			if (size == 0) {
				root = new Node<>();
				root.setColor(false);
				root.setKey(null);
				root.setLeftChild(nil);
				root.setRightChild(nil);
				root.setParent(nil);
				root.setValue(null);
			}
			return true;
		}

	}

	public void deleteRBT(Node<T, V> v) {

		Node<T, V> z = getNode(v.getKey());
		Node<T, V> x = nil;
		Node<T, V> y = nil;
		if ((z.getLeftChild()).isNull() || (z.getRightChild()).isNull()) {
			y = z;
		} else {
			y = treeSuccessor(z);
		}

		if (!(y.getLeftChild()).isNull()) {
			x = (Node<T, V>) y.getLeftChild();
		} else {
			x = (Node<T, V>) y.getRightChild();
		}
		x.setParent(y.getParent());
		if ((y.getParent()).isNull()) {
			root = x;
		} else if (!(y.getParent().getLeftChild()).isNull() && y.getParent().getLeftChild() == y) {
			y.getParent().setLeftChild(x);
		} else if (!(y.getParent().getRightChild()).isNull() && y.getParent().getRightChild() == y) {
			y.getParent().setRightChild(x);
		}
		if (y != z) {
			z.setKey(y.getKey());
		}
		dataFixingDelete(x, y);
		if (y.getColor() == false)
			deleteFixup(x);
	}

	private void dataFixingDelete(Node<T, V> x, Node<T, V> y) {

		Node<T, V> current = nil;
		Node<T, V> track = nil;
		if ((x).isNull()) {
			current = (Node<T, V>) y.getParent();
			track = y;
		} else {
			current = (Node<T, V>) x.getParent();
			track = x;
		}

		while (!(current).isNull()) {
			if (y.getKey() != current.getKey()) {
				if (y.getKey().compareTo(current.getKey()) > 0) {
					current.numRight--;
				}
				if (y.getKey().compareTo(current.getKey()) < 0) {
					current.numLeft--;
				}
			} else {
				if ((current.getLeftChild()).isNull()) {
					current.numLeft--;
				} else if ((current.getRightChild()).isNull()) {
					current.numRight--;
				} else if (track == current.getRightChild()) {
					current.numRight--;
				} else if (track == current.getLeftChild()) {
					current.numLeft--;
				}
			}

			track = current;
			current = (Node<T, V>) current.getParent();

		}

	}

	private void deleteFixup(Node<T, V> x) {

		Node<T, V> w;

		while (x != root && x.getColor() == false) {

			if (x == x.getParent().getLeftChild()) {

				w = (Node<T, V>) x.getParent().getRightChild();

				if (w.getColor() == true) {
					w.setColor(false);
					x.getParent().setColor(true);
					leftRotate((Node<T, V>) x.getParent());
					w = (Node<T, V>) x.getParent().getRightChild();
				}

				if (w.getLeftChild().getColor() == false && w.getRightChild().getColor() == false) {
					w.setColor(true);
					x = (Node<T, V>) x.getParent();
				} else {
					if (w.getRightChild().getColor() == false) {
						w.getLeftChild().setColor(false);
						w.setColor(true);
						rightRotate(w);
						w = (Node<T, V>) x.getParent().getRightChild();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(false);
					w.getRightChild().setColor(false);
					leftRotate((Node<T, V>) x.getParent());
					x = root;
				}
			} else {
				w = (Node<T, V>) x.getParent().getLeftChild();
				if (w.getColor() == true) {
					w.setColor(false);
					x.getParent().setColor(true);
					rightRotate((Node<T, V>) x.getParent());
					w = (Node<T, V>) x.getParent().getLeftChild();
				}
				if (w.getRightChild().getColor() == false && w.getLeftChild().getColor() == false) {
					w.setColor(true);
					x = (Node<T, V>) x.getParent();
				} else {
					if (w.getLeftChild().getColor() == false) {
						w.getRightChild().setColor(false);
						w.setColor(true);
						leftRotate(w);
						w = (Node<T, V>) x.getParent().getLeftChild();
					}

					w.setColor(x.getParent().getColor());
					x.getParent().setColor(false);
					w.getLeftChild().setColor(false);
					rightRotate((Node<T, V>) x.getParent());
					x = root;
				}
			}
		}
		x.setColor(false);
	}

	@Override
	public V search(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		Node<T, V> searchNode = new Node<>();
		searchNode = getNode(key);
		if (searchNode == null) {
			return null;
		} else {
			return searchNode.getValue();
		}
	}

	public Node<T, V> getNode(T key) {

		Node<T, V> current = root;
		while (!(current).isNull()) {
			if (current.getKey().compareTo(key) == 0) {
				return current;
			} else if (current.getKey().compareTo(key) < 0)
				current = (Node<T, V>) current.getRightChild();
			else
				current = (Node<T, V>) current.getLeftChild();
		}
		return null;

	}

	public Node<T, V> treeMinimum(Node<T, V> node) {

		if (!node.isNull()) {
			while (!(node.getLeftChild()).isNull())
				node = (Node<T, V>) node.getLeftChild();
		}
		return node;
	}

	public Node<T, V> treeMaximum(Node<T, V> x) {
		while (!(x.getRightChild().isNull())) {
			x = (Node<T, V>) x.getRightChild();
		}
		return x;
	}

	public Node<T, V> treeSuccessor(Node<T, V> x) {

		if (!(x.getLeftChild()).isNull()) {
			return treeMinimum((Node<T, V>) x.getRightChild());
		}
		Node<T, V> y = (Node<T, V>) x.getParent();

		while (!(y).isNull() && x == y.getRightChild()) {
			x = y;
			y = (Node<T, V>) y.getParent();
		}
		return y;
	}

	public int findNumGreater(Node<T, V> node, T key) {
		if ((node).isNull())
			return 0;
		else if (key.compareTo(node.getKey()) < 0)
			return 1 + node.numRight + findNumGreater((Node<T, V>) node.getLeftChild(), key);
		else
			return findNumGreater((Node<T, V>) node.getRightChild(), key);

	}

	public int findNumSmaller(Node<T, V> node, T key) {
		if ((node).isNull())
			return 0;
		else if (key.compareTo(node.getKey()) <= 0)
			return findNumSmaller((Node<T, V>) node.getLeftChild(), key);
		else
			return 1 + node.numLeft + findNumSmaller((Node<T, V>) node.getRightChild(), key);
	}

	private void leftRotate(Node<T, V> x) {
		if ((x.getLeftChild()).isNull() && (x.getRightChild().getLeftChild()).isNull()) {
			x.numLeft = 0;
			x.numRight = 0;
			x.right.numLeft = 1;
		} else if ((x.getLeftChild()).isNull() && !(x.getRightChild().getLeftChild()).isNull()) {
			x.numLeft = 0;
			x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
			x.right.numLeft = 2 + x.right.left.numLeft + x.right.left.numRight;
		} else if (!(x.getLeftChild()).isNull() && (x.getRightChild().getLeftChild()).isNull()) {
			x.numRight = 0;
			x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

		} else {
			x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
			x.right.numLeft = 3 + x.left.numLeft + x.left.numRight + x.right.left.numLeft + x.right.left.numRight;
		}

		Node<T, V> y;
		y = (Node<T, V>) x.getRightChild();
		x.setRightChild(y.getLeftChild());
		if (!(y.getLeftChild()).isNull()) {
			y.getLeftChild().setParent(x);
		}
		y.setParent(x.getParent());
		if ((x.getParent()).isNull()) {
			root = y;
		} else if (x.getParent().getLeftChild() == x) {
			x.getParent().setLeftChild(y);
		} else {
			x.getParent().setRightChild(y);
		}
		y.setLeftChild(x);
		x.setParent(y);
	}
	private void rightRotate(Node<T, V> y) {
		if ((y.getRightChild()).isNull() && (y.getLeftChild().getRightChild()).isNull()) {
			y.numRight = 0;
			y.numLeft = 0;
			y.left.numRight = 1;
		}

		else if ((y.getRightChild()).isNull() && !(y.getLeftChild().getRightChild()).isNull()) {
			y.numRight = 0;
			y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
			y.left.numRight = 2 + y.left.right.numRight + y.left.right.numLeft;
		}

		else if (!(y.getRightChild()).isNull() && (y.getLeftChild().getRightChild()).isNull()) {
			y.numLeft = 0;
			y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

		} else {
			y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
			y.left.numRight = 3 + y.right.numRight + y.right.numLeft + y.left.right.numRight + y.left.right.numLeft;
		}
		Node<T, V> x = (Node<T, V>) y.getLeftChild();
		y.setLeftChild(x.getRightChild());
		if (!(x.getRightChild()).isNull()) {
			x.getRightChild().setParent(y);
		}
		x.setParent(y.getParent());
		if ((y.getParent()).isNull()) {
			root = x;
		} else if (y.getParent().getRightChild() == y) {
			y.getParent().setRightChild(x);
		} else {
			y.getParent().setLeftChild(x);
		}
		x.setRightChild(y);
		y.setParent(x);

	}
}
