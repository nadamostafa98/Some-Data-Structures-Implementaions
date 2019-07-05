package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	private RBTT<T, V> RBTT = new RBTT<>();
	private LinkedList<Entry<T, V>> allElements = new LinkedList<>();
	private Set<Entry<T, V>> all = new LinkedHashSet<>();
	private LinkedList<V> allValues = new LinkedList<>();
	private Set<T> keys = new LinkedHashSet<>();
	private LinkedList<T> keysList = new LinkedList<T>();

	public Entry<T, V> ceilingEntry(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (RBTT.contains(key)) {
			V value = RBTT.search(key);
			return returnMapValue(key, (value));
		}
		if (RBTT.findNumGreater((Node<T, V>) RBTT.getRoot(), key) != 0) {
			Set<T> allKeys = keySet();
			Iterator<T> i = allKeys.iterator();
			T keyGreater = null;
			while (i.hasNext()) {
				T k = i.next();
				if (k.compareTo(key) >= 0) {
					keyGreater = k;
				}
			}
			V value = RBTT.search(keyGreater);
			return returnMapValue(keyGreater, (value));

		}
		return null;
	}

	@Override
	public T ceilingKey(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (RBTT.contains(key)) {
			return key;
		}

		if (RBTT.findNumGreater((Node<T, V>) RBTT.getRoot(), key) != 0) {
			Set<T> allKeys = keySet();
			Iterator<T> i = allKeys.iterator();
			T keyGreater = null;
			while (i.hasNext()) {
				T k = i.next();
				if (k.compareTo(key) >= 0) {
					keyGreater = k;
				}
			}
			return keyGreater;

		}
		return null;
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Entry<T, V> e = returnMapValue(firstEntry().getKey(), firstEntry().getValue());
			allValues.remove(keysList.indexOf(e.getKey()));
			keysList.remove(e.getKey());
			RBTT.delete(e.getKey());
			return e;
		}
	}

	@Override
	public Entry<T, V> pollLastEntry() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Entry<T, V> e = returnMapValue(lastEntry().getKey(), lastEntry().getValue());
			allValues.remove(keysList.indexOf(lastEntry().getKey()));
			keysList.remove(lastEntry().getKey());
			RBTT.delete(lastEntry().getKey());
			return e;
		}
	}

	@Override
	public void clear() {
		RBTT.clear();
		keysList.clear();
		allValues.clear();
	}

	@Override
	public boolean containsKey(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}

		return RBTT.contains(key);
	}

	public boolean value(INode<T, V> iRedBlackNode, V val) {
		if (iRedBlackNode.isNull()) {
			return false;
		}
		if (iRedBlackNode.getValue().equals(val)) {
			return true;
		}

		if (!iRedBlackNode.getLeftChild().isNull() && value(iRedBlackNode.getLeftChild(), val)) {
			return true;
		}
		if (!iRedBlackNode.getLeftChild().isNull() && value(iRedBlackNode.getRightChild(), val)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean containsValue(V value) {
		if (value == null) {
			throw new RuntimeErrorException(null);
		}
		return value(RBTT.getRoot(), value);
	}

	@Override
	public Set<Entry<T, V>> entrySet() {
		for (int i = 0; i < keysList.size(); i++) {
			Entry<T, V> entry = returnMapValue(keysList.get(i), allValues.get(i));
			allElements.add(entry);
		}
		Comparator<? super Entry<T, V>> c = new Comparator<Entry<T, V>>() {

			@Override
			public int compare(Entry<T, V> o1, Entry<T, V> o2) {
				if (o1.getKey().compareTo(o2.getKey()) < 0) {
					return -1;
				}
				if (o1.getKey().compareTo(o2.getKey()) > 0) {
					return 1;
				}
				if (o1.getKey().compareTo(o2.getKey()) == 0) {
					return 0;
				}
				return 0;
			}
		};
		allElements.sort(c);
		all.addAll(allElements);
		return all;
	}

	@Override
	public Entry<T, V> firstEntry() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Node<T, V> n = RBTT.treeMinimum((Node<T, V>) RBTT.getRoot());
			return returnMapValue((T) n.getKey(), (V) n.getValue());
		}
	}

	@Override
	public T firstKey() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Node<T, V> n = RBTT.treeMinimum((Node<T, V>) RBTT.getRoot());
			return (T) n.getKey();
		}
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (RBTT.contains(key)) {
			V value = RBTT.search(key);
			return returnMapValue(key, (value));
		}
		if (RBTT.findNumSmaller((Node<T, V>) RBTT.getRoot(), key) != 0) {
			Set<T> allKeys = keySet();
			Iterator<T> i = allKeys.iterator();
			T keySmaller = null;
			while (i.hasNext()) {
				T k = i.next();
				if (k.compareTo(key) <= 0) {
					keySmaller = k;
				}
			}
			V value = RBTT.search(keySmaller);
			return returnMapValue(keySmaller, (value));

		}
		return null;
	}

	@Override
	public T floorKey(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (RBTT.contains(key)) {
			return key;
		}
		if (RBTT.findNumSmaller((Node<T, V>) RBTT.getRoot(), key) != 0) {
			Set<T> allKeys = keySet();
			Iterator<T> i = allKeys.iterator();
			T keySmaller = null;
			while (i.hasNext()) {
				T k = i.next();
				if (k.compareTo(key) <= 0) {
					keySmaller = k;
				}
			}
			return keySmaller;

		}
		return null;
	}

	public Map.Entry<T, V> returnMapValue(T k, V v) {

		return new AbstractMap.SimpleEntry<T, V>(k, v);
	}

	@Override
	public V get(T key) {
		boolean existing = RBTT.contains(key);
		if (existing) {
			Node<T, V> root = (Node<T, V>) RBTT.getRoot();
			while (!(root.isNull()) && (key.compareTo((T) root.getKey()) != 0)) {
				if (key.compareTo((T) root.getKey()) < 0) {
					root = (Node<T, V>) root.getLeftChild();
				} else if (key.compareTo((T) root.getKey()) > 0) {
					root = (Node<T, V>) root.getRightChild();
				}
			}
			return (V) root.getValue();
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		if (toKey == null) {
			throw new RuntimeErrorException(null);
		}
		allElements.clear();
		ArrayList<Entry<T, V>> array = new ArrayList<Entry<T, V>>();
		for (int i = 0; i < keysList.size(); i++) {
			if (keysList.get(i).compareTo(toKey) < 0) {
				Entry<T, V> entry = returnMapValue(keysList.get(i), allValues.get(i));
				allElements.add(entry);
			}
		}
		Comparator<? super Entry<T, V>> c = new Comparator<Entry<T, V>>() {

			@Override
			public int compare(Entry<T, V> o1, Entry<T, V> o2) {
				if (o1.getKey().compareTo(o2.getKey()) < 0) {
					return -1;
				}
				if (o1.getKey().compareTo(o2.getKey()) > 0) {
					return 1;
				}
				if (o1.getKey().compareTo(o2.getKey()) == 0) {
					return 0;
				}
				return 0;
			}
		};
		allElements.sort(c);
		array.addAll(allElements);
		return array;
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		if (toKey == null) {
			throw new RuntimeErrorException(null);
		}
		allElements.clear();
		ArrayList<Entry<T, V>> array = new ArrayList<Entry<T, V>>();
		for (int i = 0; i < keysList.size(); i++) {
			if (keysList.get(i).compareTo(toKey) <= 0) {
				Entry<T, V> entry = returnMapValue(keysList.get(i), allValues.get(i));
				allElements.add(entry);
			}
		}
		Comparator<? super Entry<T, V>> c = new Comparator<Entry<T, V>>() {

			@Override
			public int compare(Entry<T, V> o1, Entry<T, V> o2) {
				if (o1.getKey().compareTo(o2.getKey()) < 0) {
					return -1;
				}
				if (o1.getKey().compareTo(o2.getKey()) > 0) {
					return 1;
				}
				if (o1.getKey().compareTo(o2.getKey()) == 0) {
					return 0;
				}
				return 0;
			}
		};
		allElements.sort(c);
		array.addAll(allElements);
		return array;
	}

	@Override
	public Entry<T, V> lastEntry() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Node<T, V> n = RBTT.treeMaximum((Node<T, V>) RBTT.getRoot());
			return returnMapValue((T) n.getKey(), (V) n.getValue());
		}
	}

	@Override
	public T lastKey() {
		if (RBTT.getRoot().isNull()) {
			return null;
		} else {
			Node<T, V> n = RBTT.treeMaximum((Node<T, V>) RBTT.getRoot());
			return (T) n.getKey();
		}
	}

	@Override
	public void put(T key, V value) {
		RBTT.insert(key, value);
		if (!keysList.contains(key)) {
			keysList.add(key);
			allValues.add(value);
		} else {
			allValues.set(keysList.indexOf(key), value);
		}
	}

	@Override
	public void putAll(Map<T, V> map) {
		if (map == null) {
			throw new RuntimeErrorException(null);
		}
		Set<T> hash_Set = map.keySet();
		Iterator<T> iterator = hash_Set.iterator();
		while (iterator.hasNext()) {
			T key = iterator.next();
			V value = map.get(key);
			RBTT.insert(key, value);
			if (!keysList.contains(key)) {
				keysList.add(key);
				allValues.add(value);
			} else {
				allValues.set(keysList.indexOf(key), value);
			}
		}
	}

	@Override
	public boolean remove(T key) {
		if (RBTT.delete(key)) {
			allValues.remove(keysList.indexOf(key));
			keysList.remove(key);
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return RBTT.size;
	}

	public LinkedList<V> collection = new LinkedList<>();

	@Override
	public Collection<V> values() {
		Set<T> allKeys = keySet();
		Iterator<T> i = allKeys.iterator();
		while (i.hasNext()) {
			T key = i.next();
			V value = RBTT.search(key);
			collection.add(value);
		}
		return collection;
	}

	@Override
	public Set<T> keySet() {
		Collections.sort(keysList);
		keys.addAll(keysList);
		return keys;
	}

}
