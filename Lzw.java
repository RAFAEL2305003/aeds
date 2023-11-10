import java.util.Map;
import java.util.HashMap;

class Lzw {
  // Definindo dados
  private Map<Integer, String> MapDecompress;
  private Map<String, Integer> MapCompress;

  // Construtor padrao
  public Lzw() {
    MapDecompress = new HashMap<>();
    MapCompress = new HashMap<>();
    
    // Inicializando os dicionarios com toda tabela ascii
    for (int i = 0; i < 255; i++) {
      char c = (char)i;
      MapCompress.put(String.valueOf(c), i);
      MapDecompress.put(i ,String.valueOf(c));
    }
  }

  // Metodo para comprimir os arquivos
  public int[] compress(String input) {
    int j = 0, code = 0, index = 0;

    int[] v = new int[input.length()];

    // Inicializando o vetor de compressao com o tamanho da entrada
    for(int i = 0; i < v.length; i++){
      v[i] = -1;
    }

    String p = String.valueOf(input.charAt(0)), c, pc; 
    j = 1;

    // Percorrendo a string a partir da segunda posicao
    while(j < input.length()) {
      c = String.valueOf(input.charAt(j));
      pc = p + c;

      // Checando se "pc" ja existe no dicionario
      if(MapCompress.containsKey(String.valueOf(pc))) {
        p = pc;
      } else {
        code = MapCompress.get(p);
        v[index++] = code;
        MapCompress.put(pc, MapCompress.size());
        p = String.valueOf(c);
      }
    
      j++;
    }

    // Salvando no vetor a ultima posicao do dicionario
    int codeLast = MapCompress.get(p);
    v[index] = codeLast;

    return v;
  }

  // Metodo para descomprimir os arquivos
  public String decompress(int[] compressedText) {
    String result = "", s = "", c = "";

    int pos = compressedText[0], i = 1, newPos = 0;
    result += MapDecompress.get(pos);
    
    // Percorrendo a string a partir da segunda posicao
    while(i < compressedText.length) {
      if(compressedText[i] != -1) {
        newPos = compressedText[i];

        // Checando se o mapa de descompressao da posicao "newPos" nao existe
        if(!MapDecompress.containsKey(newPos)) {
          s = MapDecompress.get(pos);
          s = s + c;
        } else {
          s = MapDecompress.get(newPos);
        }
        
        // Ignorando a ultima posicao da string
        if(i != compressedText.length - 1) {
          result += s;
          c = s.charAt(0) + "";
          MapDecompress.put(MapDecompress.size(), MapDecompress.get(pos) + c);
          pos = newPos;
        }
      }

      i++;
    }

    return result;
  }
  
  // Metodos para mostrar o dicionario
  /*public void showCompressedMap () {
    System.out.println("\nDictionary of compression:\n");
    List<Map.Entry<String, Integer>> list = new ArrayList<>(MapCompress.entrySet());

    list.sort(Comparator.comparing(Map.Entry::getValue));

    for (Map.Entry<String, Integer> entry : list) {
      System.out.println(entry.getValue() + ": " + entry.getKey());
    }
  }

  public void showDecompressedMap() {
    System.out.println("\nDictionary of decompression:\n");

    for(int key : MapDecompress.keySet()) {
      System.out.println(key + ": " + MapDecompress.get(key));
    }
  }*/
}
