package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.event.TraversalListener;
import it.polito.tdp.borders.db.BordersDAO;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;

import org.jgrapht.event.VertexTraversalEvent;

public class Model {

	private BordersDAO dao;
	private SimpleGraph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private Map<Country,Country> visita;
	//private ArrayList<Border> confini;
	
	public Model() {
		
		dao = new BordersDAO();
		idMap = new HashMap<>();
		//dao.loadMap(idMap);
		
	}
	
	public List<Country> loadAllCountries() {
			return dao.loadAllCountries();
		}

	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
	//	confini = new ArrayList<>(dao.getCountryPairs(anno, idMap));
		dao.loadMap(anno,idMap);
		Graphs.addAllVertices(grafo,idMap.values());
		dao.loadCorrispondenze(anno, idMap);
		
		for(Border b : dao.getCountryPairs(anno, idMap)) {
			DefaultEdge e = this.grafo.getEdge(b.getC1(), b.getC2());
			if(e==null) {
			//	this.grafo.addEdge(b.getC1(), b.getC2());
				Graphs.addEdgeWithVertices(this.grafo, b.getC1(), b.getC2());
			}
		}
	}
	
	public int connIspector() {
		ConnectivityInspector<Country,DefaultEdge> cI = new ConnectivityInspector(grafo);
		return cI.connectedSets().size();
	}
	
	public String getVertex(){
		String tot="";
		for(Country c : this.grafo.vertexSet()) {
			if(c.getnConfini()!=0)
			tot+= c.getStateNme()+" "+c.getnConfini()+"\n";
		}
		return tot;
	}
	public int getNVertex() {
		return grafo.vertexSet().size();
	}
	public int getNEdge() {
		return grafo.edgeSet().size();
	}
	public String gradoCountry(){
		Map<Country,Integer> mappa = new HashMap<>();
		String tot="";
		for(Country c : grafo.vertexSet()) {
			mappa.put(c, grafo.degreeOf(c));
			if(grafo.degreeOf(c)!=0)
			tot+=c.getcCode()+" "+c.getStateNme()+" "+grafo.degreeOf(c)+"\n";
		}
		return tot;
	}
	public List<Country> calcolaRaggiungibili(Country partenza) {
		List<Country> percorso = new ArrayList<>();
		BreadthFirstIterator<Country,DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,partenza);
		
		this.visita = new HashMap<>();
		this.visita.put(partenza, null);
		
		it.addTraversalListener(new TraversalListener<Country,DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				// TODO Auto-generated method stub
				Country c1 = grafo.getEdgeSource(e.getEdge());
				Country c2 = grafo.getEdgeTarget(e.getEdge());
				
				if(visita.containsKey(c2) && !visita.containsKey(c1)) {
					visita.put(c1, c2);
				}else if(!visita.containsKey(c2) && visita.containsKey(c2)) {
					visita.put(c2, c1);
				}
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//String result ="";
		while(it.hasNext()) {
			Country cc = it.next();
		//	result+= cc.getStateNme()+"\n";
			percorso.add(cc);
		}
		return percorso;
	}

	public List<Country> calcolaRaggiungibiliDeep(Country value) {
		// TODO Auto-generated method stub
		DepthFirstIterator<Country,DefaultEdge> it = new DepthFirstIterator<>(this.grafo,value);
		visita  = new HashMap<>();
		List<Country> percorso = new LinkedList<>();
	//	visita.put(value, null);
		percorso.add(value);
		
		while(it.hasNext()) {
			Country c = it.next();
			percorso.add(c);
		}
		return percorso;
	}
	
}
