package be.vdab.entities;

public class Klant {
	private final int klantNr;
	private final String voornaam;
	private final String familienaam;
	private final String straat;
	private final String huisNr;
	private final String postcode;
	private final String gemeente;
	private final String gebruikersNaam;
	private final String paswoord;
	
	public Klant(int klantNr, String voornaam, String familienaam, String straat, String huisNr, String postcode,
			String gemeente, String gebruikersNaam, String paswoord) {
		this.klantNr = klantNr;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
		this.gebruikersNaam = gebruikersNaam;
		this.paswoord = paswoord;
	}

	public int getKlantNr() {
		return klantNr;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public String getStraat() {
		return straat;
	}

	public String getHuisNr() {
		return huisNr;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public String getGebruikersNaam() {
		return gebruikersNaam;
	}

	public String getPaswoord() {
		return paswoord;
	}
}
