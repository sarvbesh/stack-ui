import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;

class Stack<T> {
    private java.util.Stack<T> elements; //  to store elements
    private T lastPoppedItem; // stores last popped item
    private int maxSize; // maximum size of stack

    public Stack(int maxSize) {
        elements = new java.util.Stack<>(); // initialize stack
        lastPoppedItem = null; // initialize last popped item
        this.maxSize = maxSize; // set maximum size
    }

    public void push(T item) {
        if (elements.size() < maxSize) {
            elements.push(item); // push item if stack is empty
        } else {
            throw new StackOverflowError(); // throw error if stack is full
        }
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException(); // throw error if stack is empty
        }
        lastPoppedItem = elements.pop(); // pop item and store it
        return lastPoppedItem; // return popped item
    }

    public boolean isEmpty() {
        return elements.isEmpty(); // check if stack is empty
    }

    public int size() {
        return elements.size(); // return current size of stack
    }

    public T get(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException(" " + index + ", " + elements.size()); // throw error if index is invalid
        }
        return elements.get(index); // get item at specified index
    }
}

public class Micro extends JFrame {

    private Stack<Integer> integerStack; // stack to store integers
    private JLabel poppedLabel; 
    private GraphicsPanel graphicsPanel; // panel to draw stack visually
    private JTextField inputField;
    Font font;

    public Micro() {
        super("Stack GUI"); // title of JFrame
        integerStack = new Stack<>(12); // initialize stack with maximum size 12

        font = new Font("Times New Roman", Font.PLAIN, 20);

        JButton pushButton = new JButton("Push Item"); // push button
        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pushItem(); 
            }
        });

        JButton popButton = new JButton("Pop Item"); // pop button
        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popItem(); 
            }
        });

        inputField = new JTextField(10); // input field
        poppedLabel = new JLabel("Popped Item: ");
        graphicsPanel = new GraphicsPanel(); // graphics panel

        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Enter Number: "));
        buttonPanel.add(inputField);
        buttonPanel.add(pushButton);
        buttonPanel.add(popButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(poppedLabel, BorderLayout.SOUTH);
        add(graphicsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void pushItem() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int userInput = Integer.parseInt(inputField.getText());
                    integerStack.push(userInput);
                    poppedLabel.setText("Popped Item: ");
                    graphicsPanel.repaint();
                } catch (NumberFormatException | StackOverflowError ex) {
                    poppedLabel.setText("Invalid Input or Stack is FULL");
                    JOptionPane.showMessageDialog(Micro.this, "Invalid input or Stack is full. Please enter a valid number.");
                }
            }
        });
    }

    private void popItem() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer poppedItem = integerStack.pop();
                    poppedLabel.setText("Popped Item: " + poppedItem);
                    graphicsPanel.repaint();
                } catch (EmptyStackException ex) {
                    poppedLabel.setText("Stack is empty");
                    JOptionPane.showMessageDialog(Micro.this, "Stack is empty. Cannot pop more items.");
                }
            }
        });
    }

    private class GraphicsPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int stackHeight = integerStack.size(); 
            int startX = getWidth() / 3 - 30; 
            int startY = getHeight() - 50; 
            for (int i = 0; i < stackHeight; i++) {
                g.drawRect(startX, startY - i * 40, 300, 40); 
                g.setFont(font); 
                g.drawString("Index " + i, startX - 70, startY - i * 40 + 25); 
                g.drawString(String.valueOf(integerStack.get(i)), startX + 150, startY - i * 40 + 25); 
                drawArrow(g, startX - 80, startY - i * 40 + 25, 10, Color.BLACK); 
            }

            g.setXORMode(Color.orange); 
            g.drawLine(700, 700, 700, 230); 
            g.drawLine(392, 700, 392, 230); 
            g.drawLine(700, 645, 400, 645); 
        }

        private void drawArrow(Graphics g, int x, int y, int size, Color color) {
            g.setColor(color);
            g.drawLine(x, y, x - size, y - size);
            g.drawLine(x - size, y - size, x - size, y + size);
            g.drawLine(x - size, y + size, x, y);
        }

        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    public static void main(String[] args) {
        new Micro(); // instance of Micro class to start GUI
    }
}