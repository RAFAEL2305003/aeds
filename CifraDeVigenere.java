// Cripografia de Vigenere
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Classe auxiliar
class Alphabet {
    public char[] data;
    public int index;

    public Alphabet() {
        data = new char[2];
    }
}

public class CifraDeVigenere {
    // Verificando se um caracter eh letra
    public static boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    // Verificando se uma letra eh minuscula
    public static boolean isLower(char c) {
        return (c >= 'a' && c <= 'z');
    }

    // Verificando se uma letra eh maiuscula
    public static boolean isUpper(char c) {
        return (c >= 'A' && c <= 'Z');
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

    // Metodo para ler o alfabeto e preencher um vetor com suas posicoes de acordo com a string 'str'
    public static int[] processStr(Alphabet[] v, String str) {
        int[] arr = new int[str.length()];

        char c = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if(isLetter(c)) {
                arr[i] = search(v, c);
            }
        }

        return arr;
    }

    // Metodo para retornar o indice de uma letra presente no alfabeto
    public static int search(Alphabet[] v, char c) {
        int res = 0;

        for(int i = 0; i < v.length; i++) {
            if(v[i].data[0] == c || v[i].data[1] == c) {
                res = v[i].index;
                break;
            }
        }

        return res;
    }

    // Metodo para retornar uma letra do alfabeto
    public static char searchC(Alphabet[] v, int c, boolean letter) {
        char res = 0;

        for(int i = 0; i < v.length; i++) {
            if(letter && v[i].index == c) {
                res = v[i].data[0];
                break;
            } else if(!letter && v[i].index == c) {
                res = v[i].data[1];
                break;
            }
        }

        return res;
    }

    // Metodo para criptografar o conteudo do arquivo 
    public static String encrypt(int[] arrText, int[] arrKey, Alphabet[] v, String text) {
        String encrypted = "";

        char c;
        boolean letterLower;
        int sum = 0, count = 0;

        for(int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if(isLetter(c)) {
                sum = (arrText[i] + arrKey[count++]) % 26;

                if(count >= arrKey.length) {
                    count = 0;
                }

                if(isLower(c)) {
                    letterLower = true;
                    encrypted += searchC(v, sum, letterLower);
                } else if(isUpper(c)) {
                    letterLower = false;
                    encrypted += searchC(v, sum, letterLower);
                }
            } else {
                encrypted += c;
            }
        }

        return encrypted;
    }

    // Metodo para descriptografar o conteudo do arquivo
    public static String decrypt(int[] arrText, int[] arrKey, Alphabet[] v, String text) {
        String decrypted = "";

        char c;
        boolean letterLower;
        int sub = 0, count = 0;

        for(int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if(isLetter(c)) {
                sub = (search(v, c) - arrKey[count++]) % 26;

                if(sub < 0) {
                    sub = 26 + sub;
                } 
                
                if(count >= arrKey.length) {
                    count = 0;
                }

                if(isLower(c)) {
                    letterLower = true;
                    decrypted += searchC(v, sub, letterLower);
                } else if(isUpper(c)) {
                    letterLower = false;
                    decrypted += searchC(v, sub, letterLower);
                }
            } else {
                decrypted += c;
            }
        }

        return decrypted;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int show;
        Alphabet[] v = new Alphabet[26];

        String text = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ", key, encrypted = "", decrypted = "", file = "Netflix.csv";
        char[] txt = text.toCharArray();

        for (int i = 0; i < v.length; i++) {
            v[i] = new Alphabet();
        }

        for (int i = 0; i < txt.length; i += 2) {
            v[i / 2].index = i / 2;
            v[i / 2].data[0] = txt[i];
            v[i / 2].data[1] = txt[i + 1];
        }
        
        text = "";
        System.out.print("Lendo arquivo csv...");
        text = readFromFile(file);

        System.out.println("\n");

        System.out.print("Digite a chave para a criptografia: ");
        key = sc.nextLine();
        System.out.println();

        int[] arrText = processStr(v, text);
        int[] arrKey = processStr(v, key);

        System.out.print("Criptografando...");

        encrypted = encrypt(arrText, arrKey, v, text);

        file = "CriptografiaV.csv";
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

            decrypted = decrypt(arrText, arrKey, v, encrypted);

            file = "DescriptografiaV.csv";
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
