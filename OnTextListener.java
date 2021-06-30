import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class OnTextListener implements DocumentListener {
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