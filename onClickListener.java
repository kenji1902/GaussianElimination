import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class onClickListener implements ActionListener {
    private updateUI ui;
    private int RandCounter;
    private int page;
    private int prevpage;

    onClickListener(updateUI ui){
        this.ui = ui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        page = ui.getPage();
        prevpage = ui.getPrevpage();
        GaussianElimUtil gaussian = ui.getGaussian();
        String sButtonId = e.getActionCommand();
        TimerTask delay = new TimerTask() {
            @Override
            public void run() {
                if (page < ui.getGaussian().steps.size() - 1) {
                    prevpage = page;
                    page++;
                    ui.setPrevpage(prevpage);
                    ui.setPage(page);
                    ui.colorChange(prevpage, false);
                    ui.colorChange(page, true);
                }
            }
        };
        SwingWorker<Void,Void> geninBG = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JOptionPane.showMessageDialog(null,"Press the Rand Button again to Stop" ,"Generating Matrix",JOptionPane.INFORMATION_MESSAGE);
                RandCounter = ui.getRandomTaskCount();
                ui.setRandomTaskCount(++RandCounter);
                ui.getInputPanel().setAcceptInput(false);
                ui.getInputPanel().getInput().setEnabled(false);
                genRandom();
                return null;
            }

            @Override
            protected void done() {
                super.done();
                ui.setRandomTaskCount(0);
                ui.getInputPanel().setAcceptInput(true);
                ui.getInputPanel().getInput().setEnabled(true);
                ui.getTable().getPanel().removeAll();
                ui.getInformation().getPanel().removeAll();
                ui.getInformation().Setup(ui);
                ui.getTable().setup(ui);
                ui.getFrame().repaint();
                ui.getFrame().revalidate();
            }

        };
        switch (sButtonId){
            case "first":
                ui.CancelPlay();
                prevpage = page;
                page = 0;
                ui.getTable().getPanel().removeAll();
                ui.setPage(page);
                ui.setPrevpage(prevpage);

                ui.getTable().setup(ui);
                ui.getTable().getPanel().repaint();
                ui.getTable().getPanel().revalidate();
                break;
            case "prev":
                ui.CancelPlay();
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
                ui.CancelPlay();
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
                ui.CancelPlay();
                prevpage = page;
                page = gaussian.steps.size()-1;
                ui.getTable().getPanel().removeAll();
                ui.setPage(page);
                ui.setPrevpage(prevpage);

                ui.getTable().setup(ui);
                ui.getTable().getPanel().repaint();
                ui.getTable().getPanel().revalidate();
                break;
            case "play":
                ui.setPlay(delay);
                break;
            case "stop":
                ui.CancelPlay();
                break;
            case "calculate":
                ui.CancelPlay();
                if(ui.getInputPanel().isAcceptInput()){
                    try{
                        System.out.println("Calculate");
                        gaussian = new GaussianElimUtil(ui.getNums(ui.getInputPanel().getInput().getText()));
                        gaussian.gaussianElimination();
                        prevpage = 0;
                        page = 0;
                        ui.setGaussian(gaussian);
                        ui.setPage(page);
                        ui.setPrevpage(prevpage);
                        ui.getTable().getPanel().removeAll();
                        ui.getInformation().getPanel().removeAll();
                        ui.getInformation().Setup(ui);
                        ui.getTable().setup(ui);
                        ui.getFrame().repaint();
                        ui.getFrame().revalidate();

                    }catch (Exception err){
                        JOptionPane.showMessageDialog(null,"Please provide an Input\n"+err.getMessage() ,"Invalid Input",JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    if(ui.getRandomTaskCount() < 1)
                        JOptionPane.showMessageDialog(null, "Please provide an Input\n", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null,"Random Task in Progress\nCalculate Disabled","Random Task",JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "random":
                ui.CancelPlay();
                if(ui.getRandomTaskCount() > 0) {
                    geninBG.cancel(true);
                    JOptionPane.showMessageDialog(null,"Random Generator Cancelled" ,"Random",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    geninBG.execute();
                break;
        }
        try {
            System.out.println("part: " + gaussian.steps.get(page).part);
            System.out.println("Prev Page >> " + prevpage);
            System.out.println("Page >> " + page);
            System.out.println("Random Task Count: " + ui.getRandomTaskCount());
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
    }
    void genRandom(){
        inputPanel input = ui.getInputPanel();
        int page;
        int prevpage;
        GaussianElimUtil gaussian = null;
        double[][] random;
        while(ui.getRandomTaskCount() > 0) {
            try {
                try {
                    random = ui.getRand(input.getInput().getText());
                }catch (Exception randErr){
                    ui.setRandomTaskCount(0);
                    JOptionPane.showMessageDialog(null,"Please provide an Input\n"+randErr.getMessage() ,"Invalid Input",JOptionPane.WARNING_MESSAGE);
                    break;
                }
                gaussian = new GaussianElimUtil(random);
                gaussian.gaussianElimination();
                break;
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
        if(ui.getRandomTaskCount() > 0){
            prevpage = 0;
            page = 0;
            ui.setGaussian(gaussian);
            ui.setPage(page);
            ui.setPrevpage(prevpage);
        }

    }

}