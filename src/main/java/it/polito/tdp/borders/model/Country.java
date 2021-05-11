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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
		result = prime * result + nConfini;
		result = prime * result + ((stateAbb == null) ? 0 : stateAbb.hashCode());
		result = prime * result + ((stateNme == null) ? 0 : stateNme.hashCode());
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
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		if (nConfini != other.nConfini)
			return false;
		if (stateAbb == null) {
			if (other.stateAbb != null)
				return false;
		} else if (!stateAbb.equals(other.stateAbb))
			return false;
		if (stateNme == null) {
			if (other.stateNme != null)
				return false;
		} else if (!stateNme.equals(other.stateNme))
			return false;
		return true;
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
