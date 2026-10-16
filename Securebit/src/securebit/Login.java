package securebit;

public class Login {
    
    private int id;
    private String titulo;
    private String emailUsuario;
    private String senhaCriptografada;
    private String anotacoes;
    private String tag;

    // Construtor
    public Login(int id, String titulo, String emailUsuario, String senhaCriptografada, String anotacoes, String tag) {
        this.id = id;
        this.titulo = titulo;
        this.emailUsuario = emailUsuario;
        this.senhaCriptografada = senhaCriptografada;
        this.anotacoes = anotacoes;
        this.tag = tag;
    }

    // --- GETTERS E SETTERS OBRIGATÓRIOS ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
