import javax.accessibility.AccessibleContext;
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

public class GaussianElim implements Option{

    //Window Option
    private String title;
    private int width, height;

    //Window Layout Container
    private tablePanel table;
    private inputPanel input;
    private informationPanel information;

    //Button ID
    boolean acceptInput = false;
    updateUI ui;


    GaussianElim(){
        this.title = "Gaussian Elimination Calculator";
        this.width = 900;
        this.height = 600;
        //Initialize Value
        ui = new updateUI();
        table = new tablePanel(new JPanel(new GridBagLayout()));
        input = new inputPanel(new JPanel(new GridBagLayout()));
        information = new informationPanel(new JPanel(new GridBagLayout()));
        setup();
    }
    private void setup(){
        //Setup MainFrame
        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(width,height);
        mainFrame.setLayout(new GridLayout());
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Initialize Panel for mainFrame
        JPanel mainPanel = new JPanel(new GridBagLayout());

        //Setup Panels to mainFrame
        GridBagConstraints frameConst = ui.constraints(GridBagConstraints.BOTH,0.7f,0.3f);
        //Information Panel
        ui.constraints(frameConst,informationWeightx,informationWeighty);
        information.Setup(ui);
        ui.setInformation(information);
        mainPanel.add(information.getPanel(),frameConst);

        //Table Panel
        ui.constraints(frameConst,0,1,3,1);
        ui.constraints(frameConst,tableWeightx,tableWeighty);
        table.setup(ui);
        ui.setTable(table);
        mainPanel.add(table.getPanel(),frameConst);

        //Code Panel
        ui.constraints(frameConst,2,0,1,1);
        ui.constraints(frameConst,codeWeightx,codeWeighty);
        input.setup(ui);
        ui.setInput(input);
        mainPanel.add(input.getPanel(),frameConst);

        //Show mainFrame
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        ui.setFrame(mainFrame);
    }
}
