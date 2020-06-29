package beans;

public class Client {
    private Long code_Clt;
    private String nom_Clt;
    private String prenom_Clt;
    private String numTel_Clt;
    private String adresse_Clt;

    public Client(){

    }

    public Client(Long code_clt, String nom_clt, String prenom_clt, String numTel_clt, String adresse_clt) {
        code_Clt = code_clt;
        nom_Clt = nom_clt;
        prenom_Clt = prenom_clt;
        numTel_Clt = numTel_clt;
        adresse_Clt = adresse_clt;
    }




    public void setCode_Clt(Long code_Clt) {
        this.code_Clt = code_Clt;
    }

    public String getNom_Clt() {
        return nom_Clt;
    }

    public void setNom_Clt(String nom_Clt) {
        this.nom_Clt = nom_Clt;
    }

    public String getPrenom_Clt() {
        return prenom_Clt;
    }

    public void setPrenom_Clt(String prenom_Clt) {
        this.prenom_Clt = prenom_Clt;
    }

    public String getNumTel_Clt() {
        return numTel_Clt;
    }

    public void setNumTel_Clt(String numTel_Clt) {
        this.numTel_Clt = numTel_Clt;
    }

    public String getAdresse_Clt() {
        return adresse_Clt;
    }

    public void setAdresse_Clt(String adresse_Clt) {
        this.adresse_Clt = adresse_Clt;
    }


    public Long getCode_Clt() {
        return code_Clt;
    }
}
