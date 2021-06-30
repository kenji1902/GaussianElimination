import javax.swing.*;
import java.awt.*;

public class inputPanel implements Option {
    private int tableRowSize;
    private int tableColSize;
    private GaussianElimUtil gaussian;
    private boolean acceptInput = false;
    private int page;
    private int prevpage;
    private JPanel table;
    private JPanel information;
    private JPanel inputPanel;
    private TextPlaceHolder input;
    private updateUI ui;

    inputPanel(JPanel inputPanel){
        this.inputPanel = inputPanel;
    }

    public void setup(updateUI Ui){
        this.ui = Ui;
        page = ui.getPage();
        prevpage = ui.getPrevpage();
        table = ui.getTable().getPanel();
        information = ui.getInformation().getPanel();

        inputPanel.setBackground(cellBlack);
        inputPanel.setPreferredSize(new Dimension(0,0));
        input = new TextPlaceHolder("[\t]|[a-zA-Z]|^ +| +$|( )+","Input separated by space and new line",2);
        input.setPlaceholder_color(new Color(145, 151, 161));
        JScrollPane jScrollPane = new JScrollPane(input);
        jScrollPane.setBackground(cellBlack);
        jScrollPane.getVerticalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25BD","\u25B3"));
        jScrollPane.getHorizontalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25B7","\u25C1"));
        jScrollPane.setBorder(BorderFactory.createLineBorder(cellBlack));

        CButton Calculate = new CButton("Calculate", SwingConstants.CENTER);
        Calculate.setBorder(BorderFactory.createLineBorder(cellBlack));

        GridBagConstraints c = ui.constraints(GridBagConstraints.BOTH,1,0.80f);
        ui.constraints(c,0,GridBagConstraints.RELATIVE,1,1);
        inputPanel.add(jScrollPane,c);
        ui.constraints(c,1,0.20f);
        inputPanel.add(Calculate,c);


        input.getDocument().addDocumentListener(new OnTextListener(input, e -> {
            acceptInput = input.getText().length() > 0;
        }));
        Calculate.setActionCommand(String.valueOf(buttonId.calculate));
        Calculate.addActionListener(e -> {
            if(acceptInput){
                try{
                    gaussian = new GaussianElimUtil(ui.getNums(input.getText()));
                    gaussian.gaussianElimination();
                    ui.setGaussian(gaussian);

                }catch (Exception err){
                    JOptionPane.showMessageDialog(null,"Please provide an Input\n"+err.getMessage() ,"Invalid Input",JOptionPane.WARNING_MESSAGE);
                }
                prevpage = page;
                page = 0;
                table.removeAll();
                information.removeAll();
                ui.getInformation().Setup(ui);
                ui.getTable().setup(ui);

                table.repaint();
                table.revalidate();
                ui.getFrame().repaint();
                ui.getFrame().revalidate();
            }
        });
    }

    public JPanel getPanel(){
        return inputPanel;
    }
    public TextPlaceHolder getInput(){
        return input;
    }
    public boolean isAcceptInput(){
        return acceptInput;
    }

}
