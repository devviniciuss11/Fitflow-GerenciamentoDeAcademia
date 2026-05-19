package Entidade;

public class AcessoAdm extends Pessoa {
    private String senha;
    private String email;
    public AcessoAdm(String senha, String email) {
        this.senha = senha;
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }

    @Override
    public void VerLogin() {
        System.out.println("Email para realizar o login:" + this.getEmail());
        System.out.println("Senha para realizar o login:" + this.getSenha());
    }

    public String getEmail() {
        return email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static final AcessoAdm ADM_MASTER = new AcessoAdm("5564", "FlowDevs");

}
