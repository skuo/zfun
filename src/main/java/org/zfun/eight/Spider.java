package org.zfun.eight;

public class Spider implements Formula {

	private String species = null;
	
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	@Override
	public double calculate(int a) {
		return sqrt(a * 100);
	}

	String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
