package be.vdab.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Saus {
	private long id;
	private String naam;
	private List<String> ingredienten = new ArrayList<>();

	public Saus(long id, String naam, Iterable<String> ingredienten) {
		// elke array, List en Set implementeert de interface Iterable
		this.id = id;
		this.naam = naam;
		this.ingredienten.addAll((Collection<? extends String>) ingredienten);
	}


	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public List<String> getIngredienten() {
		return ingredienten;
	}

	public void addIngredient(String ingredient) {
		ingredienten.add(ingredient);
	}
}