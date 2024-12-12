package usuarios;

import banco.Banco;

public abstract class Usuario {
	private String usuario;
	private String usuarioPai;
    private String senha;

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }
    public Usuario(String usuario, String usuarioPai, String senha) {
        this.usuario = usuario;
        this.usuarioPai = usuarioPai;
        this.senha = senha;
    }

    public String getUsuario() {
        return this.usuario;
    }
    
	public String getUsuarioPai() {
		  return this.usuarioPai;
	}
	
    public String getSenha() {
        return this.senha;
    }
    
	public void setUsuario(String usuario) {
		this.usuario = usuario;
    }

	public void setUsuarioPai(String UsuarioPai) {
		this.usuarioPai = UsuarioPai;
	}
	
    public void setSenha(String senha) {
		this.senha = senha;
    }

	public void menu(Banco banco, Usuario usuario) {

	}
    


}
