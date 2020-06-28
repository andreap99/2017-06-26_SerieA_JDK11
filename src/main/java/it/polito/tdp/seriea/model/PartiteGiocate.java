package it.polito.tdp.seriea.model;

public class PartiteGiocate implements Comparable<PartiteGiocate>{
	
	private String squadra;
	private int numPartite;
	
	public PartiteGiocate(String squadra, int numPartite) {
		super();
		this.squadra = squadra;
		this.numPartite = numPartite;
	}
	public String getSquadra() {
		return squadra;
	}
	public int getNumPartite() {
		return numPartite;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((squadra == null) ? 0 : squadra.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartiteGiocate other = (PartiteGiocate) obj;
		if (squadra == null) {
			if (other.squadra != null)
				return false;
		} else if (!squadra.equals(other.squadra))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return squadra + "--> numPartite=" + numPartite;
	}
	@Override
	public int compareTo(PartiteGiocate other) {
		return -(this.numPartite-other.numPartite);
	}
	
	

}
