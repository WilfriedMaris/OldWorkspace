package be.vdab.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Voorstelling {
	private final int voorstellingsNr;
	private final String titel;
	private final String uitvoerders;
	private final Date datum;
	private final int genreNr;
	private final BigDecimal prijs; 
	private final int vrijePlaatsen;
	
	public Voorstelling(int voorstellingsNr, String titel, String uitvoerders, Date datum, int genreNr,
			BigDecimal prijs, int vrijePlaatsen) {
		this.voorstellingsNr = voorstellingsNr;
		this.titel = titel;
		this.uitvoerders = uitvoerders;
		this.datum = datum;
		this.genreNr = genreNr;
		this.prijs = prijs;
		this.vrijePlaatsen = vrijePlaatsen;
	}

	public int getVoorstellingsNr() {
		return voorstellingsNr;
	}

	public String getTitel() {
		return titel;
	}

	public String getUitvoerders() {
		return uitvoerders;
	}

	public Date getDatum() {
		return datum;
	}

	public int getGenreNr() {
		return genreNr;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public int getVrijePlaatsen() {
		return vrijePlaatsen;
	}

}
