// Criptografia por substituicao simples
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CifraPorSubstituicao {
    // Verificando se um caracter eh letra
    public static boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

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

    // Metodo para procurar certo elemento no array e retornar verdadeiro caso ele exista no mesmo
    public static boolean search(char[] arr, char c) {
        boolean res = false;
        for (int i = 0; !res && i < arr.length; i++) {
            if(arr[i] == c) {
                res = true;
            }
        }
        return res;
    }
    
    // Metodo para procurar certo elemento no array e retornar a posicao dele caso exista no mesmo
    public static int searchI(char[] arr, char c) {
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == c) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    // Função para criptografar e descriptografar o array
    public static String criptografarEdescriptografar(String in, char[] arr1, char[] arr2) {
        int x;
        char c;
        String result = "";

        for (int i = 0; i < in.length(); i++) {
            c = in.charAt(i);
            if(isLetter(c)) {
                if(search(arr1, c)) {
                    x = searchI(arr1, c);
                    result += arr2[x];
                } else {
                    x = searchI(arr2, c);
                    result += arr1[x];
                }
            } else {
                result += c;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int show;
        String txt1 = "abcdefghijklmABCDEFGHIJKLM", txt2 = "nopqrstuvwxyzNOPQRSTUVWXYZ";
        char[] v1 = txt1.toCharArray(), v2 = txt2.toCharArray();

        String file = "Netflix.csv", text = "", encrypted = "", decrypted = "";
        System.out.print("Lendo arquivo csv...");
        text = readFromFile(file);

        System.out.println("\n");

        System.out.print("Criptografando...");
        file = "CriptografiaS.csv";
        encrypted = criptografarEdescriptografar(text, v1, v2);
        writeFromFile(file, encrypted);
        
        System.out.print("\nExibir texto criptografado? (1: sim, 0: nao): ");
        show = Integer.valueOf(scan.nextLine());

        if(show == 1) {
            System.out.println("\nTexto criptografado:");
            System.out.println(encrypted);
        }

        System.out.println();
        System.out.print("Deseja descriptografar o arquivo? (1: sim, 0: nao): ");
        show = Integer.valueOf(scan.nextLine());

        if(show == 1) {
            System.out.println("\n");

            System.out.print("Descriptografando...");
            file = "DescriptografiaS.csv";
            decrypted = criptografarEdescriptografar(encrypted, v1, v2);
            writeFromFile(file, decrypted);

            System.out.print("\nExibir texto descriptografado? (1: sim, 0: nao): ");
            show = Integer.valueOf(scan.nextLine());

            if(show == 1) {
                System.out.println("\nTexto descriptografado:");
                System.out.println(decrypted);
            }
        }

        scan.close();
    }
}