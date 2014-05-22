package de.metamob.badesee;

public class Badestelle {
	private String name;
	private String profil;
	private String bezirk;
	private String datum;
	private String sichttiefe;
	private String enterokokken;
	private String ecoli;
	private String wasserqualitaet;
	private String profilurl;
	
	public enum EWasserqualitaet { gruen, gelb, rot };
	
	
	
	/**
	 * @param name
	 * @param profil
	 * @param bezirk
	 * @param datum
	 * @param sichttiefe
	 * @param enterokokken
	 * @param ecoli
	 * @param wasserqualitaet
	 * @param profilurl
	 */
	public Badestelle(String name, String profil, String bezirk, String datum,
			String sichttiefe, String enterokokken, String ecoli,
			EWasserqualitaet wasserqualitaet, String profilurl) {
		super();
		this.name = name;
		this.profil = profil;
		this.bezirk = bezirk;
		this.datum = datum;
		this.sichttiefe = sichttiefe;
		this.enterokokken = enterokokken;
		this.ecoli = ecoli;
		this.wasserqualitaet = wasserqualitaet.toString();
		this.profilurl = profilurl;
	}

	public String getProfilurl() {
		return profilurl;
	}

	public void setProfilurl(String profilurl) {
		this.profilurl = profilurl;
	}

	public String getWasserqualitaet() {
		return wasserqualitaet;
	}

	public void setWasserqualitaet(EWasserqualitaet wasserqualitaet) {
		this.wasserqualitaet = wasserqualitaet.toString();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfil() {
		return profil;
	}
	public void setProfil(String profil) {
		this.profil = profil;
	}
	public String getBezirk() {
		return bezirk;
	}
	public void setBezirk(String bezirk) {
		this.bezirk = bezirk;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getSichttiefe() {
		return sichttiefe;
	}
	public void setSichttiefe(String sichttiefe) {
		this.sichttiefe = sichttiefe;
	}
	public String getEnterokokken() {
		return enterokokken;
	}
	public void setEnterokokken(String enterokokken) {
		this.enterokokken = enterokokken;
	}
	public String getEcoli() {
		return ecoli;
	}
	public void setEcoli(String ecoli) {
		this.ecoli = ecoli;
	}
}
