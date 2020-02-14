
public class HashTable <K, V> {
	private HashNode<K,V>[] nodes;
	
	public static void main(String[] args) {

        HashTable<String, Integer> tbl = new HashTable<String, Integer>(5);

        tbl.insert("one", 1);
        tbl.insert("three", 3);
        tbl.insert("nineteen", 19);
        tbl.insert("fifteen", 15);
        tbl.insert("six", 6);
        System.out.println(tbl);
        System.out.println("Afterrt");

        
        tbl.remove("three");
        System.out.println(tbl);
        tbl.resize(8);
        System.out.println(tbl);
        System.out.println("Afterze");
        tbl.insert("hundred", 100);
        System.out.println(tbl);
    }


	
	
	@SuppressWarnings("unchecked")
	public HashTable (int size) {
			nodes = new HashNode[size];
	}
	
	private int getIndex (K key) {
		int hash = key.hashCode() % nodes.length;
		if(hash  < 0)
			hash += nodes.length;
		return hash;
	}
	
	public V insert (K key, V data) {
		int hash = getIndex(key);
		
		for (HashNode<K, V> node = nodes[hash]; node != null; node = node.next)
			if ((hash == node.hash) && key.equals(key)) {
				V oldData = node.data;
				node.data = data;
				return oldData;
			}
		
		HashNode<K, V> node = new HashNode<K,V> (key, data, nodes[hash], hash);
		nodes[hash] = node;
		
		return null;
	}
	
	public boolean remove(K key) {
		int hash = getIndex(key);
		HashNode<K, V> previous = null;
		for (HashNode<K, V> node = nodes[hash]; node != null; node = node.next) {
			if ((hash == node.hash && key.equals(node.key))) {
				if (previous != null) 
					previous.next = node.next;
				else
					nodes[hash] = node.next;
				return true;
			}
			previous = node;
		}
		return false;
	}

	public V get(K key) {
		int hash = getIndex(key);
		for (HashNode<K, V> node = nodes[hash]; node != null; node = node.next) 
			if (key.equals(node.key))
				return node.data;
		return null;
	}
	
	public void resize (int size) {
		HashTable<K, V> newTable = new HashTable<K,V>(size);
		for (HashNode<K,V> node : nodes) {
			for (; node != null; node = node.next) {
				newTable.insert(node.key, node.data);
				remove(node.key);
			}
		}
		nodes = newTable.nodes;
	}
	
	public String toString() {
		String s = "";
		for (HashNode<K,V> node : nodes) 
			for (; node != null; node = node.next)
				s += node.data + " ";
		return s;
	}
	
	static class HashNode<K,V> {
		final K key;
		V data;
		HashNode<K,V> next;
		final int hash;
		
		public HashNode (K k, V v, HashNode<K,V> n, int h) {
			key = k;
			data = v;
			next = n;
			hash = h;
		}
	}
}

