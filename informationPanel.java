import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class informationPanel implements Option{
    private JPanel information;
    private JScrollPane scrollPane;

    informationPanel(JPanel information){
        this.information = information;
    }

    protected void Setup(updateUI ui){
        JTextPane code = new JTextPane();
        JLabel Title = new JLabel("Code");
        Title.setOpaque(true);
        Title.setBackground(cellBlack);
        Title.setForeground(cellYellow);

        //Setup ScrollPane Options
        scrollPane = new JScrollPane(code,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0,0));
        scrollPane.setBackground(cellBlack);
        scrollPane.getVerticalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25BD","\u25B3"));
        scrollPane.getHorizontalScrollBar().setUI(new cJScrollPaneUI(cellYellow,cellBlack,cellYellow,"\u25B7","\u25C1"));
        scrollPane.setBorder(BorderFactory.createLineBorder(cellBlack));
        scrollPane.setColumnHeaderView(Title);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        //Set Default Code display in Information Panel
        code.setContentType("text/html");
        code.setBackground(cellGray);

        GridBagConstraints informationConst = ui.constraints(GridBagConstraints.BOTH,1,1);
        information.add(scrollPane,informationConst);
    }
    public JPanel getPanel(){
        return information;
    }
    public JScrollPane getScrollPane(){
        return scrollPane;
    }
}
