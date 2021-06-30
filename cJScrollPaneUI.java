import javax.swing.*;
import javax.swing.plaf.ScrollPaneUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class cJScrollPaneUI extends BasicScrollBarUI {

    private Color FontColor;
    private String increaseButton;
    private String decreaseButton;
    private Color thumb;
    private Color track;
    cJScrollPaneUI(Color thumbColor, Color track, Color FontColor, String increaseButton, String decreaseButton){
        super();
        this.thumb = thumbColor;
        this.track = track;
        this.FontColor = FontColor;
        this.increaseButton = increaseButton;
        this.decreaseButton = decreaseButton;
    }

    @Override
    protected void configureScrollBarColors() {
        super.configureScrollBarColors();
        this.thumbColor = thumb;
        this.trackColor = track;
        this.thumbHighlightColor = thumb;
        this.thumbDarkShadowColor = thumb;
        this.thumbLightShadowColor = thumb;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        CButton button = new CButton(decreaseButton, SwingConstants.CENTER) {
            String dir;
            @Override
            public void setText(String text) {
                super.setText("");
                dir = text;
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);;
                Graphics2D G = (Graphics2D) g.create();
                G.setPaint(FontColor);

                G.drawString(dir, ((getWidth() - g.getFontMetrics().stringWidth(dir)) / 2), ((g.getFontMetrics().getMaxAscent() + getHeight()) / 2) - 1);
            }
        };
        button.setPreferredSize(new Dimension(20, 20));
        button.setFont(new Font("Arial Unicode MS",Font.PLAIN,10));
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        CButton button = new CButton(increaseButton, SwingConstants.CENTER) {
            String dir;
            @Override
            public void setText(String text) {
                super.setText("");
                dir = text;
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);;
                Graphics2D G = (Graphics2D) g.create();
                G.setPaint(FontColor);

                G.drawString(dir, ((getWidth() - g.getFontMetrics().stringWidth(dir)) / 2), ((g.getFontMetrics().getMaxAscent() + getHeight()) / 2) - 1);
            }
        };
        button.setPreferredSize(new Dimension(20, 20));
        button.setFont(new Font("Arial Unicode MS",Font.PLAIN,10));
        return button;
    }

}
