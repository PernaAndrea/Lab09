package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private SimpleGraph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private Map<Country,Country> visita;
	private ArrayList<Border> confini;
	
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
				this.grafo.addEdge(b.getC1(), b.getC2());
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
			tot+=c.getStateNme()+" "+ grafo.degreeOf(c)+"\n";
		}
		return tot;
	}
}
