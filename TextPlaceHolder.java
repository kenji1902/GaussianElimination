import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.awt.print.PrinterException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class TextPlaceHolder extends JTextArea {


    String Regex;
    int Alignment;
    private String placeholder;
    Color placeholder_color;

    public TextPlaceHolder( String regex, final String pText, int Alignment) {
        super();
        this.Alignment = Alignment;
        setFont(new Font("Verdana",Font.PLAIN,12));
        setBorder(new LineBorder(new Color(243,156,18)));
        setPlaceholder(pText);
        Regex = regex;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = getText();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(text);
                while(matcher.find())
                    text = text.replaceAll(regex, "$1");
                setText(text);
            }
        });
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);
        final Graphics2D g = (Graphics2D) pG;;
        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            repaint();
            revalidate();
            return;
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(placeholder_color);
        g.drawString(placeholder,  (( getWidth() - pG.getFontMetrics().stringWidth(placeholder))/2) - 1 , ((pG.getFontMetrics().getMaxAscent() + getHeight())/2) - 1 );
    }
    public void setPlaceholder_color(Color placeholder_color){
        this.placeholder_color = placeholder_color;
    }
    public void setPlaceholder(final String s) {
        placeholder_color = getDisabledTextColor();
        placeholder = s;
    }


}