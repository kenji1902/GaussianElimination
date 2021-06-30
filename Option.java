import java.awt.*;

public interface Option {
    //Colors
    Color cellRed = new Color(255, 0, 47);
    Color cellPurple = new Color(152, 118, 170);
    Color cellYellow = new Color(255, 195, 0);
    Color cellOrange = new Color(251, 128, 14);
    Color cellGreen = new Color(113, 148, 93);
    Color cellWhite = new Color(207, 207, 207);
    Color cellGray = new Color(43, 43, 43);
    Color cellBlack = new Color(28, 27, 27);
    Color cellBlue =new Color(64, 67, 245);

    //Panel Frame Size
    float informationWeightx = 0.7f;
    float informationWeighty = 0.3f;
    float tableWeightx = 1;
    float tableWeighty = 0.7f;
    float scrollTableWeightx1 = 0.80f;
    float scrollTableWeightx2 = 0.20f;
    float codeWeightx = 0.3f;
    float codeWeighty = 0.3f;

    //Button ID
    enum buttonId{
        next,
        prev,
        equal,
        calculate,
        dynButton
    }
}
