public class Solution {

    public int fitness;
    public StringBuilder chromosome;

    public Solution(int n, int m){
        chromosome = new StringBuilder(n*m);

        for(int i=0; i < n*m; i++){
            chromosome.insert(i, Math.random() < 0.5 ? '0' : '1');
        }

        calcFitness();
    }

    public void calcFitness(){
        throw new Error("Not implemented"); // TODO
    }



}
