/**
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * @author Frankie Fasola
 *
 */
public class KruskalMST {
	
	public class EdgeComparator implements Comparator<Edge<Integer>> {
		@Override
		public int compare (Edge<Integer> edge1, Edge<Integer> edge2) {
			return edge1.getWeight() <= edge2.getWeight() ? -1 : 1;
		}
	}
	
	public List<Edge<Integer>> getMST(Graph<Integer> graph) {
		List<Edge<Integer>> edges = graph.getAllEdges();
		EdgeComparator ec = new EdgeComparator();
		
		Collections.sort(edges,ec);
		DisjointSet ds = new DisjointSet();
		
		for (Vertex<Integer> v : graph.getAllVertex())
			ds.makeSet(v.getId());
		List<Edge<Integer>> result = new ArrayList<Edge<Integer>>();
		for (Edge<Integer> e : edges) {
			long root1 = ds.findSet(e.getVertex1().getId());
			long root2 = ds.findSet(e.getVertex2().getId());
			
			if (root1 == root2)
				continue;
			else {
				result.add(e);
				ds.union(e.getVertex1().getId(), e.getVertex2().getId());
			}
		}
		return result;
	}
}
