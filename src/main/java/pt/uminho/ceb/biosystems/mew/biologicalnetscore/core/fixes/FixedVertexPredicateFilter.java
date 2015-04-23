package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fixes;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.algorithms.filters.Filter;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class FixedVertexPredicateFilter implements Filter<JungNode,JungEdge>
{
    protected Predicate<JungNode> vertex_pred;
    protected Predicate<JungEdge> edge_pred;

    /**
     * Creates an instance based on the specified vertex <code>Predicate</code>.
     * @param vertex_pred   the predicate that specifies which vertices to add to the filtered graph
     */
    public FixedVertexPredicateFilter(Predicate<JungNode> vertex_pred)
    {
        this.vertex_pred = vertex_pred;
        this.edge_pred = null;
    }
    
    public FixedVertexPredicateFilter(Predicate<JungNode> vertex_pred, Predicate<JungEdge> edge_pred)
    {
        this.vertex_pred = vertex_pred;
        this.edge_pred = edge_pred;
    }
    
	@SuppressWarnings("unchecked")
	public Graph<JungNode,JungEdge> transform(Graph<JungNode,JungEdge> g)
    {
        Graph<JungNode,JungEdge> filtered;
        try
        {
            filtered = g.getClass().newInstance();
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException("Unable to create copy of existing graph: ", e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to create copy of existing graph: ", e);
        }

        HashMap<String,JungNode> auxh = new HashMap<String,JungNode>();
        
        for (JungNode v : g.getVertices())
            if (vertex_pred==null ||vertex_pred.evaluate(v))
            {
            	JungNode v2 = v.clone();
                filtered.addVertex(v2);
                auxh.put(v2.getType()+":"+v2.getDb_id(), v2);
            }
        
        for (JungEdge e : g.getEdges())
        {
           	JungNode[] incident = g.getIncidentVertices(e).toArray(new JungNode[]{});
           	boolean allIn = true;
           	
           	for(int u=0;allIn && u<incident.length;u++)
           	{
           		JungNode v2 = incident[u];
           		allIn = auxh.containsKey(v2.getType()+":"+v2.getDb_id());
           	}
           	
           	if (allIn && (edge_pred==null || edge_pred.evaluate(e)))
           	{
           		if(g.getDest(e)!=null)
           		{
           			Pair<JungNode> v = g.getEndpoints(e);
           			
           			JungNode v1 = v.getFirst();
           			v1 = auxh.get(v1.getType()+":"+v1.getDb_id());
           			
           			JungNode v2 = v.getSecond();
           			v2 = auxh.get(v2.getType()+":"+v2.getDb_id());
           			
           			filtered.addEdge(e.clone(), v1, v2, EdgeType.DIRECTED);
           		}
           		else
           		{
           			ArrayList<JungNode> incident2 = new ArrayList<JungNode>();
           			
           			for(int u=0;u<incident.length;u++)
                   	{
           				JungNode v = incident[u];
           				
           				incident2.add(auxh.get(v.getType()+":"+v.getDb_id()));
                   	}
           			
           			filtered.addEdge(e, incident2);
           		}
           	}
        }
        
        
        return filtered;
    }

}