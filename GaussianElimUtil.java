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
    public void gaussianElimination() {
        /* reduction into r.e.f. */
        int singular_flag = forwardElim(mat);

        /* if matrix is singular */
        if (singular_flag != -1) {
            JOptionPane.showMessageDialog(null,"Please provide another Input\n"+"Matrix is Singular" ,"Solution",JOptionPane.WARNING_MESSAGE);

      /* if the RHS of equation corresponding to
               zero row  is 0, * system has infinitely
               many solutions, else inconsistent*/
            if (mat[singular_flag][Unknowns] != 0)
                JOptionPane.showMessageDialog(null,"Please provide another Input\n"+"Inconsistent System" ,"Solution",JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(null,"Please provide another Input\n"+"May have infinite Solutions" ,"Solution",JOptionPane.WARNING_MESSAGE);
            return;
        }
    /* get solution to system and print it using
           backward substitution */
        backSub(mat);
    }
    // function to reduce matrix to r.e.f.
    private int forwardElim(double mat[][]) {
        for (int k = 0; k < Unknowns; k++) {
            // Initialize maximum value and index for pivot
            int index_max = k;
            int value_max = (int)mat[index_max][k];

            /* find greater amplitude for pivot if any */
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
                /* factor to set current row kth element
                 * to 0, and subsequently remaining kth
                 * column to 0 */
                double factor = mat[i][k] / mat[k][k];
                for (int j = k + 1; j <= Unknowns; j++) {
                    mat[i][j] -= mat[k][j] * factor;
                    steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                            ,null,i,j,k,index_max,value_max,true,false,4));
                }
                /* filling lower triangular matrix with
                 * zeros*/
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

    /* Start calculating from last equation up to the
           first */
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

      /* divide the RHS by the coefficient of the
               unknown being calculated */
            Solution[i] = Solution[i] / mat[i][i];
            steps.add(new gaussianSteps(Arrays.stream(mat).map(row -> Arrays.stream(row).boxed().toArray(Double[]::new)).toArray(Double[][]::new)
                    ,Arrays.stream( Solution ).boxed().toArray( Double[]::new ),i,0,0,0,0,false,true,3));
        }

//        System.out.println();
//        System.out.println("Solution for the system:");
//        for (int i = 0; i < N; i++) {
//            System.out.format("%.6f", Solution[i]);
//            System.out.println();
//        }
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
