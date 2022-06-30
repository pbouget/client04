package fr.bouget.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Adresse implements Serializable {
	public static final long serialVersionUID=987654378L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=50)
	private String voie;
	@Column(length=50)
	private String complement;
	@Column(length=5)
	private String codePostal;
	@Column(length=50)
	private String ville;
	@Column(length=50)
	private String pays;
	
	@OneToOne(mappedBy = "adresse") // attribut adresse dans Client
	@JsonIgnore
	private Client client;

	public Adresse(){
		this(null, null, null,null,null);
	}

	public Adresse(String voie, String complement, String codePostal, String ville, String pays){
		this.id=0;
		this.voie=voie;
		this.complement=complement;
		this.codePostal=codePostal;
		this.ville=ville;
		this.pays=pays;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoie() {
		return voie;
	}

	public void setVoie(String voie) {
		this.voie = voie;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	@Override
	public String toString(){
		return id+" "+voie+" "+complement+" "+codePostal+" "+ville+" "+pays;
	}
}
