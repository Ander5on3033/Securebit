package securebit;

import java.util.ArrayList;
import java.util.List;

public class Vault {
    
    private final List<Login> listaLogins;
    private boolean bloqueado;

    public Vault() {
        this.listaLogins = new ArrayList<>();
        this.bloqueado = false;
    }

    public void adicionarLogin(Login login) {
        listaLogins.add(login);
    }

    public void removerLogin(int id) {
        // Remove utilizando um loop seguro para evitar erros de versão
        for (int i = 0; i < listaLogins.size(); i++) {
            if (listaLogins.get(i).getId() == id) {
                listaLogins.remove(i);
                break;
            }
        }
    }

    public void editarLogin(int id, String novoTitulo, String novoEmail, String novaSenha, String novasAnotacoes, String novaTag) {
        for (Login login : listaLogins) {
            if (login.getId() == id) {
                login.setTitulo(novoTitulo);
                login.setEmailUsuario(novoEmail);
                login.setSenhaCriptografada(novaSenha);
                login.setAnotacoes(novasAnotacoes);
                login.setTag(novaTag);
                break;
            }
        }
    }

    // Busca e filtra usando 'for' tradicional
    public List<Login> filtrarLogins(String termoBusca) {
        List<Login> resultados = new ArrayList<>();
        
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            resultados.addAll(listaLogins);
            return resultados;
        }
        
        String termoLower = termoBusca.toLowerCase();
        
        for (Login l : listaLogins) {
            boolean matchTitulo = l.getTitulo() != null && l.getTitulo().toLowerCase().contains(termoLower);
            boolean matchEmail = l.getEmailUsuario() != null && l.getEmailUsuario().toLowerCase().contains(termoLower);
            boolean matchTag = l.getTag() != null && l.getTag().toLowerCase().contains(termoLower);
            
            if (matchTitulo || matchEmail || matchTag) {
                resultados.add(l);
            }
        }
        
        return resultados;
    }

    // Filtro por tag usando 'for' tradicional
    public List<Login> filtrarPorTag(String tagBusca) {
        List<Login> resultados = new ArrayList<>();
        
        if (tagBusca == null || tagBusca.trim().isEmpty()) {
            return resultados;
        }
        
        for (Login l : listaLogins) {
            if (l.getTag() != null && l.getTag().equalsIgnoreCase(tagBusca)) {
                resultados.add(l);
            }
        }
        
        return resultados;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public List<Login> getListaLogins() {
        return listaLogins;
    }
}