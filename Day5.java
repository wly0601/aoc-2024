import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;
import java.util.stream.Collectors;

class Inputs {
    public final List<String> input;
    public final HashMap<String, List<String>> rules;
    public List<List<String>> updates;

    Inputs(List<String> input) {
        this.rules = generateRulesAndUpdates(input);
        this.input = input;
    }

    private void generateUpdates(String str){
        List<String> split = List.of(str.split(","));
        if(this.updates == null){
            this.updates = new ArrayList<>();
            this.updates.add(split);
        } else {
            this.updates.add(split);
        }
    };

    private HashMap<String, List<String>> generateRulesAndUpdates(List<String> input){
        HashMap<String, List<String>> rules = new HashMap<String, List<String>>();
        boolean switchToProduce = false;
        for(String str : input){
            if(str.isEmpty()){
                switchToProduce = true;
                continue;
            }
            if(!switchToProduce){
                List<String> split = List.of(str.split("\\|"));
                if(!rules.containsKey(split.getFirst())){
                    List<String> initial = new ArrayList<>();
                    initial.add(split.getLast());
                    rules.put(split.getFirst(), initial);
                } else {
                    List<String> newValue = rules.get(split.getFirst());
                    newValue.add(split.getLast());
                    rules.put(split.getFirst(), newValue);
                }
            } else {
                generateUpdates(str);
            }

        }

        return rules;
    }
}

public class Day5 extends BaseUtils {
    public static final String INPUT = getFileName(NUMBER_5, null);
    public static final String TEST = getFileName(NUMBER_5, NUMBER_1);
    public static final String TEST2 = getFileName(NUMBER_5, NUMBER_2);

    public static int getMiddle(List<String> input){
        return Integer.parseInt(input.get((input.size() - 1) / 2));
    }

    public static List<List<String>> getWrongUpdates(HashMap<String, List<String>> rules, List<List<String>> updates){
        List<List<String>> wrongUpdates = new ArrayList<>();
        for (List<String> item : updates){
            for (int i = 0; i < item.size(); i++){
                List<String> subList = item.subList(i+1, item.size());
                List<String> rule = rules.get(item.get(i));
                if(rule == null && i < item.size() - 1) {
                    wrongUpdates.add(item);
                    break;
                }

                if(rule != null && !new HashSet<>(rule).containsAll(subList)){
                    wrongUpdates.add(item);
                    break;
                }
            }
        }

        return wrongUpdates;
    }

    public static List<String> swap(List<String> list, int a, int b){
        List<String> newList = new ArrayList<>(list);
        String temp = list.get(a);
        newList.set(a, list.get(b));
        newList.set(b, temp);

        return newList;
    }

    public static int firstIndexNotIn(List<String> sublist, List<String> rules){
        //System.out.println(sublist + " | " + rules);
        for(int i = 0; i < sublist.size(); i++){
            if(!rules.contains(sublist.get(i))){
                return i;
            }
        }

        return -1;
    }

    public static int executePart1(List<String> input) {
        //System.out.println(input);
        Inputs newInput = new Inputs(input);
        int sum = 0;

        for (List<String> item : newInput.updates){
            for (int i = 0; i < item.size(); i++){
                List<String> subList = item.subList(i+1, item.size());
                List<String> rule = newInput.rules.get(item.get(i));
//                System.out.println(rule + " | " + item.get(i) + "  ---  " + subList);
                if(rule == null && i < item.size() - 1) {
                    break;
                } else if(rule == null){
//                    System.out.println("Accepted : " + item + " | rule : " + rule + " | sublist : " + subList );
                    sum += getMiddle(item);
                    continue;
                }

                if(!new HashSet<>(rule).containsAll(subList)){
//                    System.out.println("Declined : " + item + " | rule : " + rule + " | sublist : " + subList );
                    break;
                }

                if (i == item.size() - 1){
//                    System.out.println("Accepted : " + item + " | rule : " + rule + " | sublist : " + subList );
                    sum += getMiddle(item);
                }
            }
        }
        return sum;
    }

    public static int executePart2(List<String> input) {
        //System.out.println(input);
        int sum = 0;
        Inputs newInput = new Inputs(input);
        //System.out.println(newInput.rules);
        List<List<String>> wrongUpdates = getWrongUpdates(newInput.rules, newInput.updates);
        for (List<String> update : wrongUpdates){
            int i = 0;
            boolean recheckFlag = false;
            while(i < update.size()){

                List<String> rule = newInput.rules.get(update.get(i));
                List<String> subList = update.subList(i+1, update.size());

                if(rule == null && i != update.size()- 1){
                    update = swap(update, i,update.size()- 1 );
                    recheckFlag = true;
                    continue;
                } else if (rule == null) {
                    i ++;
                    continue;
                }

                if(firstIndexNotIn(subList, rule) == -1){
                    if(recheckFlag) recheckFlag = false;
                    i ++;
                    continue;
                } else {
                    update = swap(update, i, firstIndexNotIn(subList, rule) + i + 1);
                    recheckFlag = true;
                }
            }
            sum += getMiddle(update);
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> input = parseFile(INPUT);
        showResult(() -> executePart1(input));
    }
}
