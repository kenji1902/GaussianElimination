import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class GaussianElim {

    //Window Option
    private String title;
    private int width, height;
    private float informationWeightx;
    private float informationWeighty;
    private float tableWeightx;
    private float tableWeighty;
    private float scrollTableWeightx1;
    private float scrollTableWeightx2;
    private float codeWeightx;
    private float codeWeighty;

    //Window Layout Container
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel table;
    private JPanel input;
    private JPanel information;

    //Gaussian Data
    private int tableRowSize;
    private int tableColSize;
    private int page = 0;
    private int prevpage = 0;
    private GaussianElimUtil gaussian = null;

    //Button ID
    boolean acceptInput = false;
    private enum buttonId{
        next,
        prev,
        equal,
        calculate,
        dynButton
    }

    //Colors
    private Color cellRed = new Color(255, 0, 47);
    private Color cellPurple = new Color(152, 118, 170);
    private Color cellYellow = new Color(255, 195, 0);
    private Color cellOrange = new Color(251, 128, 14);
    private Color cellGreen = new Color(113, 148, 93);
    private Color cellWhite = new Color(207, 207, 207);
    private Color cellGray = new Color(43, 43, 43);
    private Color cellBlack = new Color(28, 27, 27);
    private Color cellBlue =new Color(64, 67, 245);

    GaussianElim(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        //Initialize Value

        try {
            gaussian = new GaussianElimUtil(getNums("2 1 -1 2 5 \n" +
                    "4 5 -3 6 9 \n" +
                    "-2 5 -2 6 4\n" +
                    "4 11 -4 8 2"));
        }catch (Exception err){
            System.out.println(err);
        }
        gaussian.gaussianElimination();
        //Set Frame Panels Size here
        informationWeightx = 0.7f;
        informationWeighty = 0.3f;
        tableWeightx = 1;
        tableWeighty = 0.7f;
        scrollTableWeightx1 = 0.80f;
        scrollTableWeightx2 = 0.20f;
        codeWeightx = 0.3f;
        codeWeighty = 0.3f;

        setup();
    }
    private void setup(){
        //Setup MainFrame
        mainFrame = new JFrame(title);
        mainFrame.setSize(width,height);
        mainFrame.setLayout(new GridLayout());
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Initialize Panels
        mainPanel = new JPanel(new GridBagLayout());
        table = new JPanel(new GridBagLayout());
        input = new JPanel(new GridBagLayout());
        information = new JPanel(new GridBagLayout());

        //Setup Panels to mainFrame
        GridBagConstraints frameConst = constraints(GridBagConstraints.BOTH,0.7f,0.3f);

        //Information Panel
        information.setBackground(Color.BLUE);
        constraints(frameConst,informationWeightx,informationWeighty);
        mainPanel.add(information,frameConst);
        informationPanel();

        //Table Panel
        table.setBackground(Color.cyan);
        constraints(frameConst,0,1,3,1);
        constraints(frameConst,tableWeightx,tableWeighty);
        mainPanel.add(table,frameConst);
        tablePanel(gaussian.steps.get(page).matrix,gaussian.steps.get(page).Solution);

        //Code Panel
        input.setBackground(Color.YELLOW);
        constraints(frameConst,2,0,1,1);
        constraints(frameConst,codeWeightx,codeWeighty);
        mainPanel.add(input,frameConst);
        inputPanel();
        //input();
        //Show mainFrame
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    private void informationPanel(){
        JTextPane code = new JTextPane();

        JScrollPane scrollPane = new JScrollPane(code);
        scrollPane.setName("codes");
        scrollPane.setPreferredSize(new Dimension(0,0));
        scrollPane.setBackground(cellBlack);
        scrollPane.getVerticalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25BD","\u25B3"));
        scrollPane.getHorizontalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25B7","\u25C1"));
        scrollPane.setBorder(BorderFactory.createLineBorder(cellBlack));

        Codes codes = new Codes();
        code.setContentType("text/html");
        code.setText(codes.part1);
        code.setBackground(cellGray);
        GridBagConstraints informationConst = constraints(GridBagConstraints.BOTH,1,1);
        information.add(scrollPane,informationConst);
    }
    private void tablePanel(Double[][] matrix, Double[] Sols){

        //datahere
        tableRowSize = matrix.length;
        tableColSize = matrix[0].length;
        for (Double[] doubles : matrix)
            if (tableColSize < doubles.length)
                tableColSize = doubles.length;
        JPanel rowIndex = new JPanel(new GridBagLayout());
        JPanel colIndex = new JPanel(new GridBagLayout());

        JPanel GTable = new JPanel(new GridLayout(tableRowSize,tableColSize));
        GTable.setName("GTable");
        GTable.setBackground(cellGray);

        JPanel STable = new JPanel(new GridLayout(tableRowSize,1));
        STable.setName("STable");
        STable.setBackground(cellGray);
        STable.setPreferredSize(new Dimension(50,0));

        JPanel ScrollPanel = new JPanel(new GridBagLayout());
        ScrollPanel.setName("scrollPanel");
        GridBagConstraints scrollConst = constraints(GridBagConstraints.BOTH,scrollTableWeightx1,1);
        ScrollPanel.add(GTable,scrollConst);

        constraints(scrollConst,scrollTableWeightx2,1);
        ScrollPanel.add(STable,scrollConst);

        JScrollPane jScrollPane = new JScrollPane(ScrollPanel);
        jScrollPane.setName("scrollArea");
        jScrollPane.setRowHeaderView(rowIndex);
        jScrollPane.setColumnHeaderView(colIndex);
        jScrollPane.setBackground(cellBlack);
        jScrollPane.getVerticalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25BD","\u25B3"));
        jScrollPane.getHorizontalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25B7","\u25C1"));
        jScrollPane.setBorder(BorderFactory.createLineBorder(cellBlack));

        jScrollPane.getColumnHeader().setBackground(cellBlack);
        jScrollPane.getRowHeader().setBackground(cellBlack);
        rowIndex.setBackground(cellBlack);
        colIndex.setBackground(cellBlack);
        rowIndex.setForeground(cellWhite);
        colIndex.setForeground(cellWhite);


        //Buttons
        JPanel Buttons = new JPanel(new GridBagLayout());
        Buttons.setBackground(cellBlack);
        CButton prev = new CButton("<<",SwingConstants.CENTER);
        CButton next = new CButton(">>",SwingConstants.CENTER);
        CButton equal = new CButton("=",SwingConstants.CENTER);

        //Button Option
        Dimension buttonSize = new Dimension(80,20);
        prev.setMinimumSize(buttonSize);
        next.setMinimumSize(buttonSize);
        equal.setMinimumSize(buttonSize);

        //Setup Button
        prev.setActionCommand(String.valueOf(buttonId.prev));
        next.setActionCommand(String.valueOf(buttonId.next));
        equal.setActionCommand(String.valueOf(buttonId.equal));
        prev.addActionListener(new onClickLister());
        next.addActionListener(new onClickLister());
        equal.addActionListener(new onClickLister());

        GridBagConstraints buttonConst = constraints(GridBagConstraints.BOTH,0.33f,1);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(cellBlack);
        separator.setBackground(cellBlack);
        Buttons.add(prev,buttonConst);
        Buttons.add(next,buttonConst);
        Buttons.add(equal,buttonConst);

        GridBagConstraints colConst = constraints(GridBagConstraints.BOTH,1,1);
        constraints(colConst,0,GridBagConstraints.RELATIVE,2,1);
        colIndex.add(Buttons,colConst);

        JPanel colNums = new JPanel(new GridLayout(1,tableColSize));
        colNums.setBackground(cellBlack);


        Dimension cellSize = new Dimension(50,50);
        Dimension rowSize = new Dimension(40,50);
        Dimension colSize = new Dimension(50,20);

        for(int i = 0; i < tableRowSize; i++){
            for(int j = 0; j < tableColSize; j++){
                //Value of Table
                JLabel data = new JLabel(String.format("%.3f",matrix[i][j]));
                data.setHorizontalAlignment(SwingConstants.CENTER);
                data.setForeground(cellWhite);

                //SetUp Row Label
                if(j == 0){
                    GridBagConstraints rowConst = constraints(GridBagConstraints.BOTH,1,1);
                    constraints(rowConst,0,GridBagConstraints.RELATIVE,1,1);
                    JLabel rowLabel = new JLabel(String.valueOf(i ), JLabel.CENTER);
                    rowLabel.setPreferredSize(rowSize);
                    rowLabel.setForeground(cellWhite);
                    rowIndex.add(rowLabel, rowConst);
                }
                if(i == 0){
                    JLabel colLabel = new JLabel(String.valueOf(j),JLabel.CENTER);
                    colLabel.setPreferredSize(colSize);
                    colLabel.setForeground(cellWhite);
                    colNums.add(colLabel);

                }
                data.setBorder(BorderFactory.createLineBorder(cellBlack));
                data.setPreferredSize(cellSize);
                GTable.add(data);
            }
        }
        for(int j = 0; j < tableRowSize; j++){
            JLabel solLabel;
            if(Sols != null)
                solLabel = new JLabel(String.format("%.3f",Sols[j]), JLabel.CENTER);
            else
                solLabel = new JLabel(String.format("%.3f",0f), JLabel.CENTER);
            solLabel.setPreferredSize(new Dimension(0,0));
            solLabel.setForeground(cellWhite);
            solLabel.setBorder(BorderFactory.createLineBorder(cellBlack));
            STable.add(solLabel);
        }

        constraints(colConst,GridBagConstraints.RELATIVE,1,1,1);
        constraints(colConst,scrollTableWeightx1,1);
        colIndex.add(colNums,colConst);

        JLabel sols = new JLabel("Solution",JLabel.CENTER);
        sols.setPreferredSize(new Dimension(50,0));
        sols.setForeground(cellWhite);
        constraints(colConst,scrollTableWeightx2,1);
        colIndex.add(sols,colConst);


        GridBagConstraints tableConst = constraints(GridBagConstraints.BOTH,1,1);
        JPanel tableContainer = new JPanel(new GridBagLayout());
        tableContainer.setPreferredSize(new Dimension(0,0));
        tableContainer.add(jScrollPane,tableConst);
        tableContainer.setName("tableContainer");
        table.add(tableContainer,tableConst);
        colorChange(prevpage,false);
        colorChange(page,true);
    }
    private void colorChange(int page,boolean opaque){
        JScrollPane pane = (JScrollPane) getComponent(information,"codes");
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
                    break;
                case 2:
                    refactorCell(tableColSize * gaussianK + index_max,page,cellOrange,opaque);
                    code.setText(codes.part2);
                    break;
                case 3:
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellOrange,opaque);
                    refactorCell(tableColSize * gaussianJ + gaussianK,page,cellRed,opaque);
                    code.setText(codes.part3);
                    break;
                case 4:
                    refactorCell(tableColSize * gaussianI + gaussianJ,page,cellOrange,opaque);
                    refactorCell(tableColSize * gaussianK + gaussianJ,page,cellRed,opaque);
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellBlue,opaque);
                    refactorCell(tableColSize * gaussianK + gaussianK,page,cellYellow,opaque);
                    code.setText(codes.part4);
                    break;
                case 5:
                    refactorCell(tableColSize * gaussianI + gaussianK,page,cellBlue,opaque);
                    code.setText(codes.part5);
                    break;
            }
        }
        else if(backSub){
            switch (gaussian.steps.get(page).part){
                case 1:
                    refactorCell(tableColSize * gaussianI + tableRowSize,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    code.setText(codes.part6);
                    break;
                case 2:
                    refactorCell(tableColSize * gaussianI + gaussianJ,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    refactorCellSols(gaussianJ,page,cellYellow,opaque);
                    code.setText(codes.part7);
                    break;
                case 3:
                    refactorCell(tableColSize * gaussianI + gaussianI,page,cellOrange,opaque);
                    refactorCellSols(gaussianI,page,cellRed,opaque);
                    code.setText(codes.part8);
                    break;
            }
        }
    }
    private void inputPanel(){

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(cellBlack);
        inputPanel.setPreferredSize(new Dimension(0,0));
        TextPlaceHolder input = new TextPlaceHolder("[\t]|[a-zA-Z]|^ +| +$|( )+","Input separated by space and new line",2);
        input.setPlaceholder_color(new Color(145, 151, 161));
        JScrollPane jScrollPane = new JScrollPane(input);
        jScrollPane.setBackground(cellBlack);
        jScrollPane.getVerticalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25BD","\u25B3"));
        jScrollPane.getHorizontalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25B7","\u25C1"));
        jScrollPane.setBorder(BorderFactory.createLineBorder(cellBlack));

        CButton Calculate = new CButton("Calculate", SwingConstants.CENTER);
        Calculate.setBorder(BorderFactory.createLineBorder(cellBlack));

        GridBagConstraints c = constraints(GridBagConstraints.BOTH,1,0.80f);
        constraints(c,0,GridBagConstraints.RELATIVE,1,1);
        inputPanel.add(jScrollPane,c);
        constraints(c,1,0.20f);
        inputPanel.add(Calculate,c);


        GridBagConstraints inputConst = constraints(GridBagConstraints.BOTH,1,1);
        input.getDocument().addDocumentListener(new OnTextListener(input, e -> {
            if(input.getText().length() > 0)
                acceptInput = true;
            else acceptInput = false;
        }));
        Calculate.setActionCommand(String.valueOf(buttonId.calculate));
        Calculate.addActionListener(e -> {
            if(acceptInput){

                try{
                    gaussian = new GaussianElimUtil(getNums(input.getText()));
                    gaussian.gaussianElimination();


                }catch (Exception err){
                    JOptionPane.showMessageDialog(null,"Please provide an Input\n"+err.getStackTrace() ,"Invalid Input",JOptionPane.WARNING_MESSAGE);
                }
                prevpage = page;
                page = 0;
                table.removeAll();
                information.removeAll();
                informationPanel();
                tablePanel(gaussian.steps.get(0).matrix,gaussian.steps.get(0).Solution);

                mainFrame.repaint();
                mainFrame.revalidate();
            }
        });
        Calculate.addActionListener(new onClickLister());

        this.input.add(inputPanel,inputConst);
    }
    private double[][] getNums(String array) throws Exception{
        String[] Line = array.split("\n");
        String[][] matrix = Arrays.stream(Line).map(str -> str.split(" ")).toArray(String[][]::new);
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        for(String[] str : matrix)
            if(colSize < str.length)
                colSize = str.length;

        if(colSize < 3)
            colSize = 3;

        double[][] container = new double[rowSize][colSize];
        for(int i = 0; i < matrix.length;i++)
            for (int j = 0; j < matrix[i].length; j++)
                container[i][j] = Double.parseDouble(matrix[i][j]);

        return container;

    }
    private String textColor(String color, String text){
        return String.format("<font color = %s>%s</font>",color,text);
    }
    private void refactorCell(int index, int page, Color color, boolean opaque ){
        int row = (int) index / tableColSize;
        int col = index % tableColSize;

        JPanel tableContainer = (JPanel) getComponent(table,"tableContainer");
        JScrollPane scrollPanel = (JScrollPane) getComponent(tableContainer,"scrollArea");
        JPanel GTable = (JPanel) getComponent((JPanel) scrollPanel.getViewport().getView(),"GTable");
        JLabel Gcell = (JLabel) GTable.getComponent(index);
        Gcell.setOpaque(opaque);
        Gcell.setText(String.format("%.3f",gaussian.steps.get(page).matrix[row][col]));

        if(opaque) {
            if(color == cellYellow)
                Gcell.setForeground(cellGray);
            Gcell.setBackground(color);
        }
        else {
            System.out.println(page);
            if(page !=0 && this.page < prevpage)
                Gcell.setText(String.format("%.3f",gaussian.steps.get(page-1).matrix[row][col]));
            Gcell.setBackground(cellGray);
            Gcell.setForeground(cellWhite);
        }

    }
    private void refactorCellSols(int index, int page, Color color, boolean opaque){
        JPanel tableContainer = (JPanel) getComponent(table,"tableContainer");
        JScrollPane scrollPanel = (JScrollPane) getComponent(tableContainer,"scrollArea");
        JPanel STable = (JPanel) getComponent((JPanel) scrollPanel.getViewport().getView(),"STable");
        JLabel Scell = (JLabel) STable.getComponent(index);
        Scell.setOpaque(opaque);
        Scell.setText(String.format("%.3f", gaussian.steps.get(page).Solution[index]));

        if(opaque) {
            if(color == cellYellow)
                Scell.setForeground(cellGray);
            Scell.setBackground(color);
        }
        else {
            System.out.println(page);
            if(page !=0 && this.page < prevpage)
                if(gaussian.steps.get(page-1).Solution != null)
                    Scell.setText(String.format("%.3f",gaussian.steps.get(page-1).Solution[index]));
                else
                    Scell.setText(String.format("%.3f",0f));
            Scell.setBackground(cellGray);
            Scell.setForeground(cellWhite);
        }

    }
    private class onClickLister implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String sButtonId = e.getActionCommand();
            switch (sButtonId){
                case "prev":
                    if(page > 0) {
                        prevpage = page;
                        page--;
                        colorChange(prevpage,false);
                        colorChange(page,true);
                    }
                    break;
                case "next":
                    if(page < gaussian.steps.size()-1) {
                        prevpage = page;
                        page++;
                        colorChange(prevpage,false);
                        colorChange(page,true);
                    }
                    break;
                case "equal":
                    prevpage = page;
                    page = gaussian.steps.size()-1;
                    table.removeAll();
                    tablePanel(gaussian.steps.get(gaussian.steps.size()-1).matrix,gaussian.steps.get(gaussian.steps.size()-1).Solution);
                    table.repaint();
                    table.revalidate();
                    break;
            }
            System.out.println("part: "+gaussian.steps.get(page).part);
            System.out.println("Prev Page >> "+prevpage);
            System.out.println("Page >> "+page);
        }
    }
    private class OnTextListener implements DocumentListener {
        JTextComponent textComponent;
        ChangeListener changeListener;
        OnTextListener(JTextComponent textComponent, ChangeListener changeListener){
            this.textComponent = textComponent;
            this.changeListener = changeListener;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            action(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            action(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            action(e);
        }

        public void action(DocumentEvent e){
            changeListener.stateChanged(new ChangeEvent(textComponent));
        }

    }

        //Gridbag constraints Utitlity
    private GridBagConstraints constraints(int fill, float weightx, float weighty){
        GridBagConstraints consts = new GridBagConstraints();
        consts.fill = fill;
        consts.weightx = weightx;
        consts.weighty = weighty;
        return consts;
    }
    private void constraints(GridBagConstraints consts, float weightx, float weighty){
        consts.weightx = weightx;
        consts.weighty = weighty;
    }
    private void constraints(GridBagConstraints consts, int gridx, int gridy, int gridwidth, int gridheight){
        consts.gridx = gridx;
        consts.gridy = gridy;
        consts.gridwidth = gridwidth;
        consts.gridheight = gridheight;
    };
    private Component getComponent(JComponent PanelName,  String toFind){

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

}
