import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day7 extends BaseUtils{
    public static final String INPUT = getFileName(NUMBER_7, null);
    public static final String TEST = getFileName(NUMBER_7, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_7, NUMBER_2);

    public static List<String> parseLine(String input, String delimiter){
        return List.of(input.split(delimiter));
    }

    public static boolean checkIfTarget(List<Integer> nums, long currValue, int currIndex, long targetValue, String part) {
        if (currIndex == nums.size()) {
            return currValue == targetValue;
        }

        int num = nums.get(currIndex);
        boolean checkAdd = checkIfTarget(nums, currValue + num, currIndex + 1, targetValue, part);
        boolean checkMult = checkIfTarget(nums, currValue * num, currIndex + 1, targetValue, part);

        boolean concatResult = false;
        if (part.equals(NUMBER_2)) {
            //concatResult = checkIfTarget(nums, Long.parseLong(currValue + "" + num), currIndex + 1, targetValue, part);
            concatResult = checkIfTarget(nums, (long) (currValue * Math.pow(10, Math.floor(Math.log10(num)) + 1) + num), currIndex + 1, targetValue, part);
        }


        return part.equals(NUMBER_2) ? checkAdd || checkMult || concatResult : checkAdd || checkMult;
    }

    public static long executePart1(List<String> input){
        long sum = 0;
        for (String item : input){
            long target = Long.parseLong(String.valueOf(parseLine(item, ":").getFirst()));
            List<String> nums = parseLine(parseLine(item, ":").getLast(), " ");
            List<Integer> setInteger = parseToInt(nums.subList(1, nums.size()));
            //System.out.println(target + " -- " + setInteger + " || " + checkIfTarget(setInteger, setInteger.get(0), 1, target));
            if(checkIfTarget(setInteger, setInteger.get(0), 1, target, NUMBER_1)){
                sum += target;
            }
        }
        return sum;
    }

    public static long executePart2(List<String> input){
        long sum = 0;
        for (String item : input){
            long target = Long.parseLong(String.valueOf(parseLine(item, ":").getFirst()));
            List<String> nums = parseLine(parseLine(item, ":").getLast(), " ");
            List<Integer> setInteger = parseToInt(nums.subList(1, nums.size()));
            //System.out.println(target + " -- " + setInteger + " || " + checkIfTarget(setInteger, setInteger.get(0), 1, target));
            if(checkIfTarget(setInteger, setInteger.get(0), 1, target, NUMBER_2)){
                sum += target;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart2(input));
    }
}
