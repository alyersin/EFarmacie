package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTLabel {
    private JLabel labelDataOra;
    private Timer timer;

    public DTLabel(JLabel labelDataOra) {
        this.labelDataOra = labelDataOra;
    }

    public void start() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateTime();
            }
        });
        timer.start();
    }

    private void getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String oraActuala = sdf.format(new Date());
        labelDataOra.setText(oraActuala);
    }
}
