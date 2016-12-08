package be.vdab.entities;

public class SausRaden {
	private final String sausnaam;
	private String puntjes = "";
	private int teller = 0;
	private String tekst = "";
	
	public SausRaden(Saus saus){
		this.sausnaam = saus.getNaam();
		int lengte = sausnaam.length();
		for(int teller=0; teller<lengte; teller++){
			puntjes = puntjes + ".";
		}
	}
	
	public void replacePuntjes(String letter){
		StringBuilder puntjesbuilder = new StringBuilder(puntjes);
		int index = sausnaam.indexOf(letter);
		if (index == -1){
			teller++;
		}
			while (index > -1){
				puntjesbuilder.setCharAt(index, letter.charAt(0));;
				index = sausnaam.indexOf(letter, index+1);
			}
			puntjes = puntjesbuilder.toString();
	}			
	
	public String getSaus(){
		return sausnaam;
	}
	
	public String getPuntjes() {
		return puntjes;
			}

	public String getTeller() {
		return String.valueOf(teller);
	}
	
	public String getTekst(){
		String dot ="." ;
		if(teller == 10){
			tekst = "U bent verloren, de saus was " + getSaus();
		}
		if(puntjes.contains(dot) == false){
			tekst = "U bent gewonnen, de saus was " + getSaus();
		}
		return tekst;
	}

}
