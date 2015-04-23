package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.genericmotif;

import java.util.ArrayList;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetGenericMotifs implements IGetMotifs {

	protected GenericMotifStructure gms;
	protected String name;
	protected boolean repetitions;
	
	public GetGenericMotifs(GenericMotifStructure gms, String name) {
		super();
		this.gms = gms;
		this.name = name;
		this.repetitions = false;
	}

	@Override
	public GenericMotifs getMotifs(INetwork net) {
//		int nnodes = gms.getVerticesTypes().length;
		
		GenericMotifs res = new GenericMotifs(this.name);
		
		String type = gms.getVerticesTypes()[0];
		
		INode[] firstNodes = net.getNodes(type);
		
		int num = 1;
		
		//System.out.println("Starting");
		
		for(int i=0;i<firstNodes.length;i++)
		{
			
//			//System.out.println("Here 1");
			boolean[] tested = new boolean[gms.getVerticesTypes().length];
			INode[] newcandiate = new INode[gms.getVerticesTypes().length];
			
//			//System.out.println("Here 2");
			
			for(int a=0;a<gms.getVerticesTypes().length;a++)
			{
				tested[a] = false;
				newcandiate[a] = null;
			}
			
//			//System.out.println("Here 3");
			
			newcandiate[0] = firstNodes[i];
			
			//System.out.println("Node "+i+":"+firstNodes[i].getDb_id());
			
//			//System.out.println("Here 4");
			
			INode[][] sol = next(net, newcandiate, tested);
			
			//System.out.println();
//			//System.out.println("Here 5");
			
			for(int a=0;a<sol.length;a++)
			{
				GenericMotif motif = new GenericMotif(""+num,this.gms.getVerticesNames(),null,sol[a]);
				res.addData(motif);
				num++;
			}

			
//			//System.out.println("Here 6");
		}
		
		res.obtainOccurenceData(this.gms.getVerticesTypes().length);
		
		return res;
	}
	
	//get all the possible next motifs
	// true - already uses
	// false + null - not yet
	// false + !null - to be used now
	@SuppressWarnings("unchecked")
	protected INode[][] next(INetwork net, INode[] newcandiate, boolean[] otested)
	{
		// 0 - check for repetitions
		
		if(!checkRepetitions(newcandiate))
		{
			INode[][] res = new INode[0][];
			return res; //stop invalid motif 
		}
		
		// 1-if no next targets return motif
		//System.out.println();
		
//		for(int i=0;i<newcandiate.length;i++)
//		{
			//System.out.print(i+":"+newcandiate[i]+" ");
//		}
		//System.out.println();
		
		//System.out.println("Step 1");
		boolean end = true;
		
//		clone variables
		
		//System.out.println("Cloning");
		
		boolean[] tested = new boolean[otested.length];
		
		for(int i=0;i<newcandiate.length;i++)
		{
			tested[i] = otested[i];
			if(end) end = newcandiate[i]!=null && tested[i];
		}		
		
		if(end) return new INode[][]{newcandiate}; //stop it was found
		
		// 2-discover the next targets to be identify
		
		//System.out.println("Step 2");
	
		ArrayList<INode> steeps = new ArrayList<INode>();
		ArrayList<Integer> psteeps = new ArrayList<Integer>();

		// 2.1.-identify to next steeping points
		
		//System.out.println("Step 2.1");
		for(int i=0;i<newcandiate.length;i++)
		{
			if(newcandiate[i]!=null && !tested[i])
			{
				tested[i] = true;
				steeps.add(newcandiate[i]);
				psteeps.add(new Integer(i));
			}
		}
		
		//System.out.println("psteeps "+psteeps.size());
		
		// 2.2.-check interconection between steeping nodes
		
		//System.out.println("Step 2.2");
	
		if(!checkInterconnection(net, psteeps, newcandiate))
		{
			INode[][] res = new INode[0][];
			return res; //stop nothing found 
		}
		
		// 2.3.-identify possible targets' numbers
		
		//System.out.println("Step 2.3");
		
		HashMap<Integer,ArrayList<Object[]>> tedges = new HashMap<Integer,ArrayList<Object[]>>();
		
		ArrayList<Integer> ptargets = getNextTargetsNumbers(net, psteeps, tested, tedges);
	
		end = true;
		
		
		for(int i=0;i<newcandiate.length;i++)
		{
			end = newcandiate[i]!=null && tested[i];
		}		
		
		if(end) return new INode[][]{newcandiate}; //it ended
		
		
		if(ptargets.size()==0)
		{
			INode[][] res = new INode[0][];
			return res; //stop nothing found 
		}
		
		// 2.4.-identify possible targets' connected edges
		
		//System.out.println("Step 2.4");
		
		INode[][] lsteep = new INode[ptargets.size()][];
		int[][] ldir = new int[ptargets.size()][];
		String[][] leTypes = new String[ptargets.size()][];
		
		for(int i=0;i<ptargets.size();i++)
		{
			ArrayList<Object[]> t = tedges.get(ptargets.get(i));
			
			lsteep[i] = new INode[t.size()];
			ldir[i] = new int[t.size()];
			leTypes[i] = new String[t.size()];
			
			for(int a=0;a<t.size();a++)
			{
				Object[] f = t.get(a);
				
				lsteep[i][a] = newcandiate[((Integer)f[0]).intValue()];
				leTypes[i][a] = (String)f[1];
				ldir[i][a] = ((Integer)f[2]).intValue();
			}
		}
		
		// 2.5.-identify possible targets
		
		//System.out.println("Step 2.5");
		
		ArrayList<INode>[] candidates = new ArrayList[ptargets.size()];
		
		for(int i=0;i<ptargets.size();i++)
		{
			ArrayList<INode> can = nextTargetsForPossition(net, this.gms.getVerticesTypes()[ptargets.get(i).intValue()], lsteep[i], ldir[i], leTypes[i]);
			
			if(can.size()==0)
			{
				INode[][] res = new INode[0][];
				return res; //stop nothing found 
			}
			
			candidates[i] = can;
		}
		
		// 2.6.-produce all possible combinations
		
		//System.out.println("Step 2.6");
		
		int[][] mConter = startMultiConter(candidates);
		
		int[] multiConter = mConter[0];
		int[] max = mConter[1];
		
		INode[][] res = new INode[0][];
		
//		int yor = 0;
		
		do {
			//System.out.print(yor+" Conter: ");
//			for(int i=0;i<multiConter.length;i++)
//			{
				//System.out.print(multiConter[i]+" ");
//			}
			//System.out.println();
			
//			yor++;
			
			INode[] nextcandiate = generateNextCandiate(multiConter, newcandiate, ptargets, candidates);
			res = combineSolution(res, next(net, nextcandiate, tested));
		} while(advanceMultiConterFinished(multiConter, max));
		
		// 3.-Return all possible next targets

		//System.out.println("Step 3");
		return res;
	}
	
	protected boolean checkRepetitions(INode[] newcandiate)
	{
		for(int a=0;a<newcandiate.length;a++)
		{
			for(int b=(a+1);b<newcandiate.length;b++)
			{
				if(newcandiate[a]!=null && newcandiate[b]!=null)
				{
					if(newcandiate[a].equals(newcandiate[b])) return false;
				}
			}
		}
		
		return true;
	}
	
	protected INode[][] combineSolution(INode[][] res1, INode[][] res2)
	{
		INode[][] res = new INode[res1.length+res2.length][];
		
		int z = 0;
		
		for(int i=0;i<res1.length;i++)
		{
			res[z] = res1[i];
			z++;
		}
		
		for(int i=0;i<res2.length;i++)
		{
			res[z] = res2[i];
			z++;
		}
		
		return res;
	}
	
	protected INode[] generateNextCandiate(int[] multiConter, INode[] newcandiate, ArrayList<Integer> ptargets, ArrayList<INode>[] candidates)
	{
		INode[] nextcandiate = new INode[newcandiate.length];
		
		for(int i=0;i<newcandiate.length;i++) nextcandiate[i] = newcandiate[i];
		
		for(int i=0;i<ptargets.size();i++)
		{
			nextcandiate[ptargets.get(i).intValue()] = candidates[i].get(multiConter[i]);
		}
		
		return nextcandiate;
	}
	
	protected boolean advanceMultiConterFinished(int[] multiConter, int[] max)
	{
		//System.out.println("TICK");
		
		boolean res = true;
		
		boolean stop = false;
		
		int tick = 0;
		
		//System.out.print("Begin: ");
//		for(int i=0;i<multiConter.length;i++)
//		{
			//System.out.print(multiConter[i]+" ");
//		}
		//System.out.println();
		
		while(!stop)
		{
			if((multiConter[tick]+1)<max[tick])
			{
				multiConter[tick]++;
				stop = true;
			}
			else
			{
				tick++;
				if(tick<max.length)
				{
					for(int i=0;i<tick;i++)
					{
						multiConter[i] = 0;
					}
//					multiConter[tick]++;
				}
				else
				{
					stop = true;
					res = false;
				}
			}
		}
		
		//System.out.print("End: ");
		for(int i=0;i<multiConter.length;i++)
		{
			//System.out.print(multiConter[i]+" ");
		}
		//System.out.println();
		
		return res;
	}
	
	protected int[][] startMultiConter(ArrayList<INode>[] candidates)
	{
		int[][] res = new int[2][candidates.length];
		
		//System.out.print("MAX: ");
		
		for(int i=0;i<candidates.length;i++)
		{
			res[0][i] = 0;
			res[1][i] = candidates[i].size();
			
			//System.out.print(res[1][i]+" ");
		}
		
		//System.out.println();
		
		return res;
	}
	
	protected ArrayList<Integer> getNextTargetsNumbers(INetwork net, ArrayList<Integer> psteeps,
		boolean[] tested, HashMap<Integer,ArrayList<Object[]>> arr) //Integer - node String - edge type Integer - direction
	{
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		for(int i=0;i<psteeps.size();i++)
		{
			int[] hark = this.gms.getConnected_edges()[psteeps.get(i).intValue()];
			
			for(int a=0;a<hark.length;a++)
			{
				Integer n = new Integer(hark[a]);
				
				if(!tested[n.intValue()] && !res.contains(n))
				{
					res.add(n);
					arr.put(n, new ArrayList<Object[]>());
				}
				
				if(res.contains(n))
				{
					arr.get(n).add(new Object[]{psteeps.get(i), this.gms.getType_edges()[psteeps.get(i).intValue()][a], new Integer(0)});
				}
			}
			
			Object[][] hearken = this.gms.getPointer(psteeps.get(i).intValue());
			
			for(int a=0;a<hearken.length;a++)
			{
				Integer n = (Integer)hearken[a][0];

				if(!tested[n.intValue()] && !res.contains(n))
				{
					res.add(n);
					arr.put(n, new ArrayList<Object[]>());
				}
				
				if(res.contains(n))
				{
					arr.get(n).add(new Object[]{psteeps.get(i), (Integer)hearken[a][1], new Integer(1)});
				}
			}
		}
		
		return res;
	}
	
	protected boolean checkInterconnection(INetwork net, ArrayList<Integer> psteeps, INode[] newcandiate)
	{
		for(int i=0;i<psteeps.size();i++)
		{
			int[] cns = this.gms.getConnected_edges()[psteeps.get(i).intValue()];
			for(int a=0;a<cns.length;a++)
			{
				if(psteeps.contains(new Integer(cns[a])))
				{
					String type = this.gms.getType_edges()[psteeps.get(i).intValue()][a];
					if(!checkConnection(net, newcandiate[psteeps.get(i).intValue()], newcandiate[cns[a]], type)) return false;
				}
			}
		}
		
		return true;
	}
	
	protected boolean checkConnection(INetwork net, INode s, INode e, String edgeType)
	{
		IEdge[] aedges = net.getOutEdges(s);
		
		for(int a=0;a<aedges.length;a++)
		{
			if(aedges[a].isType().equals(edgeType))
			{
				INode sec = net.getConectedNodes(aedges[a])[1];
				
				if(sec.equals(e)) return true;
			}
		}
		
		return false;
	}
	
	//finds one target
	protected ArrayList<INode> nextTargetsForPossition(INetwork net, String nType, INode[] steep, int[] dir, String[] eTypes) //0 - steep-> target, 1 - target->steep
	{
		ArrayList<INode> candiates = new ArrayList<INode>();
		
		IEdge[] aedges;
		
		if(dir[0]==0) aedges = net.getOutEdges(steep[0]);
		else aedges = net.getInEdges(steep[0]);
		
		for(int a=0;a<aedges.length;a++)
		{
			if(aedges[a].isType().equals(eTypes[0]))
			{
				INode sec;
				
				if(dir[0]==0) sec = net.getConectedNodes(aedges[a])[1];
				else sec = net.getConectedNodes(aedges[a])[0];
				
				if(sec.getType().equals(nType)) candiates.add(sec);
			}
		}

		if(candiates.size()==0) return new ArrayList<INode>(); //stop 
		
		if(steep.length>1)
		{
			for(int a=1;candiates.size()>0 && a<steep.length;a++)
			{
				ArrayList<INode> marked = new ArrayList<INode>();
				
				for(int b=0;b<candiates.size();b++)
				{
					if(!validTarget(net, steep[a], candiates.get(b), dir[a], eTypes[a])) marked.add(candiates.get(b));
				}

				for(int b=0;b<marked.size();b++)
				{
					candiates.remove(marked.get(b));
				}
			}
		}
		
		return candiates;
	}
	
	protected boolean validTarget(INetwork net, INode steep, INode target, int dir, String eTypes)
	{
		IEdge[] aedges;
		
		if(dir==0) aedges = net.getOutEdges(steep);
		else aedges = net.getInEdges(steep);
		
		for(int a=0;a<aedges.length;a++)
		{
			if(aedges[a].isType().equals(eTypes))
			{
				INode sec;
				
				if(dir==0) sec = net.getConectedNodes(aedges[a])[1];
				else sec = net.getConectedNodes(aedges[a])[0];
				
				if(sec.equals(target)) return true;
			}
		}
		
		return false;
	}
	
}
