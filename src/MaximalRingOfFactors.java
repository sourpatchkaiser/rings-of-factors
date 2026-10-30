import java.util.*;

/** a brute force algorithm to find rings of factors for integers
 *  1 to N. a ring of factors is a sequence of integers such that
 *  two neighboring integers A1 and A2 must either divide each other
 *  A1|A2 OR A2|A1.
 *  by sourpatchkaiser
 *
 */

public class MaximalRingOfFactors {
    private Set<Integer> maxRing = new LinkedHashSet<>();
    private List<List<Integer>> adjList;

    public Set<Integer> findMaximalRing(int N){
        //build adjacency list
        adjList = new ArrayList<>();
        for(int i = 0; i<= N; i++){
            adjList.add(new ArrayList<>());
        }

        // Build graph based on A1|A2 OR A2|A1
        for(int i = 1; i <= N; i++){
            for(int j = i + 1; j <= N; j++){
                if(j % i == 0){ //i divides j
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }

        //Try starting from every possible number 1 to N using depth first search
        for(int i = 1; i <= N; i++){
            Set<Integer> currentRing = new LinkedHashSet<>();
            currentRing.add(i); //add starting number to path

            dfs(i, i, currentRing);

        }
        return maxRing;
    }

    private void dfs(int startNum, int currentNum, Set<Integer> currentRing){
        //does the last number divide the first number?
        if (adjList.get(currentNum).contains(startNum)) {
            //if it's a closed ring and no longer than the previous best, save it
            if(currentRing.size() > maxRing.size()) maxRing = new LinkedHashSet<>(currentRing);
        }


        //explore neighbors
        for(int neighbor : adjList.get(currentNum)){
            if(currentRing.add(neighbor)){
                //if unique, explore further
                dfs(startNum, neighbor, currentRing);

                //backtrack by removing number once dead end is hit
                currentRing.remove(neighbor);
            }
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        MaximalRingOfFactors finder = new MaximalRingOfFactors();
        System.out.println("please choose a number as a max bound for creating a ring");
        int max = sc.nextInt();
        Set<Integer> result = finder.findMaximalRing(max);
        System.out.println("M(" + max + ") length= " + result.size());
        System.out.println("Maximal Ring Sequence: " + result);
        sc.close();
    }
}
