package beans;

public class  ObjV {
    private Long Code_Clt;
    private String nom;
    private String prenom;
    private String Chiffre_affaire;
    private String Categorie;
    private int id_pro;
    private int qte_dispo;
    private String lib_pro;
    private Double prix_unit_pro;
    private String fuction;

    public ObjV(){

    }
    public ObjV(Long Code_Clt, String nom, String prenom, String Chiffre_affaire,
                String Categorie,int id_pro,String lib_pro,Double prix_unit_pro,String fuction,int qte_dispo) {
        Code_Clt = Code_Clt;
        nom = nom;
        prenom = prenom;
        Chiffre_affaire = Chiffre_affaire;
        Categorie = Categorie;
        id_pro = id_pro;
        lib_pro = lib_pro;
        prix_unit_pro =prix_unit_pro;
        fuction =fuction;
        qte_dispo =qte_dispo;
    }


    public Long getCode_Clt() {
        return Code_Clt;
    }

    public void setCode_Clt(Long code_Clt) {
        this.Code_Clt = code_Clt;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getChiffre_affaire() {
        return Chiffre_affaire;
    }

    public void setChiffre_affaire(String chiffre_affaire) {
        this.Chiffre_affaire = chiffre_affaire;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        this.Categorie = categorie;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getLib_pro() {
        return lib_pro;
    }

    public void setLib_pro(String lib_pro) {
        this.lib_pro = lib_pro;
    }

    public Double getPrix_unit_pro() {
        return prix_unit_pro;
    }

    public void setPrix_unit_pro(Double prix_unit_pro) {
        this.prix_unit_pro = prix_unit_pro;
    }

    public String getFuction() {
        return fuction;
    }

    public void setFuction(String fuction) {
        this.fuction = fuction;
    }

    public int getQte_dispo() {
        return qte_dispo;
    }

    public void setQte_dispo(int qte_dispo) {
        this.qte_dispo = qte_dispo;
    }
}
