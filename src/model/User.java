package model;

import java.util.List;

import model.auth.PasswordHash;

public class User {
	private int id;
	private String name;
	private UserGender gender;
	private String email;
	private List<Post> posts;
	private String passwordHash;
	
	public User(int id) {
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}
	
	public UserGender getGender() {
		return gender;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public void setPassword(String password){
		this.passwordHash = PasswordHash.hashPassword(password);
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void validate() {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
		}
		
		if (gender == null) {
			throw new IllegalArgumentException("O sexo do usuário é inválido.");
		}
		
		if (email == null || email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("O email do usuário é inválido.");
		}	
	}

	@Override
	public String toString() {
		return name;
	}
	
}
