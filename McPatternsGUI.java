import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class McPatternsGUI extends JFrame {
	private McPatternsPresenter presenter;
    private JTextArea currentOrder;
    private static final String NO_MENU = "No menu file was read";
	
    private McPatternsGUI() {}
    
	public McPatternsGUI(McPatternsPresenter presenter) {
		
		this.presenter = presenter;
		presenter.attachView(this);
		
		presenter.loadMenuItems();
        makeGUI();
		showGUI();
	}
	
	private void makeGUI() {
	       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       this.setLayout(new BorderLayout());

	        makeTitle();
	        makeMenu(presenter.getItems());
	        makeOrderPanel();
	}
	
	private void makeTitle() {
	       JPanel title = new JPanel(new FlowLayout());
	       title.add(new JLabel("Point of Sale UI"));
	       this.add(title, BorderLayout.NORTH);
	}
	
	private void makeMenu(Set<String> items) {
        JPanel panel;

        if (items.size() == 0) {
            panel = new JPanel(new GridLayout(3,3));
            String label = "<html><body style='width: 200px'>" + NO_MENU + "</body></html>";
            for (int i = 0; i < 9; i++)
                panel.add(new JLabel(label));
        }
        else {
            int cols = (int)Math.ceil(Math.sqrt(items.size()));
            panel = new JPanel(new GridLayout(cols, cols));

            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    presenter.addToOrder(((JButton)e.getSource()).getText());
                }
            };

            for (String item : items) {
                JButton btn = new JButton(item);
                btn.addActionListener(listener);
                panel.add(btn);
            }
        }

        this.add(panel, BorderLayout.CENTER);
        this.pack();
    }
	
	private void makeOrderPanel() {
        JPanel orderButtons = new JPanel(new GridLayout(1,2));

        JButton confirm = new JButton("Place Order") {
            @Override
            public Dimension getPreferredSize() {
                int width = this.getParent().getWidth() / 2;
                return new Dimension(width, width);
            }
        };
        confirm.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                placeOrder();
       }
       });
        orderButtons.add(confirm);
      
        JButton cancel = new JButton("Cancel") {
            @Override
            public Dimension getPreferredSize() {
                int width = this.getParent().getWidth() / 2;
                return new Dimension(width, width);
            }
        };
        cancel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                if (presenter.getOrderItemCount() != 0)
                    if (0 == JOptionPane.showConfirmDialog((Component)e.getSource(), "Cancel order?", "Cancel order?", JOptionPane.YES_NO_OPTION)) {
                       presenter.cancelOrder();
                        currentOrder.setText("");
                    }

           }
       });
        orderButtons.add(cancel);
      
       JPanel orderPane = new JPanel();
       orderPane.setLayout(new BorderLayout());
       orderPane.setBorder(BorderFactory.createRaisedBevelBorder());

        JLabel yourOrder = new JLabel("Order List");
        yourOrder.setHorizontalAlignment(SwingConstants.CENTER);
        orderPane.add(yourOrder, BorderLayout.NORTH);

        
        currentOrder = new JTextArea();
        currentOrder.setEditable(false);

        currentOrder.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        currentOrder.setText("                                   ");

       orderPane.add(currentOrder, BorderLayout.CENTER);
       orderPane.add(orderButtons, BorderLayout.SOUTH);
       this.add(orderPane, BorderLayout.EAST);
    }
	
	private void showGUI() {
		this.pack();
	    this.setVisible(true);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void placeOrder() {
        if (presenter.getOrderItemCount() != 0) {
            CreditCard card = new InvalidCard();
            String prompt = "\"Swipe\" the card.";
      
            do {
                String number = (String)JOptionPane.showInputDialog(this, prompt, "Complete the order.", JOptionPane.PLAIN_MESSAGE);

                if (number == null) {
                    JOptionPane.showMessageDialog(this, "Order completion canceled.", "Payment canceled.", JOptionPane.WARNING_MESSAGE);
                    return;
                }
              
                card = CreditCardFactory.build(number);

                prompt = "Invalid card.\n\"Swipe\" the card again.";
            } while (card.getNumber() == null);

            presenter.submitOrder(card);
            JOptionPane.showMessageDialog(this, "Paid using " + card.getName(), "Order completed.", JOptionPane.PLAIN_MESSAGE);
        }
    }
	
	void printOrder(String text) {
        currentOrder.setText(text);
    }

	void error(String message) {
        JOptionPane.showMessageDialog(this, message, "There's been a problem.", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
