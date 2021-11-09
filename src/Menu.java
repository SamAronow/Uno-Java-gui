import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu extends JPanel{
    private int numPlayers;
    private String[] names;
    private JLabel welcome = new JLabel("Welcome to Uno!");
    private ArrayList<JTextField> nameFields= new ArrayList();
    private ArrayList<JLabel> nameLabels = new ArrayList();
    private JButton enter = new JButton("Play!");
    private JButton changeNum = new JButton ("Two Players");

    public Menu() {
        this.setLayout(null);
        this.setSize(750, 750);
        this.setBackground(Color.GREEN);
        numPlayers = 3;
        names = new String[numPlayers];
        welcome.setBounds(125, 25, 500, 75);
        welcome.setFont(new Font("MV Boli", Font.BOLD, 55));
        welcome.setForeground(Color.RED);
        this.add(welcome);
        changeNum.setBounds(150,575,200,100);
        this.add(changeNum);
        changeNum.addActionListener(e -> {
            update();
        });
        enter.setBounds(400, 575, 200, 100);
        this.add(enter);
        for (int i=0; i<numPlayers; i++) {
            nameLabels.add(new JLabel("Enter Player Name:"));
            nameLabels.get(i).setBounds(25, 200+(i*100), 325, 75);
            nameLabels.get(i).setFont(new Font("MV Boli", Font.PLAIN, 25));
            nameLabels.get(i).setForeground(Color.RED);
            this.add(nameLabels.get(i));
        }
        for (int i = 0; i <numPlayers; i++){
            nameFields.add(new JTextField());
            nameFields.get(i).setBounds(400, 215 + (i * 100), 250, 50);
            nameFields.get(i).setFont(new Font("MV Boli", Font.PLAIN, 25));
            nameFields.get(i).setForeground(Color.RED);
            this.add(nameFields.get(i));
        }
    }

        public void update(){
            if (numPlayers==3){
                numPlayers=2;
                changeNum.setText("Three Players");
                nameLabels.get(2).setVisible(false);
                nameFields.get(2).setVisible(false);
                nameFields.get(2).setText("");
            }
            else{
                numPlayers=3;
                changeNum.setText("Two Players");
                nameLabels.get(2).setVisible(true);
                nameFields.get(2).setVisible(true);
            }
        }

    public JButton getEnter(){
        return enter;
    }

    public boolean validNames(){
        for (int i=0; i<numPlayers; i++){
            if (nameFields.get(i).getText().equals("")){
                return false;
            }
        }
        if (nameFields.get(0).getText().equals(nameFields.get(1).getText())){
            return false;
        }
        if (numPlayers==3 && (nameFields.get(0).getText().equals(nameFields.get(2).getText()) || nameFields.get(1).getText().equals(nameFields.get(2).getText()))){
            return false;
        }
        for (int i=0; i<numPlayers; i++) {
            names[i] = nameFields.get(i).getText();
        }
        return true;
    }

    public String[] getNames(){
        return names;
    }

    public int getNumPlayers(){
        return numPlayers;
    }
}
