import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class onClickListener implements ActionListener {
    private updateUI ui;

    onClickListener(updateUI ui){
        this.ui = ui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        inputPanel input = ui.getInputPanel();
        informationPanel information = ui.getInformation();

        int page = ui.getPage();
        int prevpage = ui.getPrevpage();
        GaussianElimUtil gaussian = ui.getGaussian();
        JPanel table = ui.getTable().getPanel();

        String sButtonId = e.getActionCommand();
        switch (sButtonId){
            case "prev":
                if(page > 0) {
                    prevpage = page;
                    page--;
                    ui.setPage(page);
                    ui.setPrevpage(prevpage);
                    ui.colorChange(prevpage,false);
                    ui.colorChange(page,true);
                }
                break;
            case "next":
                if(page < gaussian.steps.size()-1) {
                    prevpage = page;
                    page++;
                    ui.setPage(page);
                    ui.setPrevpage(prevpage);
                    ui.colorChange(prevpage,false);
                    ui.colorChange(page,true);
                }
                break;
            case "equal":
                prevpage = page;
                page = gaussian.steps.size()-1;
                table.removeAll();
                ui.setPage(page);
                ui.setPrevpage(prevpage);
                //tablePanel(gaussian.steps.get(gaussian.steps.size()-1).matrix,gaussian.steps.get(gaussian.steps.size()-1).Solution);
                tablePanel tablePanel = new tablePanel(table);
                tablePanel.setup(ui);
                tablePanel.getPanel().repaint();
                tablePanel.getPanel().revalidate();
                break;
            case "calculate":
                if(input.isAcceptInput()){
                    try{
                        gaussian = new GaussianElimUtil(ui.getNums(input.getInput().getText()));
                        gaussian.gaussianElimination();
                        ui.setGaussian(gaussian);

                    }catch (Exception err){
                        JOptionPane.showMessageDialog(null,"Please provide an Input\n"+err.getMessage() ,"Invalid Input",JOptionPane.WARNING_MESSAGE);
                    }
                    prevpage = 0;
                    page = 0;
                    table.removeAll();
                    information.getPanel().removeAll();
                    ui.getInformation().Setup(ui);
                    ui.getTable().setup(ui);
                    ui.getFrame().repaint();
                    ui.getFrame().revalidate();
                }
                break;
        }
        System.out.println("part: "+gaussian.steps.get(page).part);
        System.out.println("Prev Page >> "+prevpage);
        System.out.println("Page >> "+page);
    }
}