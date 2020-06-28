package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new SerieADAO();
	}

	public String creaGrafo() {
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.dao.listTeams());
		for(Adiacenza a : this.dao.getAdiacenze()) {
			Graphs.addEdge(grafo, a.getS1(), a.getS2(), a.getPeso());
		}
		return String.format("Grafo creato! %d vertici e %d archi\n", this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size());
	}

	public Set<String> getSquadre() {
		return this.grafo.vertexSet();
	}

	public String connessioni(String squadra) {
		
		List<PartiteGiocate> lista = new ArrayList<>();
		for(String s : Graphs.neighborListOf(grafo, squadra))
			lista.add(new PartiteGiocate(s,(int) this.grafo.getEdgeWeight(this.grafo.getEdge(s, squadra))));
		Collections.sort(lista);
		String output = "Lista connessioni di " + squadra +":\n";
		for(PartiteGiocate p : lista) {
			output += p.toString() + "\n";
		}
		return output;
	}

	public List<Season> getStagioni() {
		return this.dao.listSeasons();
	}

	public String simula(Season stagione) {
		Simulator sim = new Simulator();
		sim.init(this.dao.getMatches(stagione), this.dao.getTeams(stagione));
		String output = "Classifica stagione "+stagione.getDescription()+ ":\n" +sim.run();
		return output;
	}

}
