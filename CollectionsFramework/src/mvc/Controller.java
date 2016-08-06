package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    public static void start() {
        Model model = new Model();
        View view = new View();
        ActionListener add10ActionListener = (ActionEvent evt) -> {
            int input = view.getInput();
            int output = model.addTen(input);
            view.setOutput(output);
        };
        view.setAdd10Action(add10ActionListener);
        view.setVisible(true);
    }
    public static void main(String[] args) {
        Controller.start();
    }
}
