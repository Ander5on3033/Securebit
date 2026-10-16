package securebit.Service;

public class AutenticacaoService {
    
    private String senhaMestra; // Em um sistema de produção ideal, armazena-se o hash da senha (ex: SHA-256)
    private int tentativasFalhas;
    private static final int LIMITE_TENTATIVAS = 3; // RNF004: Limite de tentativas de login
    private long ultimaAtividade;
    private long tempoLimiteInatividadeMillis = 5 * 60 * 1000; // RNF002: Timeout padrão de 5 minutos de inatividade
    private boolean bloqueadoTemporariamente;

    // Construtor: Inicializa o serviço definindo a senha mestra padrão
    public AutenticacaoService(String senhaMestraCadastrada) {
        this.senhaMestra = senhaMestraCadastrada;
        this.tentativasFalhas = 0;
        this.bloqueadoTemporariamente = false;
        atualizarUltimaAtividade();
    }

    /**
     * Valida a senha mestra informada pelo usuário.
     * Atende ao RNF004 (Bloqueio temporário após tentativas falhas).
     */
    public boolean validarSenhaMestra(String senhaDigitada) {
        if (bloqueadoTemporariamente) {
            System.out.println("Acesso bloqueado temporariamente por excesso de tentativas.");
            return false;
        }

        if (this.senhaMestra.equals(senhaDigitada)) {
            // Sucesso na autenticação: reseta as falhas e atualiza a atividade
            tentativasFalhas = 0;
            atualizarUltimaAtividade();
            return true;
        } else {
            // Falha na autenticação: incrementa o contador
            tentativasFalhas++;
            if (tentativasFalhas >= LIMITE_TENTATIVAS) {
                bloqueadoTemporariamente = true;
                System.out.println("Limite de tentativas excedido! Sistema bloqueado temporariamente.");
            }
            return false;
        }
    }

    /**
     * Simula ou gerencia o suporte à autenticação biométrica ou PIN alternativo.
     * Atende ao RNF003 (Autenticação biométrica).
     */
    public boolean autenticarBiometria() {
        if (bloqueadoTemporariamente) {
            return false;
        }
        // Aqui entraria a integração nativa ou via biblioteca com o leitor biométrico do dispositivo.
        atualizarUltimaAtividade();
        return true;
    }

    /**
     * Atualiza o carimbo de tempo da última ação do usuário.
     */
    public void atualizarUltimaAtividade() {
        this.ultimaAtividade = System.currentTimeMillis();
    }

    /**
     * Verifica se a sessão expirou devido à inatividade.
     * Atende ao RNF002 (Timeout de sessão).
     */
    public boolean verificarTimeout() {
        long tempoAtual = System.currentTimeMillis();
        long tempoDecorrido = tempoAtual - ultimaAtividade;
        
        return tempoDecorrido > tempoLimiteInatividadeMillis;
    }

    // Getters e Setters de controle de segurança
    public boolean isBloqueadoTemporariamente() {
        return bloqueadoTemporariamente;
    }

    public void setBloqueadoTemporariamente(boolean bloqueadoTemporariamente) {
        this.bloqueadoTemporariamente = bloqueadoTemporariamente;
    }

    public void setTempoLimiteInatividadeMinutos(int minutos) {
        this.tempoLimiteInatividadeMillis = minutos * 60L * 1000L;
    }
}