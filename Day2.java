import java.util.ArrayList;
import java.util.List;

public class Day2 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_2, null);
    public static final String TEST = getFileName(NUMBER_2, NUMBER_1);

    public static List<Integer> parseLine(String line) {
        List<Integer> integerForm = new ArrayList<>();
        List<String> splitLine = List.of(line.split("\\s+"));

        for (String item : splitLine) {
            integerForm.add(Integer.parseInt(item));
        }
        return integerForm;
    }

    public static boolean checkSequence(List<Integer> input){
        boolean increaseFlag = false;

        int initialDiff = input.get(1) - input.get(0);
        if(Math.abs(initialDiff) > 3 || initialDiff == 0){
            return false;
        }

        if(initialDiff > 0) {
            increaseFlag = true;
        }

        for(int i = 1; i < input.size() - 1; i++){
            int diff = input.get(i+1) - input.get(i);
            if(Math.abs(diff) > 3 || diff == 0){
                return false;
            }

            if(diff > 0 && !increaseFlag){
                return false;
            } else if (diff < 0 && increaseFlag) {
                return false;
            }
        }

        return true;
    }

    public static int executePart1(List<String> input) {
        int count = 0;
        for (String item : input) {
            List<Integer> parsedLine = parseLine(item);
            if(checkSequence(parsedLine)) {
                count ++;
            }
        }

        return count;
    }

    public static int executePart2(List<String> input) {
        int count = 0;
        for (String item : input) {
            List<Integer> parsedLine = parseLine(item);
            for (int i = 0; i < parsedLine.size(); i++){
                int temp = parsedLine.get(i);
                parsedLine.remove(i);
                if(checkSequence(parsedLine)) {
                    count ++;
                    break;
                }
                parsedLine.add(i, temp);
            }

        }

        return count;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart1(input));
    }
}
