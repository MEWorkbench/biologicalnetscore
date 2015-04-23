package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class NetworkMerger {

	public static void complexMergeNetworks(INetwork net1, INetwork net2, INetwork netf, INode[] exclusiveNodes1, INode[] exclusiveNodes2, 
		INode[] decisionPoints, INode[] diferentDirection, String reactionType, String fluxName)
	{
		ArrayList<INode> reactions = new ArrayList<INode>();
		ArrayList<INode> otherNodes = new ArrayList<INode>();
		
		for(int i=0;i<exclusiveNodes1.length;i++)
		{
			INode node = exclusiveNodes1[i].clone();
			node.addData("Color", "green");
			if(exclusiveNodes1[i].getType().equals(reactionType))
			{
				node.removeData(fluxName);
				node.addData("flux 1", exclusiveNodes1[i].getData(fluxName));
				node.addData("flux 2", "");
				reactions.add(node);
			}
			else otherNodes.add(node);
		}
		
		for(int i=0;i<exclusiveNodes2.length;i++)
		{
			INode node = exclusiveNodes2[i].clone();
			node.addData("Color", "blue");
			if(exclusiveNodes2[i].getType().equals(reactionType))
			{
				node.removeData(fluxName);
				node.addData("flux 1", "");
				node.addData("flux 2", exclusiveNodes2[i].getData(fluxName));
				reactions.add(node);
			}
			else otherNodes.add(node);
		}
		
		for(int i=0;i<decisionPoints.length;i++)
		{
			int idex = otherNodes.indexOf(decisionPoints[i]);
			
			INode node;
			
			if(idex==-1)
			{
				node = decisionPoints[i].clone();
				node.addData("Color", "yellow");
				otherNodes.add(node);
			}
			else
			{
				node = otherNodes.get(idex);
				node.addData("Color", "yellow");
			}
		}
		
		for(int i=0;i<diferentDirection.length;i++)
		{
			int idex = reactions.indexOf(diferentDirection[i]);
			
			INode node;
			
			if(idex==-1)
			{
				node = diferentDirection[i].clone();
				String flux1 = net1.getNode(node.getDb_id(), node.getType()).getData(fluxName);
				String flux2 = net2.getNode(node.getDb_id(), node.getType()).getData(fluxName);
				node.removeData(fluxName);
				node.addData("flux 1", flux1);
				node.addData("flux 2", flux2);
				node.addData("Color", "purple");
				reactions.add(node);
			}
			else
			{
				node = reactions.get(idex);
				node.addData("Color", "purple");
			}
		}
		
		INode[] nodes = new INode[reactions.size()+otherNodes.size()];
		int k = 0;
		
		for(int i=0;i<reactions.size();i++)
		{
			netf.addNode(reactions.get(i));
			nodes[k] = reactions.get(i);
			k++;
		}
		
		for(int i=0;i<otherNodes.size();i++)
		{
			netf.addNode(otherNodes.get(i));
			nodes[k] = otherNodes.get(i);
			k++;
		}
		
		for(int i=0;i<nodes.length;i++)
		{
			INode rec = nodes[i];
			
			if(rec.getData("Color").equals("green"))
			{
				INode naux = net1.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges = net1.getOutEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net1.getConectedNodes(edges[a])[1];
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						
						netf.addNode(met);
					}
					else met = met2;
					

					IEdge nedge = edges[a].clone();
					
					nedge.addData("Color", "green");
					
					netf.addEdge(nedge, rec, met);
				}
				
				edges = net1.getInEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net1.getConectedNodes(edges[a])[0];
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						
						netf.addNode(met);
					}
					else met = met2;

					IEdge nedge = edges[a].clone();
					
					nedge.addData("Color", "green");
					
					netf.addEdge(nedge, met, rec);
				}
				
			}
			else if(rec.getData("Color").equals("blue"))
			{
				INode naux = net2.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges = net2.getOutEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net2.getConectedNodes(edges[a])[1];
				
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						
						netf.addNode(met);
					}
					else met = met2;

					IEdge nedge = edges[a].clone();
					
					nedge.addData("Color", "blue");
					
					netf.addEdge(nedge, rec, met);
				}
				
				edges = net2.getInEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net2.getConectedNodes(edges[a])[0];
				
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						
						netf.addNode(met);
					}
					else met = met2;

					IEdge nedge = edges[a].clone();
					
					nedge.addData("Color", "blue");
					
					netf.addEdge(nedge, met, rec);
				}
				
			}
			else
			{
				INode naux1 = net1.getNode(rec.getDb_id(), rec.getType());
				INode naux2 = net2.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges1 = net1.getOutEdges(naux1);
				IEdge[] edges2 = net2.getOutEdges(naux2);
				INode[] enods1 = new INode[edges1.length];
				INode[] enods2 = new INode[edges2.length];
				boolean[] commun = new boolean[edges2.length];
				
				for(int a=0;a<edges1.length;a++) enods1[a] = net1.getConectedNodes(edges1[a])[1];
				for(int a=0;a<edges2.length;a++)
				{
					enods2[a] = net2.getConectedNodes(edges2[a])[1];
					commun[a] = false;
				}
				
				for(int a=0;a<edges1.length;a++)
				{
					INode met = enods1[a];
					
					boolean found = false;
					
					for(int b=0;b<commun.length && !found;b++)
					{
						if(enods2[b].equals(met))
						{
							found = true;
							commun[b] = true;
						}
					}
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						netf.addNode(met);
					}
					else met = met2;

					IEdge nedge = edges1[a].clone();
					
					if(!found) nedge.addData("Color", "green");
					else nedge.addData("Color", "black");
					netf.addEdge(nedge, met, rec);
					
				}
				
				for(int a=0;a<edges2.length;a++)
				{
					INode met = enods2[a];
					
					if(!commun[a])
					{
						INode met2 = netf.getNode(met.getDb_id(), met.getType());
						
						if(met2==null)
						{
							met = met.clone();
							met.addData("Color", "black");
							
							if(met.getType().equals(reactionType))
							{
								String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
								String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
								met.removeData(fluxName);
								met.addData("flux 1", flux1);
								met.addData("flux 2", flux2);
							}
							netf.addNode(met);
						}
						else met = met2;

						IEdge nedge = edges2[a].clone();
						
						nedge.addData("Color", "blue");
						
						netf.addEdge(nedge, rec, met);
					}
				}
				
				
				

				edges1 = net1.getInEdges(naux1);
				edges2 = net2.getInEdges(naux2);
				enods1 = new INode[edges1.length];
				enods2 = new INode[edges2.length];
				commun = new boolean[edges2.length];
				
				for(int a=0;a<edges1.length;a++) enods1[a] = net1.getConectedNodes(edges1[a])[0];
				for(int a=0;a<edges2.length;a++)
				{
					enods2[a] = net2.getConectedNodes(edges2[a])[0];
					commun[a] = false;
				}
				
				for(int a=0;a<edges1.length;a++)
				{
					INode met = enods1[a];
					
					boolean found = false;
					
					for(int b=0;b<commun.length && !found;b++)
					{
						if(enods2[b].equals(met))
						{
							found = true;
							commun[b] = true;
						}
					}
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2==null)
					{
						met = met.clone();
						met.addData("Color", "black");
						
						if(met.getType().equals(reactionType))
						{
							String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
							met.removeData(fluxName);
							met.addData("flux 1", flux1);
							met.addData("flux 2", flux2);
						}
						netf.addNode(met);
					}
					else met = met2;

					IEdge nedge = edges1[a].clone();
					
					if(!found) nedge.addData("Color", "green");
					else nedge.addData("Color", "black");
					netf.addEdge(nedge, met, rec);
					
				}
				
				for(int a=0;a<edges2.length;a++)
				{
					INode met = enods2[a];
					
					if(!commun[a])
					{
						INode met2 = netf.getNode(met.getDb_id(), met.getType());
						
						if(met2==null)
						{
							met = met.clone();
							met.addData("Color", "black");
							
							if(met.getType().equals(reactionType))
							{
								String flux1 = net1.getNode(met.getDb_id(), met.getType()).getData(fluxName);
								String flux2 = net2.getNode(met.getDb_id(), met.getType()).getData(fluxName);
								met.removeData(fluxName);
								met.addData("flux 1", flux1);
								met.addData("flux 2", flux2);
							}
							netf.addNode(met);
						}
						else met = met2;

						IEdge nedge = edges2[a].clone();
						
						nedge.addData("Color", "blue");
						
						netf.addEdge(nedge, met, rec);
					}
				}
			}
		}

		
	}
	
	public static void simpleMergeNetworks(INetwork net1, INetwork net2, INetwork netf, INode[] exclusiveNodes1, INode[] exclusiveNodes2, 
			INode[] decisionPoints, INode[] diferentDirection, String reactionType, String fluxName)
	{
		ArrayList<INode> reactions = new ArrayList<INode>();
		ArrayList<INode> otherNodes = new ArrayList<INode>();
		
		for(int i=0;i<exclusiveNodes1.length;i++)
		{
			INode node = exclusiveNodes1[i].clone();
			node.addData("Color", "green");
			if(exclusiveNodes1[i].getType().equals(reactionType))
			{
				node.removeData(fluxName);
				node.addData("flux 1", exclusiveNodes1[i].getData(fluxName));
				node.addData("flux 2", "");
				reactions.add(node);
			}
			else otherNodes.add(node);
		}
			
		for(int i=0;i<exclusiveNodes2.length;i++)
		{
			INode node = exclusiveNodes2[i].clone();
			node.addData("Color", "blue");
			if(exclusiveNodes2[i].getType().equals(reactionType))
			{
				node.removeData(fluxName);
				node.addData("flux 1", "");
				node.addData("flux 2", exclusiveNodes2[i].getData(fluxName));
				reactions.add(node);
			}
			else otherNodes.add(node);
		}
		
		for(int i=0;i<decisionPoints.length;i++)
		{
			int idex = otherNodes.indexOf(decisionPoints[i]);
			
			INode node;
			
			if(idex==-1)
			{
				node = decisionPoints[i].clone();
				node.addData("Color", "yellow");
				otherNodes.add(node);
			}
			else
			{
				node = otherNodes.get(idex);
				node.addData("Color", "yellow");
			}
		}
		
		for(int i=0;i<diferentDirection.length;i++)
		{
			int idex = reactions.indexOf(diferentDirection[i]);
			
			INode node;
			
			if(idex==-1)
			{
				node = diferentDirection[i].clone();
				String flux1 = net1.getNode(node.getDb_id(), node.getType()).getData(fluxName);
				String flux2 = net2.getNode(node.getDb_id(), node.getType()).getData(fluxName);
				node.removeData(fluxName);
				node.addData("flux 1", flux1);
				node.addData("flux 2", flux2);
				node.addData("Color", "purple");
				reactions.add(node);
			}
			else
			{
				node = reactions.get(idex);
				node.addData("Color", "purple");
			}
		}
		
		INode[] nodes = new INode[reactions.size()+otherNodes.size()];
		int k = 0;
		
		for(int i=0;i<reactions.size();i++)
		{
			netf.addNode(reactions.get(i));
			nodes[k] = reactions.get(i);
			k++;
		}
		
		for(int i=0;i<otherNodes.size();i++)
		{
			netf.addNode(otherNodes.get(i));
			nodes[k] = otherNodes.get(i);
			k++;
		}
		
		for(int i=0;i<nodes.length;i++)
		{
			INode rec = nodes[i];
			
			if(rec.getData("Color").equals("green"))
			{
				INode naux = net1.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges = net1.getOutEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net1.getConectedNodes(edges[a])[1];
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						met = met2;
					
						IEdge nedge = edges[a].clone();
					
						nedge.addData("Color", "green");
						
						netf.addEdge(nedge, rec, met);
					}
				}
				
				edges = net1.getInEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net1.getConectedNodes(edges[a])[0];
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						met = met2;
						
						IEdge nedge = edges[a].clone();
					
						nedge.addData("Color", "green");
					
						netf.addEdge(nedge, met, rec);
					}
				}
				
			}
			else if(rec.getData("Color").equals("blue"))
			{
				INode naux = net2.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges = net2.getOutEdges(naux);
				
				for(int a=0;a<edges.length;a++)
				{
					INode met = net2.getConectedNodes(edges[a])[1];
				
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						met = met2;
					
						IEdge nedge = edges[a].clone();
					
						nedge.addData("Color", "blue");
						
						netf.addEdge(nedge, rec, met);
					}
				}
					
				edges = net2.getInEdges(naux);
					
				for(int a=0;a<edges.length;a++)
				{
					INode met = net2.getConectedNodes(edges[a])[0];
				
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						met = met2;
						
						IEdge nedge = edges[a].clone();
						
						nedge.addData("Color", "blue");
					
						netf.addEdge(nedge, met, rec);
					}
				}
					
			}
			else
			{
				INode naux1 = net1.getNode(rec.getDb_id(), rec.getType());
				INode naux2 = net2.getNode(rec.getDb_id(), rec.getType());
				IEdge[] edges1 = net1.getOutEdges(naux1);
				IEdge[] edges2 = net2.getOutEdges(naux2);
				INode[] enods1 = new INode[edges1.length];
				INode[] enods2 = new INode[edges2.length];
				boolean[] commun = new boolean[edges2.length];
					
				for(int a=0;a<edges1.length;a++) enods1[a] = net1.getConectedNodes(edges1[a])[1];
				for(int a=0;a<edges2.length;a++)
				{
					enods2[a] = net2.getConectedNodes(edges2[a])[1];
					commun[a] = false;
				}
				
				for(int a=0;a<edges1.length;a++)
				{
					INode met = enods1[a];
					
					boolean found = false;
					
					for(int b=0;b<commun.length && !found;b++)
					{
						if(enods2[b].equals(met))
						{
							found = true;
							commun[b] = true;
						}
					}
					
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						 met = met2;
						 IEdge nedge = edges1[a].clone();
					
						 if(!found) nedge.addData("Color", "green");
						 else nedge.addData("Color", "black");
						 netf.addEdge(nedge, met, rec);
					}
						
				}
					
				for(int a=0;a<edges2.length;a++)
				{
					INode met = enods2[a];
					
					if(!commun[a])
					{
						INode met2 = netf.getNode(met.getDb_id(), met.getType());
						
						if(met2!=null)
						{
							met = met2;

							IEdge nedge = edges2[a].clone();
						
							nedge.addData("Color", "blue");
							
							netf.addEdge(nedge, rec, met);
						}
					}
				}
					
				edges1 = net1.getInEdges(naux1);
				edges2 = net2.getInEdges(naux2);
				enods1 = new INode[edges1.length];
				enods2 = new INode[edges2.length];
				commun = new boolean[edges2.length];
					
				for(int a=0;a<edges1.length;a++) enods1[a] = net1.getConectedNodes(edges1[a])[0];
				for(int a=0;a<edges2.length;a++)
				{
					enods2[a] = net2.getConectedNodes(edges2[a])[0];
					commun[a] = false;
				}
					
				for(int a=0;a<edges1.length;a++)
				{
					INode met = enods1[a];
						
					boolean found = false;
						
					for(int b=0;b<commun.length && !found;b++)
					{
						if(enods2[b].equals(met))
						{
							found = true;
							commun[b] = true;
						}
					}
					INode met2 = netf.getNode(met.getDb_id(), met.getType());
					
					if(met2!=null)
					{
						 met = met2;

						 IEdge nedge = edges1[a].clone();
					
						if(!found) nedge.addData("Color", "green");
						else nedge.addData("Color", "black");
						netf.addEdge(nedge, met, rec);
					}
					
				}
					
				for(int a=0;a<edges2.length;a++)
				{
					INode met = enods2[a];
					
					if(!commun[a])
					{
						INode met2 = netf.getNode(met.getDb_id(), met.getType());
						
						if(met2!=null)
						{
							met = met2;

							IEdge nedge = edges2[a].clone();
						
							nedge.addData("Color", "blue");
						
							netf.addEdge(nedge, met, rec);
						}
					}
				}
			}
		}			
	}
	
}
