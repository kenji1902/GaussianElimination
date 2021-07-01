import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;


public class GaussianElimUtil {
    public class gaussianSteps{
        public Double [][] matrix;
        public Double [] Solution;
        public int i;
        public int j;
        public int k;
        public int index_max;
        public int value_max;
        public boolean forwardElim;
        public boolean backSub;
        public int part;
        gaussianSteps(Double[][]matrix,Double[]Solution,int i, int j, int k, int index_max, int value_max, boolean forwardElim, boolean backSub, int part){
            this.matrix = matrix;
            this.Solution = Solution;
            this.i = i;
            this.j = j;
            this.k = k;
            this.index_max = index_max;
            this.value_max = value_max;
            this.forwardElim = forwardElim;
            this.backSub = backSub;
            this.part = part;
        }
    }
    public LinkedList<gaussianSteps> steps;
    private int Unknowns;
    private double [][]mat;

    GaussianElimUtil(double[][] mat){
        this.mat = mat;
        this.Unknowns = mat.length;
        steps = new LinkedList<>();
    }
    // function to get matrix content
    public void gaussianElimination() throws Exception{
        int singular_flag = forwardElim(mat);
        if (singular_flag != -1) {
            String flag = "";
            if (mat[singular_flag][Unknowns] != 0)
                flag = "Matrix is Singular Inconsistent System";
            else
                flag = "Matrix is Singular May have infinite Solutions";
            throw new Exception(flag);
        }
        backSub(mat);
    }

    private int forwardElim(double mat[][]) {
        for (int k = 0; k < Unknowns; k++) {
            // Initialize maximum value and index for pivot
            int index_max = k;
            int value_max = (int)mat[index_max][k];

            // get the Highest Value in Row
            for (int i = k + 1; i < Unknowns; i++) {
                if (Math.abs(mat[i][k]) > value_max) {
                    value_max = (int) mat[i][k];
                    index_max = i;
                }
                steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new),
                        null,i,0,k,index_max,value_max,true,false,1));
            }

            //determine if the matrix is singular
            if (mat[k][index_max] == 0) {
                steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                        ,null,0,0,k,index_max,value_max,true,false,2));
                return k;
            }

            //swap the highest value in the row given that index max takes the index of highest value
            if (index_max != k) {
                swap_row(mat, k, index_max);
            }
            for (int i = k + 1; i < Unknowns; i++) {
                //Create a multiplier for equation
                //to make the lower triangle to be zero
                double Multiplier = mat[i][k] / mat[k][k];
                for (int j = k + 1; j <= Unknowns; j++) {
                    mat[i][j] -= mat[k][j] * Multiplier;
                    steps.  add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                            ,null,i,j,k,index_max,value_max,true,false,4));
                }
                //set the Current lower triangle to Zero
                mat[i][k] = 0;
                steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                        ,null,i,0,k,index_max,value_max,true,false,5));
            }
        }
        return -1;
    }
    // function to calculate the values of the unknowns
    private void backSub(double mat[][]) {
        double Solution[] = new double[Unknowns]; // An array to store solution

        for (int i = Unknowns - 1; i >= 0; i--) {
            /* start with the RHS of the equation */
            Solution[i] = mat[i][Unknowns];
            steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                    ,Arrays.stream( Solution ).boxed().toArray( Double[]::new ),i,0,0,0,0,false,true,1));
            for (int j = i + 1; j < Unknowns; j++) {
                Solution[i] -= mat[i][j] * Solution[j];
                steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                        ,Arrays.stream( Solution ).boxed().toArray( Double[]::new ),i,j,0,0,0,false,true,2));
            }

            Solution[i] = Solution[i] / mat[i][i];
            steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                    ,Arrays.stream( Solution ).boxed().toArray( Double[]::new ),i,0,0,0,0,false,true,3));
        }

    }
    private void swap_row(double mat[][], int i, int j) {
        for (int k = 0; k <= Unknowns; k++) {
            double temp = mat[i][k];
            mat[i][k] = mat[j][k];
            mat[j][k] = temp;
            steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                    ,null,i,j,k,0,0,true,false,3));
        }
    }
}
