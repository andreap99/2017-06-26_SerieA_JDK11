package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Simulator {
	
	//PARAMETRI DELLA SIMULAZIONE
	private int T = 1000;
	private int p = 10;
	
	//OUTPUT DA CALCOLARE
	
	//STATO DEL MONDO
	private Map<String, Team> squadre;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Match> coda;
	
	//INIZIALIZZAZIONE
	public void init(List<Match> partite, Set<String> teams) {
		this.coda = new PriorityQueue<>(partite);
		this.squadre = new HashMap<>();
		for(String s : teams) {
			this.squadre.put(s, new Team(s, T));
		}
	}
	
	public String run() {
		
		while(!this.coda.isEmpty()) {
			Match m = this.coda.poll();
			//System.out.println(m);
			processEvent(m);
		}
		List<Team> classifica = new ArrayList<>(squadre.values());
		Collections.sort(classifica);
		String output = "";
		for(Team t : classifica) {
			output += t.toString() + ":  " + t.getNumPunti() + "\n";
		}
		
		return output;
	}
	
	private void processEvent(Match m) {
		
		Team casa = squadre.get(m.getHomeTeam());
		Team ospite = squadre.get(m.getAwayTeam());
		
		if(casa.getNumTifosi()<ospite.getNumTifosi()) {
			System.out.println("*");
			Double rapp = (double) casa.getNumTifosi()/ospite.getNumTifosi();
			if(probabilitaGoal(rapp)) {
				m.setFthg(m.getFthg()-1);
			}
			
		}
		if(ospite.getNumTifosi()<casa.getNumTifosi()) {
			Double rapp = (double) ospite.getNumTifosi()/casa.getNumTifosi();
			if(probabilitaGoal(rapp)) {
				m.setFtag(m.getFtag()-1);
			}
		}
		
		if(m.getFthg()>m.getFtag()) {
			
			casa.setPunti(3);
			double perc = (double) ((m.getFthg()-m.getFtag())*this.p)/100;
			int n = (int) (perc*ospite.getNumTifosi());
			casa.setNumTifosi(n);
			ospite.setNumTifosi(-n);
			
		}else if(m.getFtag()>m.getFthg()){
			
			ospite.setPunti(3);
			double perc = (double) ((m.getFtag()-m.getFthg())*this.p)/100;
			int n = (int) perc*casa.getNumTifosi();
			ospite.setNumTifosi(n);
			casa.setNumTifosi(-n);
			
		}else {
			
			casa.setPunti(1);
			ospite.setPunti(1);
			
		}
		
	}

	private boolean probabilitaGoal(Double rapp) {
		
		Double x = Math.random();
		if(x>1-rapp) {
			return false;
		}
		else
			return true;
	}


}
