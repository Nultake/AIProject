package Game.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Game.State;
import Game.TileType;
import Game.Algorithms.AStarAlgorithm;
import Game.Algorithms.Algorithm;
import Game.Algorithms.UniformCostAlgorithm;

public class SettingsFrame extends JFrame {
    private JComboBox<String> comboBox;

    public SettingsFrame() {
        ArrayList<ColorButton> initialButtons = new ArrayList<>();
        ArrayList<ColorButton> goalButtons = new ArrayList<>();
        ArrayList<ColorButton> orderButtons = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 550);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setTitle("AI Project Settings");

        JLabel lblNewLabel = new JLabel("Inital State :");
        lblNewLabel.setBounds(121, 61, 71, 14);
        contentPane.add(lblNewLabel);

        for (int i = 0; i < 3; i++) {

            ColorButton btnO = new ColorButton();
            btnO.setBounds((201 + (i * 48)), 256, 42, 36);
            btnO.setBackground(Color.white);
            contentPane.add(btnO);

            btnO.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ColorButton clickedButton = (ColorButton) e.getSource();

                    switch (clickedButton.getTileType()) {
                        case EMPTY:
                            clickedButton.setTileType(TileType.GREEN);
                            clickedButton.setBackground(Color.GREEN);
                            break;
                        case GREEN:
                            clickedButton.setTileType(TileType.BLUE);
                            clickedButton.setBackground(Color.BLUE);
                            break;
                        case BLUE:
                            clickedButton.setTileType(TileType.RED);
                            clickedButton.setBackground(Color.RED);
                            break;
                        case RED:
                            clickedButton.setTileType(TileType.EMPTY);
                            clickedButton.setBackground(Color.WHITE);
                            break;
                    }

                }
            });

            orderButtons.add(btnO);

            for (int j = 0; j < 3; j++) {
                ColorButton btn = new ColorButton();
                btn.setBounds((201 + (i * 48)), (57 + (j * 47)), 42, 36);
                btn.setBackground(Color.white);
                contentPane.add(btn);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ColorButton clickedButton = (ColorButton) e.getSource();

                        switch (clickedButton.getTileType()) {
                            case EMPTY:
                                clickedButton.setTileType(TileType.GREEN);
                                clickedButton.setBackground(Color.GREEN);
                                break;
                            case GREEN:
                                clickedButton.setTileType(TileType.BLUE);
                                clickedButton.setBackground(Color.BLUE);
                                break;
                            case BLUE:
                                clickedButton.setTileType(TileType.RED);
                                clickedButton.setBackground(Color.RED);
                                break;
                            case RED:
                                clickedButton.setTileType(TileType.EMPTY);
                                clickedButton.setBackground(Color.WHITE);
                                break;
                        }

                    }
                });

                ColorButton btnG = new ColorButton();
                btnG.setBounds((548 + (i * 48)), (57 + (j * 47)), 42, 36);
                btnG.setBackground(Color.white);
                contentPane.add(btnG);
                btnG.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ColorButton clickedButton = (ColorButton) e.getSource();

                        switch (clickedButton.getTileType()) {
                            case EMPTY:
                                clickedButton.setTileType(TileType.GREEN);
                                clickedButton.setBackground(Color.GREEN);
                                break;
                            case GREEN:
                                clickedButton.setTileType(TileType.BLUE);
                                clickedButton.setBackground(Color.BLUE);
                                break;
                            case BLUE:
                                clickedButton.setTileType(TileType.RED);
                                clickedButton.setBackground(Color.RED);
                                break;
                            case RED:
                                clickedButton.setTileType(TileType.EMPTY);
                                clickedButton.setBackground(Color.WHITE);
                                break;
                        }

                    }
                });

                initialButtons.add(btn);

                goalButtons.add(btnG);
            }
        }

        JLabel lblGoalState = new JLabel("Goal State:");
        lblGoalState.setBounds(468, 61, 71, 14);
        contentPane.add(lblGoalState);

        JLabel lblNewLabel_1 = new JLabel("Tile Order :");
        lblNewLabel_1.setBounds(121, 269, 71, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Algorithm:");
        lblNewLabel_2.setBounds(468, 269, 71, 14);
        contentPane.add(lblNewLabel_2);

        comboBox = new JComboBox<String>();
        comboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "A* Algorithm", "Uniformed Cost Algorithm" }));
        comboBox.setBounds(548, 265, 142, 22);
        contentPane.add(comboBox);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setBounds(274, 387, 316, 23);
        contentPane.add(startGameButton);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkTiles(orderButtons) && checkTiles(initialButtons) && checkTiles(goalButtons)) {
                    Algorithm algorithm = null;
                    if (comboBox.getSelectedIndex() == 0)
                        algorithm = new AStarAlgorithm();

                    else if (comboBox.getSelectedIndex() == 1)
                        algorithm = new UniformCostAlgorithm();

                    State goalState = new State();
                    goalState.setTiles(buttonsToTileTypes(goalButtons));

                    State initialState = new State();
                    initialState.setTiles(buttonsToTileTypes(initialButtons));

                    algorithm.setGoalState(goalState);
                    algorithm.setInitialState(initialState);

                    int i = 0;
                    TileType[] order = new TileType[3];

                    for (ColorButton colorButton : orderButtons) {
                        order[i] = colorButton.getTileType();
                        i++;
                    }

                    algorithm.setOrder(order);

                    State[] solutionPath = algorithm.findSolutionPath();

                    new GameBoardFrame(algorithm, solutionPath);

                } else
                    JOptionPane.showMessageDialog(null, "There is tile that is not fit",
                            "Something went wrong!", JOptionPane.ERROR_MESSAGE);

            }
        });

        setVisible(true);
    }

    private boolean checkTiles(ArrayList<ColorButton> buttons) {
        boolean greenFound = false;
        boolean blueFound = false;
        boolean redFound = false;

        for (ColorButton colorButton : buttons) {
            switch (colorButton.getTileType()) {
                case GREEN:
                    if (greenFound)
                        return false;
                    greenFound = true;
                    break;
                case RED:
                    if (redFound)
                        return false;
                    redFound = true;
                    break;
                case BLUE:
                    if (blueFound)
                        return false;
                    blueFound = true;
                    break;
                default:
                    break;
            }
        }

        return greenFound && blueFound && redFound;
    }

    private TileType[][] buttonsToTileTypes(ArrayList<ColorButton> colorButtons) {
        TileType[][] tileTypes = new TileType[3][3];
        int i = 0, j = 0;
        for (ColorButton colorButton : colorButtons) {
            tileTypes[i][j] = colorButton.getTileType();

            if (i < 2)
                i++;
            else {
                i = 0;
                j++;
            }
        }

        return tileTypes;
    }
}
