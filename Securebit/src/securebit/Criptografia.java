package securebit;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Criptografia {
    
    // Chave secreta fixa de 16 caracteres para o AES (exigência padrão do algoritmo AES-128)
    // Dica: Em versões mais avançadas do sistema, essa chave pode vir da senha mestra do usuário.
    private static final String CHAVE_SECRETA = "SecureBitChave16"; 
    private static final String ALGORITMO = "AES";

    /**
     * Criptografa um texto plano (como a senha do usuário) em uma string codificada em Base64.
     */
    public static String criptografar(String textoPlano) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(CHAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] bytesCriptografados = cipher.doFinal(textoPlano.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(bytesCriptografados);
            
        } catch (Exception e) {
            System.err.println("Erro ao criptografar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Descriptografa a string cifrada de volta para o texto original (ex: para exibir a senha ao usuário autorizado).
     */
    public static String descriptografar(String textoCriptografado) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(CHAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] decodeBytes = Base64.getDecoder().decode(textoCriptografado);
            byte[] bytesDescriptografados = cipher.doFinal(decodeBytes);
            
            return new String(bytesDescriptografados, "UTF-8");
            
        } catch (Exception e) {
            System.err.println("Erro ao descriptografar: " + e.getMessage());
            return null;
        }
    }
}