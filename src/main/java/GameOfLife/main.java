package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {


    public static void main(String[] args) {
        System.out.println("GameOfLife by ultraflame42");
        JFrame root = new JFrame("GameOfLife");
        root.setSize(1020,720);
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CellGrid cellGrid = new CellGrid(800, 500);
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellGrid.StartSim();
            }
        });
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellGrid.StopSim();
            }
        });
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellGrid.StopSim();
                cellGrid.Simulate();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellGrid.ClearCells();
            }
        });

        JPanel buttonCtn = new JPanel();
        buttonCtn.setLayout(new BoxLayout(buttonCtn, BoxLayout.Y_AXIS));
        buttonCtn.add(playButton);
        buttonCtn.add(pauseButton);
        buttonCtn.add(stepButton);
        buttonCtn.add(clearButton);


        root.setLayout(new BoxLayout(root.getContentPane(),BoxLayout.X_AXIS));
        root.add(cellGrid);
        root.add(buttonCtn);

        root.setVisible(true);

    }
}
