package beans;

public class Commande_Fournisseurs {
    private Long id_cmd;
    private Long id_pro ;
    private Long qte_cmd_frs;
    private Long id_Fours ;

    public Commande_Fournisseurs(){

    }

    public Commande_Fournisseurs(Long id_cmd, Long id_pro, Long qte_cmd_frs, Long id_fours) {
        this.id_cmd = id_cmd;
        this.id_pro = id_pro;
        this.qte_cmd_frs = qte_cmd_frs;
        id_Fours = id_fours;
    }

    public Long getId_cmd() {
        return id_cmd;
    }

    public void setId_cmd(Long id_cmd) {
        this.id_cmd = id_cmd;
    }

    public Long getId_pro() {
        return id_pro;
    }

    public void setId_pro(Long id_pro) {
        this.id_pro = id_pro;
    }

    public Long getQte_cmd_frs() {
        return qte_cmd_frs;
    }

    public void setQte_cmd_frs(Long qte_cmd_frs) {
        this.qte_cmd_frs = qte_cmd_frs;
    }

    public Long getId_Fours() {
        return id_Fours;
    }

    public void setId_Fours(Long id_Fours) {
        this.id_Fours = id_Fours;
    }
}
