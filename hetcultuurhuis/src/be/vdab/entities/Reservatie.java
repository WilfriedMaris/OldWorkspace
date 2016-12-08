package be.vdab.entities;

public class Reservatie {
	private final int klantNr;
	private final int voorstellingsNr;
	private final int plaatsen;
	
	public Reservatie(int klantNr, int voorstellingsNr, int plaatsen){
		this.klantNr = klantNr;
		this.voorstellingsNr = voorstellingsNr;
		this.plaatsen = plaatsen;
	}

	public int getKlantNr() {
		return klantNr;
	}

	public int getVoorstellingsNr() {
		return voorstellingsNr;
	}

	public int getPlaatsen() {
		return plaatsen;
	}

}
	
	