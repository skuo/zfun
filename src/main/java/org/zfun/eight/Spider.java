package org.zfun.eight;

import java.util.Optional;

public class Spider implements Formula {

	private String species = null;
	private String note = null;
	
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// Use Optional
	public Optional<String> getSpiderNote() {
		return Optional.ofNullable(note);
	}
	
	@Override
	public double calculate(int a) {
		return sqrt(a * 100);
	}

	String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}
