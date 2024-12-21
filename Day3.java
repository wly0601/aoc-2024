import java.util.ArrayList;
import java.util.List;

public class Day3 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_3, null);
    public static final String TEST = getFileName(NUMBER_3, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_3, NUMBER_2);

    public static String getPair(int start, String input){
        StringBuilder s = new StringBuilder();
        for (int j = start; j < input.length(); j ++) {
            s.append(input.charAt(j));
            if(String.valueOf(s.charAt(s.length() - 1)).equals(")")) {
                return s.substring(0, s.length() - 1);
            } else if (String.valueOf(s).contains("mul(")){
                return "";
            }
        }

        return  "Input to large!, are you sure this is the right input?";
    }

    public static List<Integer> ableToParsed(String str) {
        List<Integer> splitString;
        try {
            splitString = parseToInt(List.of(str.split(",")));
        } catch (NumberFormatException e) {
            return new ArrayList<Integer>();
        }
        return splitString;
    }

    public static int executePart1(List<String> input) {
        String joinedInput = String.join("", input);
        int i = 0;
        int count = 0;
        while (i < joinedInput.length() - 4){
            String getMul = joinedInput.substring(i, i + 4);
            String pair = getPair(i + 4, joinedInput);
            if (getMul.equals("mul(") && !pair.isEmpty() && !ableToParsed(pair).isEmpty()){
                List<Integer> parsed = ableToParsed(pair);
                count += parsed.getFirst() * parsed.getLast();
            }

            i ++;
        }
        return count;
    }

    public static int executePart2(List<String> input) {
        String joinedInput = String.join("", input);
        int i = 0;
        int count = 0;
        String doMultiplication = "";

        while (i < joinedInput.length() - 4){
            String getMul = joinedInput.substring(i, i + 4);
            String pair = getPair(i + 4, joinedInput);

            if(getMul.equals("do()")){
                doMultiplication = "y";
            } else if (getMul.equals("don'")) {
                if(getMul.concat("t()").equals("don't()")){
                    doMultiplication = "n";
                }
            }

            if (getMul.equals("mul(") && !pair.isEmpty() && !ableToParsed(pair).isEmpty()){
                List<Integer> parsed = ableToParsed(pair);
                // System.out.println(parsed + " -- " + doMultiplication);
                if(doMultiplication.equals("y") || doMultiplication.isEmpty()) {
                    count += parsed.getFirst() * parsed.getLast();
                }
            }

            i ++;
        }
        return count;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart1(input));
    }
}
