import javax.swing.*;
import java.awt.*;

public class DynamicButton extends JButton {
    private int ID = 0;
    public DynamicButton(int ID){
        super.setContentAreaFilled(false);
        setBackground(Color.decode("#2B2B2B"));
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }
    @Override
    protected void paintComponent(Graphics g) {
        ButtonModel model = getModel();
        if (model.isPressed()) {
            g.setColor(Color.decode("#1c1b1b"));
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
