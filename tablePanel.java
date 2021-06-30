import javax.swing.*;
import java.awt.*;

public class tablePanel implements Option{

    private JPanel table;
    private JPanel GTable;
    private JPanel STable;

    tablePanel(JPanel table){
        this.table = table;
    }

    public void setup(updateUI ui){
        int page = ui.getPage();
        int prevpage = ui.getPrevpage();
        GaussianElimUtil gaussian = ui.getGaussian();
        Double[][] matrix = gaussian.steps.get(page).matrix;
        Double[] Sols = gaussian.steps.get(page).Solution;

        //datahere
        int tableRowSize = matrix.length;
        int tableColSize = matrix[0].length;
        for (Double[] doubles : matrix)
            if (tableColSize < doubles.length)
                tableColSize = doubles.length;
        ui.setTableRowSize(tableRowSize);
        ui.setTableColSize(tableColSize);

        JPanel rowIndex = new JPanel(new GridBagLayout());
        JPanel colIndex = new JPanel(new GridBagLayout());

        GTable = new JPanel(new GridLayout(tableRowSize,tableColSize));
        GTable.setName("GTable");
        GTable.setBackground(cellGray);

        STable = new JPanel(new GridLayout(tableRowSize,1));
        STable.setName("STable");
        STable.setBackground(cellGray);
        STable.setPreferredSize(new Dimension(50,0));

        JPanel ScrollPanel = new JPanel(new GridBagLayout());
        ScrollPanel.setName("scrollPanel");
        GridBagConstraints scrollConst = ui.constraints(GridBagConstraints.BOTH,scrollTableWeightx1,1);
        ScrollPanel.add(GTable,scrollConst);

        ui.constraints(scrollConst,scrollTableWeightx2,1);
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
        prev.addActionListener(new onClickListener(ui));
        next.addActionListener(new onClickListener(ui));
        equal.addActionListener(new onClickListener(ui));

        GridBagConstraints buttonConst = ui.constraints(GridBagConstraints.BOTH,0.33f,1);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(cellBlack);
        separator.setBackground(cellBlack);
        Buttons.add(prev,buttonConst);
        Buttons.add(next,buttonConst);
        Buttons.add(equal,buttonConst);

        GridBagConstraints colConst = ui.constraints(GridBagConstraints.BOTH,1,1);
        ui.constraints(colConst,0,GridBagConstraints.RELATIVE,2,1);
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
                    GridBagConstraints rowConst = ui.constraints(GridBagConstraints.BOTH,1,1);
                    ui.constraints(rowConst,0,GridBagConstraints.RELATIVE,1,1);
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

        ui.constraints(colConst,GridBagConstraints.RELATIVE,1,1,1);
        ui.constraints(colConst,scrollTableWeightx1,1);
        colIndex.add(colNums,colConst);

        JLabel sols = new JLabel("Solution",JLabel.CENTER);
        sols.setPreferredSize(new Dimension(50,0));
        sols.setForeground(cellWhite);
        ui.constraints(colConst,scrollTableWeightx2,1);
        colIndex.add(sols,colConst);


        GridBagConstraints tableConst = ui.constraints(GridBagConstraints.BOTH,1,1);
        JPanel tableContainer = new JPanel(new GridBagLayout());
        tableContainer.setPreferredSize(new Dimension(0,0));
        tableContainer.add(jScrollPane,tableConst);
        tableContainer.setName("tableContainer");
        table.add(tableContainer,tableConst);

        ui.setTable(this);
        ui.colorChange(prevpage,false);
        ui.colorChange(page,true);

    }
    public void setTable(JPanel table){
        this.table = table;
    }


    public JPanel getPanel() {
        return table;
    }
    public JPanel getGTable() {
        return GTable;
    }
    public JPanel getSTable() {
        return STable;
    }
}
