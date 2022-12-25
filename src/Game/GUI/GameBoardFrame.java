package Game.GUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Game.State;
import Game.TileType;
import Game.Algorithms.Algorithm;
import Game.Algorithms.CostBasedAlgorithm;
import Game.Algorithms.UniformCostAlgorithm;

public class GameBoardFrame extends JFrame {
    public GameBoardFrame(Algorithm algorithm, State[] solutionPath) {
        setBounds(50, 50, 1800, 800);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setTitle(algorithm instanceof UniformCostAlgorithm ? "Result : Uniform Cost Algorithm"
                : "Result : A* Algorithm");

        setTitle(getTitle() + " Cost : " + ((CostBasedAlgorithm) algorithm).calculatePathCost(solutionPath));

        int x = 0;
        int y = 0;
        for (State state : solutionPath) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ColorButton btn = new ColorButton();
                    btn.setBounds((201 + (i * 48) + (x * 359)), (57 + (j * 47) + (y * 267)), 42, 36);
                    TileType type = state.getTiles()[j][i];

                    switch (type) {
                        case EMPTY:
                            btn.setBackground(Color.WHITE);
                            break;
                        case RED:
                            btn.setBackground(Color.RED);
                            break;
                        case BLUE:
                            btn.setBackground(Color.BLUE);
                            break;
                        case GREEN:
                            btn.setBackground(Color.GREEN);
                            break;
                        default:
                            break;
                    }
                    contentPane.add(btn);

                }
            }
            x++;
            if (x == 4) {
                x = 0;
                y++;
            }
        }
        setVisible(true);
    }
}
