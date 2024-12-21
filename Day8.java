import java.util.*;

public class Day8 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_8, null);
    public static final String TEST = getFileName(NUMBER_8, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_8, NUMBER_2);

    public static HashMap<String, List<List<Integer>>> getAntenas(List<String> input){
        HashMap<String, List<List<Integer>>> temp = new HashMap<>();
        for (int i = 0; i < input.size(); i++){
            for(int j = 0; j < input.get(i).length(); j++){
                if(String.valueOf(input.get(i).charAt(j)).matches("[a-zA-Z0-9]")){
                    try {
                        List<List<Integer>> getCurrentCoords = temp.get(String.valueOf(input.get(i).charAt(j)));
                        getCurrentCoords.add(List.of(i,j));
                        temp.put(String.valueOf(input.get(i).charAt(j)), getCurrentCoords);
                    } catch (Exception e) {
                        List<List<Integer>> empty = new ArrayList<>();
                        empty.add(List.of(i,j));
                        temp.put(String.valueOf(input.get(i).charAt(j)), empty);
                    }
                }
            }
        }

        return temp;
    }

    public static List<List<Integer>> getSubset(int setOrder, int subsetOrder) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> subset = new ArrayList<>();
        generateSubset(setOrder, subsetOrder, 0, subset, result);
        return result;
    }

    public static void generateSubset(int setOrder, int subsetOrder, int start, List<Integer> subset, List<List<Integer>> result) {
        if (subset.size() == subsetOrder) {
            result.add(new ArrayList<>(subset));
            return;
        }

        for (int i = start; i < setOrder; i++) {
            subset.add(i);
            generateSubset(setOrder, subsetOrder, i + 1, subset, result);
            subset.removeLast();
        }
    }

    public static int executePart1(List<String> input){
        int sum = 0;
        HashSet<List<Integer>> antinodes = new HashSet<>();
        HashMap<String, List<List<Integer>>> antenas = getAntenas(input);
        HashMap<Integer, List<List<Integer>>> combinations = new HashMap<>();

        System.out.println(antenas);

        for (String key : antenas.keySet()) {
            List<List<Integer>> subsets;
            int tempSize = antenas.get(key).size();

            if (combinations.get(tempSize) != null) {
                subsets = combinations.get(tempSize);
            } else {
                subsets = getSubset(tempSize, Integer.parseInt(NUMBER_2));
                combinations.put(tempSize, subsets);
            }

            for (List<Integer> sub : subsets){
                List<Integer> getAntena1 = antenas.get(key).get(sub.getFirst());
                List<Integer> getAntena2 = antenas.get(key).get(sub.getLast());

                int xMove = getAntena2.getFirst() - getAntena1.getFirst();
                int yMove = getAntena2.getLast() - getAntena1.getLast();
            }

        }



        return sum;
    }

    public static int executePart2(List<String> input){
        int sum = 0;

        return sum;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(TEST);
        showResult(() -> executePart1(input));
    }
}
