import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updateUI implements Option {



    private informationPanel information;
    private tablePanel table;
    private inputPanel input;
    private GaussianElimUtil gaussian;
    private JFrame mainframe;

    private boolean acceptinput;
    private Timer play;
    private int tableColSize;
    private int tableRowSize;
    private int page;
    private int prevpage;

    private int randomTaskCount;

    updateUI(){
        try {
            gaussian = new GaussianElimUtil(getNums("2 1 -1 2 5 \n" +
                    "4 5 -3 6 9 \n" +
                    "-2 5 -2 6 4\n" +
                    "4 11 -4 8 2"));
            gaussian.gaussianElimination();
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
        randomTaskCount = 0;
        page = 0;
        prevpage = 0;
        acceptinput = false;
    }

    public void colorChange(int page,boolean opaque){
        JScrollPane pane = information.getScrollPane();
        JTextPane code = (JTextPane) pane.getViewport().getView();

        int gaussianI = gaussian.steps.get(page).i;
        int gaussianJ = gaussian.steps.get(page).j;
        int gaussianK = gaussian.steps.get(page).k;
        int value_max = gaussian.steps.get(page).value_max;
        int index_max = gaussian.steps.get(page).index_max;

        boolean forwardElim = gaussian.steps.get(page).forwardElim;
        boolean backSub = gaussian.steps.get(page).backSub;
        Codes codes = new Codes();

        if(forwardElim){
            switch (gaussian.steps.get(page).part){
                case 1:
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellOrange,opaque);
                    code.setText(codes.part1);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(0);
                    break;
                case 2:
                    refactorCell(tableColSize * gaussianK + index_max,page,cellOrange,opaque);
                    code.setText(codes.part2);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(0);
                    break;
                case 3:
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellOrange,opaque);
                    refactorCell(tableColSize * gaussianJ + gaussianK,page,cellRed,opaque);
                    code.setText(codes.part3);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(8);
                    break;
                case 4:
                    refactorCell(tableColSize * gaussianI + gaussianJ,page,cellOrange,opaque);
                    refactorCell(tableColSize * gaussianK + gaussianJ,page,cellRed,opaque);
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellBlue,opaque);
                    refactorCell(tableColSize * gaussianK + gaussianK,page,cellYellow,opaque);
                    code.setText(codes.part4);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(66);
                    break;
                case 5:
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellBlue,opaque);
                    code.setText(codes.part5);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(195);
                    break;
            }
        }
        else if(backSub){
            switch (gaussian.steps.get(page).part){
                case 1:
                    refactorCell(tableColSize * gaussianI + tableRowSize,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    code.setText(codes.part6);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(8);
                    break;
                case 2:
                    refactorCell(tableColSize * gaussianI + gaussianJ,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    refactorCellSols(gaussianJ,page,cellYellow,opaque);
                    code.setText(codes.part7);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(57);
                    break;
                case 3:
                    refactorCell(tableColSize * gaussianI + gaussianI,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    code.setText(codes.part8);
                    if(opaque)
                        pane.getVerticalScrollBar().setValue(86);
                    break;
            }
        }
    }
    public void refactorCell(int index, int page, Color color, boolean opaque ){

        int row = (int) index / tableColSize;
        int col = index % tableColSize;

        JPanel GTable = this.table.getGTable();
        JLabel Gcell = (JLabel) GTable.getComponent(index);
        Gcell.setOpaque(opaque);
        Gcell.setText(String.format("%.3f",gaussian.steps.get(page).matrix[row][col]));

        if(opaque) {
            if(color == cellYellow)
                Gcell.setForeground(cellGray);
            Gcell.setBackground(color);
        }
        else {
            if(page !=0 && this.page < prevpage)
                Gcell.setText(String.format("%.3f",gaussian.steps.get(page-1).matrix[row][col]));
            Gcell.setBackground(cellGray);
            Gcell.setForeground(cellWhite);
        }

    }
    public void refactorCellSols(int index, int page, Color color, boolean opaque){

        JPanel STable = this.table.getSTable();
        JLabel Scell = (JLabel) STable.getComponent(index);
        Scell.setOpaque(opaque);
        Scell.setText(String.format("%.3f", gaussian.steps.get(page).Solution[index]));

        if(opaque) {
            if(color == cellYellow)
                Scell.setForeground(cellGray);
            Scell.setBackground(color);
        }
        else {
            if(page !=0 && this.page < prevpage)
                if(gaussian.steps.get(page-1).Solution != null)
                    Scell.setText(String.format("%.3f",gaussian.steps.get(page-1).Solution[index]));
                else
                    Scell.setText(String.format("%.3f",0f));
            Scell.setBackground(cellGray);
            Scell.setForeground(cellWhite);
        }

    }
    public double[][] getNums(String array) throws Exception{
        String[] Line = array.split("\n");
        String[][] matrix = Arrays.stream(Line).map(str -> str.split(" ")).toArray(String[][]::new);
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        for(String[] str : matrix)
            if(colSize < str.length)
                colSize = str.length;

        if(rowSize < 2)
            throw new Exception("Row must be greater than 2");
        if(colSize <= 3)
            throw new Exception("Col must be greater than 3");

        double[][] container = new double[rowSize][colSize];
        for(int i = 0; i < matrix.length;i++)
            for (int j = 0; j < matrix[i].length; j++)
                container[i][j] = Double.parseDouble(matrix[i][j]);

        return container;

    }

    public double[][] getRand(String array) throws Exception{
        String[] line = array.split(" ");
        Pattern pattern = Pattern.compile("\n");
        Matcher matcher = pattern.matcher(array);
        if(matcher.find()){
            throw new Exception("Input Format (Random):\nRow Min Max\nMin = Minimum Value of Random\nMax = Maximum Value of Random");
        }
        if(line.length != 3)
            throw new Exception("Input Format (Random):\nRow Min Max\nMin = Minimum Value of Random\nMax = Maximum Value of Random");


        int Row = Integer.parseInt(line[0]);
        int Col = Row + 1;
        float min = Integer.parseInt(line[1]);
        float max = Integer.parseInt(line[2]);

        if(Row < 2 || Row > 40)
            throw new Exception("Row must be greater than 1 and Less than or equal to 40");
        if(min >= max)
            throw new Exception("min must be less than max");

        double[][] random = new double[Row][Col];
        for(int i = 0; i < Row; i++){
            for(int j = 0; j < Col; j++){
                float Val = (float) Math.floor(Math.random() * (max - min + 1) + min);
                random[i][j] = Val;
            }
        }
        for(double[] y : random){
            for(double x : y){
                System.out.print(x+" ");
            }
            System.out.println();
        }
        return random;
    }

    //Gridbag constraints Utitlity
    public GridBagConstraints constraints(int fill, float weightx, float weighty){
        GridBagConstraints consts = new GridBagConstraints();
        consts.fill = fill;
        consts.weightx = weightx;
        consts.weighty = weighty;
        return consts;
    }

    public void constraints(GridBagConstraints consts, float weightx, float weighty){
        consts.weightx = weightx;
        consts.weighty = weighty;
    }

    public void constraints(GridBagConstraints consts, int gridx, int gridy, int gridwidth, int gridheight){
        consts.gridx = gridx;
        consts.gridy = gridy;
        consts.gridwidth = gridwidth;
        consts.gridheight = gridheight;
    };

    public Component getComponent(JComponent PanelName,  String toFind){

        Component data = null;
        try{
            Component [] components = PanelName.getComponents();
            System.out.printf("Items inside %s: %n{%n",PanelName.getName());
            for (Component component : components) {
                System.out.printf("\t%s%n",component.getName());
                if (component.getName() == toFind) {
                    data = component;
                }
            }
            System.out.println("}");
        }catch (Exception err){
            System.out.println(err);
        }
        return data;
    }

    //Setter
    public void setInformation(informationPanel information) {
        this.information = information;
    }

    public void setGaussian(GaussianElimUtil gaussian) {
        this.gaussian = gaussian;
    }

    public void setTable(tablePanel table) {
        this.table = table;
    }

    public void setTableColSize(int tableColSize) {
        this.tableColSize = tableColSize;
    }

    public void setTableRowSize(int tableRowSize) {
        this.tableRowSize = tableRowSize;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPrevpage(int prevpage) {
        this.prevpage = prevpage;
    }

    public void setFrame(JFrame frame) {this.mainframe = frame;}

    public void setInput(inputPanel inputPanel){this.input = inputPanel;}

    public void setRandomTaskCount(int value) {this.randomTaskCount = value;}

    public void setPlay(TimerTask task) {
        if(play != null)
            play.cancel();

        play = new Timer();
        play.schedule(task, 0, 100);
    }
    //Getter
    public void CancelPlay() {
        if(play != null)
            play.cancel();
    }

    public informationPanel getInformation() {
        return information;
    }

    public GaussianElimUtil getGaussian() {
        return gaussian;
    }

    public tablePanel getTable() {
        return table;
    }

    public inputPanel getInputPanel() {return input;};

    public int getPage() {
        return page;
    }

    public int getPrevpage() {
        return prevpage;
    }

    public JFrame getFrame() {return mainframe;}

    public int getRandomTaskCount() {
        return randomTaskCount;
    }

}
