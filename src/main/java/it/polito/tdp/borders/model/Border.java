package it.polito.tdp.borders.model;

public class Border {

	Country c1;
	Country c2;
	int anno;
	public Border(Country c1, Country c2, int anno) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.anno = anno;
	}
	public Country getC1() {
		return c1;
	}
	public void setC1(Country c1) {
		this.c1 = c1;
	}
	public Country getC2() {
		return c2;
	}
	public void setC2(Country c2) {
		this.c2 = c2;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
}
