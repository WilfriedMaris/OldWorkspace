package be.vdab.entities;

public class Genre {
	private final int genreNr;
	private final String naam;
	
	public Genre(String naam, int genreNr){
		this.naam = naam;
		this.genreNr = genreNr;
	}

	public String getNaam() {
		return naam;
	}

	public int getGenreNr() {
		return genreNr;
	}

}
