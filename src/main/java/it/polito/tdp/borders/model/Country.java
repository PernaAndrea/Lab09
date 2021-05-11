package it.polito.tdp.borders.model;

public class Country {

	String stateAbb;
	int cCode;
	String stateNme;
	int nConfini;
	
	public Country(String stateAbb, int cCode, String stateNme) {
		super();
		this.stateAbb = stateAbb;
		this.cCode = cCode;
		this.stateNme = stateNme;
		nConfini =0;
	}

	public int getnConfini() {
		return nConfini;
	}

	public void setnConfini(int nConfini) {
		this.nConfini = nConfini;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public int getcCode() {
		return cCode;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public String getStateNme() {
		return stateNme;
	}

	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}

	@Override
	public String toString() {
		return cCode +"  "+ stateAbb +"  "+ stateNme +" \n";
	}
	
}
