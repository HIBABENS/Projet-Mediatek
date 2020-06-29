package beans;

public class Fournisseur {
    private Long id;
    private String nom;
    private String numTel;
    private String adresse;
    private String type_Fournissement;

    public Fournisseur() {
    }

    public Fournisseur(String nom, String numTel, String adresse,String type_Fournissement) {
        this.nom = nom;
        this.numTel = numTel;
        this.adresse = adresse;
        this.type_Fournissement=type_Fournissement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType_Fournissement() {
        return type_Fournissement;
    }

    public void setType_Fournissement(String type_Fournissement) {
        this.type_Fournissement = type_Fournissement;
    }

    @Override
    public String toString() {
        return "Fournisseur{" + "id=" + id + ", nom=" + nom + ", numTel=" + numTel + ", adresse=" + adresse + '}';
    }

}
