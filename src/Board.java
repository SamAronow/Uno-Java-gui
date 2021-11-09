import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
public class Board extends JPanel{
    private Deck deck;
    private Hand[] hands;
    private JLabel[] nameLabels;
    private Card last;
    private JLabel lastPic;
    private JLabel topDeck;
    private JLabel direction;
    private JLabel curCol;
    private JLabel askColor;
    private JButton moveOn;
    private JButton draw;
    private JLayeredPane[] showColor;
    private ActionListener deckList;
    private boolean canPlay;
    private boolean reved;
    private int numPlayers;
    private String[] names;
    private JButton restart;

    private JButton pause;

    public Board(ActionListener list, String[] names, int numPlayers) {
        //Set Up Panel
        this.setLayout(null);
        this.setSize(900,750);
        this.setBackground(Color.GREEN);
        //Set up deck, hands and nameDisplays and adds them to board
        deck = new Deck(list);
        deckList =list; //used to recreate the deck when it runs out
        hands = new Hand[numPlayers];
        this.names = names;
        nameLabels = new JLabel[numPlayers];
        for (int i=0; i<numPlayers; i++) {
            hands[i] = new Hand(deck);
            nameLabels[i] = new JLabel(names[i]+"");
            nameLabels[i].setFont(new Font("MV Boli", Font.PLAIN, 25));
            nameLabels[i].setForeground(Color.RED);
            this.add(hands[i]);
            this.add(nameLabels[i]);
        }
        hands[0].sortHand();
        //set Up last card and set the image to the panel, making sure its not a wild
        last = deck.deal();
        while (last.getCardColor()==CardColor.ALL){
            deck = new Deck(list);
            last=deck.deal();
        }
        lastPic = new JLabel();
        lastPic.setIcon(last.getFront());
        lastPic.setBounds(463, 250, 100, 150);
        this.add(lastPic);
        // add pic of top deck to show the deck
        topDeck = new JLabel(Card.getBack());
        topDeck.setBounds(337,250,100,150);
        this.add(topDeck);
        //set up the direction arrow to show the direction of play
        direction = new JLabel(new ImageIcon("Arrow.png"));
        direction.setBounds(0,225,200,200);
        this.add(direction);
        //sets up label to show the current color (useful with wild play to see color of wild)
        curCol = new JLabel();
        curCol.setBounds(600,300,50,50);
        updateCurCol(); // sets color depending on what last is
        this.add(curCol);
        //set Up the label to ask for a new color when wild is played (starts not visible)
        askColor = new JLabel ("Choose New Color");
        askColor.setForeground(Color.RED);
        askColor.setFont(new Font("MV Boli", Font.PLAIN, 20));
        askColor.setBounds(600,250,250,25);
        this.add(askColor);
        askColor.setVisible(false);
        //sets up a JLayered pane with buttons and colors to allow for player to choose
        //color when wild is played by clicking button of desired color. (starts not Vis)
        JLabel[] setColor = new JLabel[4]; // local not used after added to layered pane
        JButton[] setButton = new JButton[4]; // local not used after added to layered pane
        showColor = new JLayeredPane[4];
        for (int i=0; i<4; i++){
            setColor[i]= new JLabel();
            setButton[i] = new JButton();
            setColor[i].setBounds(0,0,50,50);
            setButton[i].setBounds(0,0,50,50);
            showColor[i] = new JLayeredPane();
            showColor[i].setBounds(600+i*60,300,50,50);
            showColor[i].add(setColor[i],0);
            showColor[i].add(setButton[i],1);
            this.add(showColor[i]);
            showColor[i].setVisible(false);
        }
        setColor[0].setIcon(new ImageIcon ("Red.png"));
        setColor[1].setIcon(new ImageIcon ("Green.png"));
        setColor[2].setIcon(new ImageIcon ("Blue.png"));
        setColor[3].setIcon(new ImageIcon ("Yellow.png"));
        //adds actions to each of the buttons of the showColor panes (makes the panes actually do stuff)
        setButton[0].addActionListener(e -> {
            last.setWildColor(CardColor.RED);
            curCol.setIcon(new ImageIcon("Red.png"));
            repeatedSetButtonActions();
        });
        setButton[1].addActionListener(e -> {
            last.setWildColor(CardColor.GREEN);
            curCol.setIcon(new ImageIcon("Green.png"));
            repeatedSetButtonActions();
        });
        setButton[2].addActionListener(e ->{
            last.setWildColor(CardColor.BLUE);
            curCol.setIcon(new ImageIcon("Blue.png"));
            repeatedSetButtonActions();
        });
        setButton[3].addActionListener(e -> {
            last.setWildColor(CardColor.YELLOW);
            curCol.setIcon(new ImageIcon("Yellow.png"));
            repeatedSetButtonActions();
        });
        //sets up button to draw card and adds an action for it
        draw = new JButton("draw");
        draw.setBounds(337,410,225,75);
        draw.setFont(new Font("MV Boli", Font.PLAIN,25));
        draw.setForeground(Color.RED);
        this.add(draw);
        draw.addActionListener(e->{
            hands[0].getHand().add(deck.deal());
            checkEmpty();
            hands[0].sortHand();
            hands[0].updatePane();
            if (!checkCanPlay()){
                draw.setVisible(true);
            }
            else{
                draw.setVisible(false);
            }
        });
        //checks to see if draw is needed at start (if p1 has unplayable hand)
        if (!checkCanPlay()){
            draw.setVisible(true);
        }
        else{
            draw.setVisible(false);
        }
        //sets up button to allow player to move to next turn
        moveOn = new JButton("Next Turn");
        moveOn.setBounds(337,410,225,75);
        moveOn.setFont(new Font("MV Boli", Font.PLAIN,25));
        moveOn.setForeground(Color.RED);
        moveOn.setVisible(false);
        this.add(moveOn);
        moveOn.addActionListener(e->{
            nextTurn();
        });
        restart = new JButton("Restart");
        restart.setBounds(337,410,225,75);
        restart.setFont(new Font("MV Boli", Font.PLAIN,25));
        restart.setForeground(Color.RED);
        restart.setVisible(false);
        this.add(restart);
        restart.addActionListener(deckList);
        //initialize the starting primitives
        this.numPlayers = numPlayers;
        canPlay=false;
        reved=false;
        setLoc(); // sets up bounds of everything.
        pause = new JButton("Pause");
        pause.setBounds(200,200,50,50);
        pause.addActionListener(e->{
            if (pause.getText().equals("pause")){
                hands[0].updatePaneBack();
                pause.setText("resume");
            }
            else{
                hands[0].updatePane();
                pause.setText("pause");
            }
        });
        this.add(pause);
    }

    public JButton getRestart(){
        return restart;
    }

    //method for once a card is played, adds the moveOn button and makes the hand invisible
    //so you don't see the players cards passing the computer. also updates last cardPic and curCol
    public void waitNext(Card played){
        hands[0].updatePaneBack();
        last=played;
        lastPic.setIcon(last.getFront());
        updateCurCol();
        if (hands[0].getHand().size()==0){
            win();
        }
        else{
            moveOn.setVisible(true);
        }
    }
    //sets up the next turn checking for special and if they have to draw also rotates board
    private void nextTurn(){
        moveOn.setVisible(false);
            checkSpecial();
            if (reved) {
                revSwap();
            } else {
                swap();
            }
            hands[0].sortHand();
            setLoc();
            if (!checkCanPlay()) {
                draw.setVisible(true);
            } else {
                draw.setVisible(false);
            }
    }
    //checks if last is skip, draw 2, or reverse and does the respective action
    private void checkSpecial(){
        if (last.getType()==Name.SKIP){
            swap();
        }
        if (last.getType()==Name.DRAW_TWO){
            if (numPlayers ==2 || !reved){
                hands[1].getHand().add(deck.deal());
                checkEmpty();
                hands[1].getHand().add(deck.deal());
                checkEmpty();
            }
            else{
                hands[2].getHand().add(deck.deal());
                checkEmpty();
                hands[2].getHand().add(deck.deal());
                checkEmpty();
            }
        }
        if (last.getType()==Name.REVERSE) {
            reved = !reved;
        }
    }
    //If wild played asks what color they want and does action to choose that color.
    public void wildAction(Card played){
        last=played;
        lastPic.setIcon(last.getFront());
        setLoc();
        askColor.setVisible(true);
        curCol.setVisible(false);
        for (int i=0; i<4; i++){
            showColor[i].setVisible(true);
        }
        if (last.getType() == Name.DRAW_FOUR) {
            if (numPlayers ==2 || !reved){
                hands[1].getHand().add(deck.deal());
                checkEmpty();
                hands[1].getHand().add(deck.deal());
                checkEmpty();
                hands[1].getHand().add(deck.deal());
                checkEmpty();
                hands[1].getHand().add(deck.deal());
                checkEmpty();
            }
            else{
                hands[2].getHand().add(deck.deal());
                checkEmpty();
                hands[2].getHand().add(deck.deal());
                checkEmpty();
                hands[2].getHand().add(deck.deal());
                checkEmpty();
                hands[2].getHand().add(deck.deal());
                checkEmpty();
            }
        }
    }
    //updates the curCol to the color of last.
    private void updateCurCol(){
        if(last.getCardColor()==CardColor.BLUE){
            curCol.setIcon(new ImageIcon("Blue.png"));
        }
        if(last.getCardColor()==CardColor.YELLOW){
            curCol.setIcon(new ImageIcon("Yellow.png"));
        }
        if(last.getCardColor()==CardColor.GREEN){
            curCol.setIcon(new ImageIcon("Green.png"));
        }
        if(last.getCardColor()==CardColor.RED){
            curCol.setIcon(new ImageIcon("Red.png"));
        }
        curCol.setVisible(true);
    }
    //swaps the card when no reverse is in play.
    private void swap(){
        String temp = nameLabels[0].getText();
        nameLabels[0].setText(nameLabels[1].getText());
        Hand tempH = hands[0];
        hands[0]=hands[1];
        if (numPlayers == 2){
            nameLabels[1].setText(temp);
            hands[1] = tempH;
        }
        else {
            nameLabels[1].setText(nameLabels[2].getText());
            nameLabels[2].setText(temp);
            hands[1] = hands[2];
            hands[2] = tempH;
        }
    }
    //swaps cards when reverse is in play
    private void revSwap(){
        String temp = nameLabels[0].getText();
        Hand tempH = hands[0];
        if (numPlayers ==2){
            nameLabels[0].setText(nameLabels[1].getText());
            hands[0]=hands[1];
            nameLabels[1].setText(temp);
            hands[1] = tempH;
        }
        else {
            nameLabels[0].setText(nameLabels[2].getText());
            nameLabels[2].setText(nameLabels[1].getText());
            nameLabels[1].setText(temp);
            hands[0] = hands[2];
            hands[2] = hands[1];
            hands[1] = tempH;
        }
    }
    //sets location of everything after each turn
    public void setLoc(){
        nameLabels[0].setBounds(10,460,885,35);
        hands[0].setBounds(10, 500, 885, 150);
        nameLabels[1].setBounds(10, 0, 435,35);
        hands[1].setBounds(10, 40, 435, 150);
        if (numPlayers==3) {
            nameLabels[2].setBounds(460, 0, 435, 35);
            hands[2].setBounds(455, 40, 435, 150);
        }
        if (!reved){
            direction.setIcon(new ImageIcon("Arrow.png"));
        }
        if (reved){
            direction.setIcon(new ImageIcon("RevArrow.png"));
        }
        hands[0].updatePane();
        hands[1].updatePaneBack();
        if (numPlayers==3) {
            hands[2].updatePaneBack();
        }
    }
    //checks if they can play and returns it
    private boolean checkCanPlay(){
        for (Card card : hands[0].getHand()){
            if (card.compareCard(last)){
                return true;
            }
        }
        return false;
    }
    //displays a win
    public void win(){
        JLabel win = new JLabel(nameLabels[0].getText()+" Wins!");
        win.setBounds(10,500,800,150);
        win.setFont(new Font("MV Boli", Font.PLAIN, 100));
        win.setForeground(Color.BLUE);
        this.add(win);
        restart.setVisible(true);
    }
    //when adding actions for the buttons this is the repeated stuff so easier to just add method
    private void repeatedSetButtonActions(){
        hands[0].updatePaneBack();
        moveOn.setVisible(true);
        askColor.setVisible(false);
        for (int i=0; i<4; i++){
            showColor[i].setVisible(false);
        }
        curCol.setVisible(true);
    }
    //checks if deck is empty and resets it and removes cards in hands
    public void checkEmpty(){
        if (deck.getDeck().size()==0){
            deck.emptyDeck(deckList,hands, last);
        }
    }
    //gets the current hand for checking which card is played in main class
    public Hand getCurHand(){
        return hands[0];
    }
    //gets last card to compare for checking if valid play in main class
    public Card getLast(){
        return last;
    }
}
