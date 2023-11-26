import java.util.Scanner;
import java.io.*;

public class CifraDeCesar {
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

    public static boolean ehLetra(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    // Metodo para criptografar o texto do arquivo
    public static String criptografar(String texto, int chave) {
        String result = "";

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (ehLetra(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int x = (c - base + chave) % 26;
                char ec = (char) (base + x);
                result += ec;
            } else {
                result += c;
            }
        }

        return result;
    }

    // Metodo para descriptografar o texto do arquivo
    public static String descriptografar(String texto, int chave) {
        String result = "";

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (ehLetra(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int x = (c - base - chave + 26) % 26;
                char dc = (char) (base + x);
                result += dc;
            } else {
                result += c;
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o valor da chave: ");
        int chave = Integer.valueOf(sc.nextLine()), show;

        while(chave <= 0) {
            System.out.print("Digite o valor da chave: ");
            chave = Integer.valueOf(sc.nextLine());
        }

        System.out.println();
        System.out.print("Lendo arquivo csv...");
        String file = "Netflix.csv", text = readFromFile(file);

        System.out.println("\n");

        System.out.print("Criptografando...");

        String encrypted = criptografar(text, chave);

        file = "CriptografiaCesar.csv";
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
            
            String decrypted = descriptografar(encrypted, chave);

            file = "DescriptografiaCesar.csv";
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