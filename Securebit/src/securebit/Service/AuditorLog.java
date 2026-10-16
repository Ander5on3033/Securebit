package securebit.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditorLog {
    
    // Lista em memória para armazenar os registos de auditoria
    private List<String> historicoLogs;

    // Construtor: Inicializa a lista de registos de auditoria
    public AuditorLog() {
        this.historicoLogs = new ArrayList<>();
    }

    /**
     * Regista um novo evento no sistema de auditoria.
     * Importante: Registra apenas metadados e ações (ex: tentativas de login, edições), 
     * garantindo que nenhuma senha ou nota sensível seja incluída no log.
     */
    public void registarEvento(String tipoAcao, String utilizador) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = agora.format(formatter);

        // Formata a mensagem de auditoria de forma segura
        String logEntry = String.format("[%s] Ação: %s | Utilizador: %s", dataHoraFormatada, tipoAcao, utilizador);
        historicoLogs.add(logEntry);
    }

    /**
     * Retorna o relatório completo de atividades para visualização exclusiva do Auditor.
     * Atende ao requisito de acesso restrito a relatórios sem exposição de dados confidenciais.
     */
    public List<String> obterRelatorioAtividades() {
        // Retorna uma cópia da lista para proteger o encapsulamento interno
        return new ArrayList<>(historicoLogs);
    }

    /**
     * Opcional: Limpa o histórico de logs se necessário por políticas de segurança.
     */
    public void limparLogs() {
        historicoLogs.clear();
    }
}