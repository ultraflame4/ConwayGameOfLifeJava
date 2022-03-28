package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

public class CellGrid extends JComponent implements MouseListener {
    static final int CellSize = 10;
    private int width;
    private int height;
    int cellCountX;
    int cellCountY;
    boolean[] cells;

    boolean isSimulating = false;
    public int fps = 30;
    Timer timer;

    public CellGrid(int width, int height) {
        this.height = height;
        this.width = width;
        cellCountX = (int) Math.floor(width / CellSize);
        cellCountY = (int) Math.floor(height / CellSize);
        cells = new boolean[cellCountX * cellCountY];
        addMouseListener(this);
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));

        timer = new Timer((1 / fps) * 1000, e -> {
            Simulate();
        });
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < cells.length; i++) {
            g.setColor(cells[i] ? Color.BLACK : Color.WHITE);
            int[] coords = Index2XY(i);
            int x = coords[0];
            int y = coords[1];
            g.fillRect(x * CellSize, y * CellSize, CellSize, CellSize);
        }


        drawGrid(g);
    }

    void drawGrid(Graphics g) {
        g.setColor(Color.gray);
        for (int x = 0; x < width; x += CellSize) {
            g.fillRect(x, 0, 1, height);
            for (int y = 0; y < height; y += CellSize) {
                g.fillRect(0, y, width, 1);
            }
        }
    }

    int XY2Index(int x, int y) {
        return y * cellCountX + x;
    }

    int[] Index2XY(int index) {
        int y = (int) Math.floor(index / cellCountX);
        int x = index % cellCountX;
        return new int[]{x, y};
    }


    public void StartSim() {
        isSimulating = true;
        timer.start();
    }

    public void StopSim() {
        isSimulating = false;
        if (timer != null) {
            timer.stop();
        }
    }

    boolean getCellState(int x, int y) {
        int index = XY2Index(x, y);
        if (index < 0 || index > cells.length-1) {
            return false;
        }
        return cells[index];
    }

    public void Simulate() {
        repaint(0, 0, width, height);

        boolean[] bufferCells = cells.clone();
        for (int i = 0; i < cells.length; i++) {
            int[] xy = Index2XY(i);
            int x = xy[0];
            int y = xy[1];
            boolean[] neighbourStates = new boolean[]{
                    //top
                    getCellState(x - 1, y - 1),
                    getCellState(x, y - 1),
                    getCellState(x + 1, y - 1),
                    //sides
                    getCellState(x + 1, y),
                    getCellState(x - 1, y),
                    //bottom
                    getCellState(x - 1, y + 1),
                    getCellState(x, y + 1),
                    getCellState(x + 1, y + 1),
            };

            int neighbourCount = 0;
            //count
            for (int j = 0; j < neighbourStates.length; j++) {
                if (neighbourStates[j]) {
                    neighbourCount++;
                }
            }

            if (neighbourCount<2||neighbourCount > 3){
                //die
                bufferCells[i] = false;
            }
            else if (neighbourCount==3) {
                bufferCells[i] = true;
            }
        }
        cells = bufferCells;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (int) Math.floor(e.getX() / CellSize);
        int y = (int) Math.floor(e.getY() / CellSize);
        int index = XY2Index(x, y);
        cells[index] = !cells[index];
        repaint(0, 0, width, height);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void ClearCells() {
        repaint(0, 0, width, height);
        cells = new boolean[cells.length];
    }
}
