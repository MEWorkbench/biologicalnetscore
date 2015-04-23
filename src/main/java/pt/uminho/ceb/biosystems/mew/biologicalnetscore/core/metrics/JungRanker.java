package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.IRanker;
import edu.uci.ics.jung.algorithms.importance.AbstractRanker;
import edu.uci.ics.jung.algorithms.scoring.VertexScorer;


public class JungRanker implements Serializable, IRanker
{
    private static final long serialVersionUID = 1L;
    protected AbstractRanker<INode, IEdge> ranker;
    protected VertexScorer<INode, Double> vertexRanker;
    
    public JungRanker(AbstractRanker<INode, IEdge> ranker) {
        this.ranker = ranker;
        this.vertexRanker = null;
    }
    
    public JungRanker(VertexScorer<INode, Double> vertexRanker) {
        this.ranker = null;
        this.vertexRanker = vertexRanker;
    }
    
    public double getRankScore(INode n) {
        if (this.ranker != null) {
            return this.ranker.getVertexRankScore(n);
        }
        return (double)this.vertexRanker.getVertexScore(n);
    }

}
