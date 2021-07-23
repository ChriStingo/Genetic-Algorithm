/**
 *
 * @author Christian Stingone
 */
public class DNA {
    private char[][] genes;
    private int[] fitness;
    private char[] father1;
    private char[] father2;
    public double mutation_value = 0.015;     //popolazione: 300, cicli: 5000, mutation: 0.015/0.025
    private int chars = 0;
    public boolean end = false;
    public javax.swing.JTextArea show;
    
    public DNA(int quantity, int length, double mut, javax.swing.JTextArea attempts){
        String alphabet = "abcdefghijklmnopqrstuvwxyz ";
        end = false;
        show = attempts;
        chars = length;
        genes = new char[quantity][length];
        fitness = new int[quantity];
        mutation_value = mut;
        for(int k = 0; k < genes.length; k++){
            for(int i = 0; i < genes[0].length; i++){
                genes[k][i] = alphabet.charAt( (int) (Math.random() * 27));
                System.out.print(genes[k][i]);
            }
            System.out.println();
            fitness[k] = 0;
        }
    }
    
    // Compute the fitness funcion and save the fitness value into the fitness array
    public void fitness_function(String solution){
        for(int i = 0; i < genes.length; i++)    fitness[i]=0;
        for(int k = 0; k < genes.length; k++){
            for(int i = 0; i < genes[0].length; i++){
                if(genes[k][i] == solution.charAt(i)){
                    fitness[k]++;
                    if(fitness[k] == genes[0].length){
                        end = true;   
                        for(int l = 0; l < genes[0].length; l++)
                            System.out.print(genes[k][l]);   
                        return;
                    }
                }
            }
        }
    }
    
    // Find the best element, with max fitness value
    public char [] best(){
        int max = 0;
        int index = 0;
        for(int i = 0; i < genes.length; i++){
            if(fitness[i] > max){    
                max = fitness[i];
                index = i;
            }
        }
        return genes[index];
    }
    
    // Probability selection based on fitness value, bigger fitness value = more probability to be chosen
    public void selection(){
        int ftot = 0, free = 0;
        for(int k = 0; k < genes.length; k++){
            ftot+= fitness[k];
        }
        int[]  matingpool = new int[ftot];
        for(int k = 0; k < genes.length; k++){
            for(int i = 0; i < fitness[k]; i++, free++){
                matingpool[free] = k;
            }
            
        }
        for(int i = 0; i < genes.length-1; i+=2){
            father1 = genes[matingpool[(int) (Math.random() * ftot)]];
            father2 = genes[matingpool[(int) (Math.random() * ftot)]];
            for(; father1 == father2;)
                father2 = genes[matingpool[(int) (Math.random() * ftot)]];
            inheritance(i);
        }
        
        for(int k = 0; k < genes.length; k++){
            String phrase = "";
            for(int i = 0; i < genes[0].length; i++){
                System.out.print(genes[k][i]);
                phrase += Character.toString(genes[k][i]);
            }
            show.append(phrase);
            show.append("\n");
            System.out.println(" - fitness: " + fitness[k] );
        }
        show.append("-------------------------");
        show.append("\n");

    }
    
    // Create 2 sons from the 2 father elements. Cross inheritance
    public void inheritance(int i){
        int random =  (int) (Math.random() * chars-1);
        for(int k = 0; k < genes[0].length-1; k++){
            if(k <= random){
                genes[i][k] = father1[k];
                genes[i+1][k] = father2[k];
            }else{    
                genes[i][k] = father2[k];
                genes[i+1][k] = father1[k];
            }
            
        }
        mutation(genes[i]);
        mutation(genes[i+1]);
    }
    
    // Compute random mutation inside new child
    public void mutation(char[] gene){
        if(mutation_value > Math.random()){
            String alphabet = "abcdefghijklmnopqrstuvwxyz ";
            gene[(int) (Math.random() * chars)] =  alphabet.charAt( (int) (Math.random() * 27));
        }
    }
        
        
        
}

