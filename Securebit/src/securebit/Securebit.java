package securebit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.*;

public class Securebit extends JFrame {
    
    private DefaultTableModel tableModel;
    private JTable tabelaLogins;
    private JTextField txtBusca, txtnomeUsuario, txtemail, txtSenha, txtTag;

    public Securebit() {
        setTitle("Securebit");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // --- 1. PAINEL SUPERIOR: FILTRO DE CONTAS RF004X(JAVA)--- 
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Menu Inicial"));
        painelFiltro.add(new JLabel("Filtrar (Usuário ou E-mail)"));
        txtBusca = new JTextField(25);
        painelFiltro.add(txtBusca);
        
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnLimparFiltro = new JButton("Mostrar Todos");
        painelFiltro.add(btnFiltrar);
        painelFiltro.add(btnLimparFiltro);
        
        add(painelFiltro, BorderLayout.NORTH);

        // --- 2. PAINEL CENTRAL: TABELA DO COFRE ---
        String[] colunas = {"ID", "Usuário", "E-mail", "Tag"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaLogins = new JTable(tableModel);
        tabelaLogins.setRowHeight(25);
        tabelaLogins.removeColumn(tabelaLogins.getColumnModel().getColumn(0)); // Oculta o ID visualmente
        
        JScrollPane scrollPane = new JScrollPane(tabelaLogins);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contas"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. PAINEL INFERIOR: FORMULÁRIO DE CADASTRO ---
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Conta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Instancia os campos definindo largura proporcional (15 colunas)
        txtnomeUsuario = new JTextField(15);
        txtemail = new JTextField(15);
        txtSenha = new JTextField(15);
        txtTag = new JTextField(15);

        // Linha 0: Nome de Usuário e E-mail
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("Nome de Usuário: "), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painelCadastro.add(txtnomeUsuario, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("E-mail: "), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        painelCadastro.add(txtemail, gbc);

        // Linha 1: Senha e Tag
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("Senha: "), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painelCadastro.add(txtSenha, gbc);

        gbc.gridx = 2; gbc.weightx = 0.0;
        painelCadastro.add(new JLabel("Tag: "), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        painelCadastro.add(txtTag, gbc);

        // Linha 2: Botão Salvar centralizado RF001X
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4; gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(240, 35));
        painelCadastro.add(btnSalvar, gbc);

        // Rodapé de dica
        JLabel lblDica = new JLabel("Clique em qualquer linha da tabela para copiar a senha.");
        lblDica.setForeground(new Color(0, 102, 204));
        lblDica.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel painelSul = new JPanel(new BorderLayout(5, 5));
        painelSul.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelSul.add(painelCadastro, BorderLayout.CENTER);
        painelSul.add(lblDica, BorderLayout.SOUTH);
        
        add(painelSul, BorderLayout.SOUTH);

        // Carrega dados iniciais do banco
        carregarTabelaDoBanco("");

        // --- EVENTOS DE INTERAÇÃO RF003X---

        btnSalvar.addActionListener(e -> {
            String nomeUsuario = txtnomeUsuario.getText().trim();
            String email = txtemail.getText().trim();
            String senha = txtSenha.getText().trim();
            String tag = txtTag.getText().trim();

            if (nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome de Usuário, E-mail e Senha).", "Importante!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            salvarNoBanco(nomeUsuario, email, senha, tag);
            carregarTabelaDoBanco("");

            txtnomeUsuario.setText("");
            txtemail.setText("");
            txtSenha.setText("");
            txtTag.setText("");
        });

        btnFiltrar.addActionListener(e -> {
            String termo = txtBusca.getText().trim();
            carregarTabelaDoBanco(termo);
        });

        btnLimparFiltro.addActionListener(e -> {
            txtBusca.setText("");
            carregarTabelaDoBanco("");
        });

        // Copiar senha ao clicar na linha da tabela RF003X
        tabelaLogins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int linhaSelecionada = tabelaLogins.getSelectedRow();
                if (linhaSelecionada != -1) {
                    String servico = (String) tableModel.getValueAt(linhaSelecionada, 1);
                    String senhaBanco = buscarSenhaPorServico(servico);

                    if (senhaBanco != null) {
                        StringSelection stringSelection = new StringSelection(senhaBanco);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                        
                        JOptionPane.showMessageDialog(Securebit.this, 
                            "Senha do serviço [" + servico + "] copiada para a área de transferência!", 
                            "Cópia Rápida", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    // --- MÉTODOS DE BANCO DE DADOS ---

    private void salvarNoBanco(String servico, String loginUser, String senha, String tag) {
        String sql = "INSERT INTO credenciais (id_usuario, titulo_servico, login_usuario, senha_encriptada, anotacoes) VALUES (1, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico);
            stmt.setString(2, loginUser);
            stmt.setString(3, senha); 
            stmt.setString(4, tag);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Credencial salva com sucesso na tabela 'credenciais'!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco (Verifique se o usuário ID 1 existe): " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabelaDoBanco(String filtro) {
        tableModel.setRowCount(0);
        String sql = "SELECT id_credencial, titulo_servico, login_usuario, anotacoes FROM credenciais";
        if (!filtro.isEmpty()) {
            sql += " WHERE titulo_servico LIKE ? OR login_usuario LIKE ?";
        }

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (!filtro.isEmpty()) {
                String param = "%" + filtro + "%";
                stmt.setString(1, param);
                stmt.setString(2, param);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_credencial");
                String servico = rs.getString("titulo_servico");
                String loginUser = rs.getString("login_usuario");
                String anotacoes = rs.getString("anotacoes");
                tableModel.addRow(new Object[]{id, servico, loginUser, anotacoes});
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

    private String buscarSenhaPorServico(String servico) {
        String sql = "SELECT senha_encriptada FROM credenciais WHERE titulo_servico = ? LIMIT 1";
        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("senha_encriptada");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar senha: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Securebit().setVisible(true);
        });
    }
}