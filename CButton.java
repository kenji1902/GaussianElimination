import javax.swing.*;
import java.awt.*;

public class CButton extends JButton {
    private String text;
    private int alignment;
    CButton(String text){

    }
    CButton(String text,int alignment){
        super(text);
        super.setContentAreaFilled(false);
        this.text = text;
        this.alignment = alignment;
        setBackground(Color.decode("#2B2B2B"));
        setForeground(Color.decode("#FFC300"));
        setBorderPainted(false);

        setHorizontalAlignment(alignment);
        setFont(new Font("Verdana",Font.PLAIN,12));
        //setUI(new StyledButtonUI());
    }

    @Override
    protected void paintComponent(Graphics g) {
        ButtonModel model = getModel();
        if (model.isPressed()) {
            g.setColor(new Color(243,156,18));
        }
        else if (model.isRollover()) {
            g.setColor(new Color(55, 73, 79));
        }
        else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
