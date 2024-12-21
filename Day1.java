import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Splitter {
    public final List<Integer> left;
    public final List<Integer> right;

    Splitter(List<Integer> left, List<Integer> right) {
        this.left = left;
        this.right = right;
    }
}

public class Day1 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_1, null);
    public static final String TEST = getFileName(NUMBER_1, NUMBER_1);

    public static Splitter splitList(List<String> input){
        List<Integer> left = new ArrayList<Integer>();
        List<Integer> right = new ArrayList<Integer>();

        for (String item : input){
            List<String> splitInput = List.of(item.split("\\s+"));

            left.add(Integer.parseInt(splitInput.getFirst()));
            right.add(Integer.parseInt(splitInput.getLast()));
        }

        return new Splitter(left, right);
    };

    public static int executePart1(List<String> input) {
        List<Integer> firstList = splitList(input).left;
        List<Integer> secondList = splitList(input).right;
        int sum = 0;

        Collections.sort(firstList);
        Collections.sort(secondList);

        for (int i = 0; i < firstList.size(); i++) {
            int difference = Math.abs(secondList.get(i) - firstList.get(i));
            sum += difference;
        }

        return sum;
    };

    public static int executePart2(List<String> input) {
        List<Integer> firstList = splitList(input).left;
        List<Integer> secondList = splitList(input).right;
        int sum = 0;

        for (int item : firstList) {
            sum += item * Collections.frequency(secondList, item);
        }

        return sum;
    };

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart1(input));
    }
}