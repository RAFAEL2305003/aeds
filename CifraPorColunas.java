// Cifra por colunas
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CifraPorColunas {
    // Metodo para ler arquivos
    public static String readFromFile(String file) {
        String str = "";
        try (FileReader fileReader = new FileReader(file)) {
            int character;
            while ((character = fileReader.read()) != -1) {
                char s = (char) character;
                str += s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    // Metodo para escrever em arquivos
    public static void writeFromFile(String file, String encrypted) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(encrypted);
            System.out.println("\nResultados foram escritos em " + file);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Metodo para criptografar o conteudo do arquivo 
    public static String encrypt(String text, String key) {
        String encrypted = "";

        char[] txt = key.toCharArray();

        // Arredondando a divisao entre o comprimento do texto e o da chave e armazenando na variavel 'rows'
        int rows = (int) Math.ceil((double) text.length() / key.length()), columns = key.length(), index = 0, column;
        
        char[][] matrix = new char[rows][columns];
        
        // Preenchendo matriz com o texto
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
               if(index < text.length()) {
                    matrix[i][j] = text.charAt(index++);
               } 
            }
        }

        // Ordenando o array de chave
        Arrays.sort(txt);

        // Montando a mensagem criptografada
        for(char c : txt) {
            column = key.indexOf(c);
            for(int i = 0; i < rows; i++) {
                encrypted += matrix[i][column];
            }
        }

        return encrypted;
    }
    
    // Metodo para descriptografar o conteudo do arquivo
    public static String decrypt(String text, String key) {
        String decrypted = "";

        char[] txt = key.toCharArray();

        int rows = text.length() / key.length(), columns = key.length(), index = 0, column;
        
        char[][] matrix = new char[rows][columns];
        
        // Preenchendo matriz com o texto criptografado
        for(int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
               if(index < text.length()) {
                    matrix[j][i] = text.charAt(index++);
               } 
            }
        }

        // Ordenando o array de chave
        Arrays.sort(txt);

        // Montando a mensagem descriptografada
        for(int i = 0; i < rows; i++) {
            for (char c : txt) {
                column = key.indexOf(c);
                decrypted += matrix[i][column];
            }
        }

        return decrypted;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int show;

        System.out.print("Lendo arquivo csv...");
        String file = "Netflix.csv", text = readFromFile(file);

        System.out.println("\n");

        System.out.print("Digite a chave para criptografia: ");
        String key = sc.nextLine();

        System.out.println();

        System.out.print("Criptografando...");

        String encrypted = encrypt(text, key);

        file = "CriptografiaC.csv";
        writeFromFile(file, encrypted);

        System.out.print("\nExibir texto criptografado? (1: sim, 0: nao): ");
        show = Integer.valueOf(sc.nextLine());

        if(show == 1) {
            System.out.println("\nTexto criptografado:");
            System.out.println(encrypted);
        }

        System.out.println();
        System.out.print("Deseja descriptografar o arquivo? (1: sim, 0: nao): ");
        show = Integer.valueOf(sc.nextLine());

        if(show == 1) {
            System.out.println("\n");
            System.out.print("Descriptografando...");
            
            String decrypted = decrypt(encrypted, key);

            file = "DescriptografiaC.csv";
            writeFromFile(file, decrypted);

            System.out.print("\nExibir texto descriptografado? (1: sim, 0: nao): ");
            show = Integer.valueOf(sc.nextLine());

            if(show == 1) {
                System.out.println("\nTexto descriptografado:");
                System.out.println(decrypted);
            }
        }

        sc.close();
    }
}