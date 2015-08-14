package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics;

import java.io.Serializable;

import edu.uci.ics.jung.algorithms.importance.AbstractRanker;
import edu.uci.ics.jung.algorithms.scoring.VertexScorer;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.IRanker;


public class JungRanker implements Serializable, IRanker
{
    private static final long serialVersionUID = 1L;
    protected AbstractRanker<JungNode, JungEdge> ranker;
    protected VertexScorer<JungNode, Double> vertexRanker;
    
    public JungRanker(AbstractRanker<JungNode, JungEdge> ranker) {
        this.ranker = ranker;
        this.vertexRanker = null;
    }
    
    public JungRanker(VertexScorer<JungNode, Double> vertexRanker) {
        this.ranker = null;
        this.vertexRanker = vertexRanker;
    }
    
    public double getRankScore(INode n) {
        if (this.ranker != null) {
            return this.ranker.getVertexRankScore((JungNode)n);
        }
        return (double)this.vertexRanker.getVertexScore((JungNode)n);
    }

}
