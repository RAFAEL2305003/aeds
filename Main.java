
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Lzw lzw = new Lzw();
        
        System.out.print("\nYour input: ");
        String input = sc.nextLine();

        int[] result = lzw.compress(input);
        
        System.out.print("\nCompressed text: ");
    
        for (int i = 0; i < result.length; i++) {
            if(result[i] != -1) {
                System.out.print(result[i] + " ");
            }
        }
        
        System.out.println("\nDescompressed text: " + lzw.decompress(result) + "\n");
    
        sc.close();
    }
}