package be.vdab.entities;

import java.util.Date;

public class GastenBoekEntry {
	private final long id;
	private final String naam;
	private final Date datumTijd;
	private final String tekst;

	public GastenBoekEntry(String naam, String bericht) {
		if (!isNaamValid(naam)) {
			throw new IllegalArgumentException();
		}
		if (!isBerichtValid(bericht)) {
			throw new IllegalArgumentException();
		}
		this.id = 0;
		this.naam = naam;
		this.datumTijd = new Date();
		this.tekst = bericht;
	}

	public GastenBoekEntry(long id, String naam, Date datumTijd, String bericht) {
		this.id = id;
		this.naam = naam;
		this.datumTijd = datumTijd;
		this.tekst = bericht;
	}

	public static boolean isNaamValid(String naam) {
		return naam != null && !naam.isEmpty();
	}

	public static boolean isBerichtValid(String bericht) {
		return bericht != null && !bericht.isEmpty();
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Date getDatumTijd() {
		return datumTijd;
	}

	public String getTekst() {
		return tekst;
	}

}