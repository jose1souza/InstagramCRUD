package model;

public enum UserGender {
	M("Masculino"), 
	F("Feminino");
	
	private String value;
	
	UserGender(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
