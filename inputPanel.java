import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

    inputPanel(JPanel inputPanel){
        this.inputPanel = inputPanel;
    }

    public void setup(updateUI ui){
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
        Image img = null;
        try {
            img = ImageIO.read(new File(System.getProperty("user.dir")+"\\resource\\random.png")).getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image finalImg = img;
        CButton Random = new CButton("",SwingConstants.CENTER){
            @Override
            protected void paintComponent(Graphics g2) {
                super.paintComponent(g2);
                Graphics2D g = (Graphics2D) g2;
                g.setPaint(cellYellow);
                try{
                    g2.drawImage(finalImg,(this.getWidth() - finalImg.getWidth(null))/2,(this.getHeight() - finalImg.getHeight(null))/2,this);
                }
                catch (Exception e) {
                    g.drawString("R", ((getWidth() - g.getFontMetrics().stringWidth("A")) / 2), ((g.getFontMetrics().getMaxAscent() + getHeight()) / 2) - 1);
                }
            }
        };
        Random.setBorder(BorderFactory.createLineBorder(cellBlack));
        GridBagConstraints c = ui.constraints(GridBagConstraints.BOTH,1,0.80f);
        ui.constraints(c,0,GridBagConstraints.RELATIVE,2,1);
        inputPanel.add(jScrollPane,c);

        ui.constraints(c,0.80f,0.20f);
        ui.constraints(c,0,1,1,1);
        inputPanel.add(Calculate,c);

        ui.constraints(c,1,1,1,1);
        ui.constraints(c,0.20f,0.20f);
        inputPanel.add(Random,c);

        input.getDocument().addDocumentListener(new OnTextListener(input, e -> {
            acceptInput = input.getText().length() > 0;
        }));

        Calculate.setActionCommand(String.valueOf(buttonId.calculate));
        Calculate.addActionListener(new onClickListener(ui));
        Random.setActionCommand(String.valueOf(buttonId.random));
        Random.addActionListener(new onClickListener(ui));
        ui.setInput(this);

    }

    public void setAcceptInput(boolean acceptInput){
        this.acceptInput = acceptInput;
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
