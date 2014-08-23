/*
 * Copyright 2014 olivier.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author olivier
 */
public class WinCalc extends JFrame implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JTextField res;
	private final JPanel pan;
	private final JPanel grid;
	private boolean computationInProgress;
	private boolean isInfinity;

	// creation of the buttons
	private final JButton button1 = new JButton("1");
	private final JButton button2 = new JButton("2");
	private final JButton button3 = new JButton("3");
	private final JButton button4 = new JButton("4");
	private final JButton button5 = new JButton("5");
	private final JButton button6 = new JButton("6");
	private final JButton button7 = new JButton("7");
	private final JButton button8 = new JButton("8");
	private final JButton button9 = new JButton("9");
	private final JButton button0 = new JButton("0");
	private final JButton buttonPoint = new JButton(".");
	private final JButton buttonPOW = new JButton("^");
	private final JButton buttonPlus = new JButton("+");
	private final JButton buttonMinus = new JButton("-");
	private final JButton buttonMult = new JButton("\u00D7");
	private final JButton buttonDiv = new JButton("/");
	private final JButton openedBracket = new JButton("(");
	private final JButton closedBracket = new JButton(")");
	private final JButton reset = new JButton("C");
	private final JButton calc = new JButton("=");

	public JButton getCalc() {
		return calc;
	}

	public JButton getReset() {
		return reset;
	}

	public JTextField getRes() {
		return res;
	}

	public WinCalc(String title) {
		this.computationInProgress = false;
		this.isInfinity = false;
		URL url = this.getClass().getResource("icone.png");
		if (url == null) {
			System.err.println("can't find icon's app.");
		} else {
			setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		}

		this.setTitle(title);
		this.setSize(350, 240);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setType(Window.Type.NORMAL);

		// main panel
		pan = new JPanel();
		pan.setBackground(Color.white);
		pan.setPreferredSize(new Dimension(150, 60));

		// focus required to use keyboard
		pan.setFocusable(true);
		pan.requestFocusInWindow();

		// pan.setLayout(new GridBagLayout());
		pan.setLayout(new BorderLayout(5, 5));

		this.setContentPane(pan);
		res = new JTextField("");
		res.setPreferredSize(new Dimension(240, 45));
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 24);
		res.setFont(font);
		res.setEditable(false);
		res.setFocusable(false);
		// panel for 1..9 buttons
		grid = new JPanel();
		grid.setLayout(new GridLayout(4, 5, 5, 5));
		grid.setBackground(Color.WHITE);

		// configuration of the buttons
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		button0.addActionListener(this);
		button1.setBackground(Color.BLACK);
		button2.setBackground(Color.BLACK);
		button3.setBackground(Color.BLACK);
		button4.setBackground(Color.BLACK);
		button5.setBackground(Color.BLACK);
		button6.setBackground(Color.BLACK);
		button7.setBackground(Color.BLACK);
		button8.setBackground(Color.BLACK);
		button9.setBackground(Color.BLACK);
		button0.setBackground(Color.BLACK);
		buttonPoint.addActionListener(this);
		buttonPOW.addActionListener(this);
		buttonPlus.addActionListener(this);
		buttonMinus.addActionListener(this);
		buttonMult.addActionListener(this);
		buttonDiv.addActionListener(this);
		openedBracket.addActionListener(this);
		closedBracket.addActionListener(this);
		reset.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		reset.setBackground(Color.BLACK);
		calc.addActionListener(this);
		calc.setFont(new Font("Roman", Font.BOLD, 18));
		calc.setBackground(Color.BLACK);
		button1.setFocusable(false);
		button2.setFocusable(false);
		button3.setFocusable(false);
		button4.setFocusable(false);
		button5.setFocusable(false);
		button6.setFocusable(false);
		button7.setFocusable(false);
		button8.setFocusable(false);
		button9.setFocusable(false);
		button0.setFocusable(false);
		buttonPoint.setFocusable(false);
		buttonPOW.setFocusable(false);
		buttonPlus.setFocusable(false);
		buttonMinus.setFocusable(false);
		buttonMult.setFocusable(false);
		buttonDiv.setFocusable(false);
		openedBracket.setFocusable(false);
		closedBracket.setFocusable(false);
		reset.setFocusable(false);
		calc.setFocusable(false);
		pan.addKeyListener(this);

		// insertion of the 1..9 buttons
		grid.add(button1);
		grid.add(button2);
		grid.add(button3);
		grid.add(buttonPlus);
		grid.add(openedBracket);
		grid.add(button4);
		grid.add(button5);
		grid.add(button6);
		grid.add(buttonMinus);
		grid.add(closedBracket);
		grid.add(button7);
		grid.add(button8);
		grid.add(button9);
		grid.add(buttonMult);
		grid.add(reset);
		grid.add(button0);
		grid.add(buttonPoint);
		grid.add(buttonPOW);
		grid.add(buttonDiv);
		grid.add(calc);

		pan.add(res, BorderLayout.NORTH);
		pan.add(grid, BorderLayout.CENTER);

		this.setVisible(true);
	}

	public void update_msg(String msg) {
		res.setText(msg);
	}

	private boolean displayableButton(String name, JButton but) {
		if (but.getText().equals(name)) {
			System.out.println("[DEBUG] " + name);
			if ((((!computationInProgress) || !isNumberButton(name)))
					&& (!isInfinity)) {
				update_msg(res.getText() + name);
			} else {
				update_msg(name);
				isInfinity = false;
			}
			computationInProgress = false;
			return true;
		}
		return false;
	}

	private boolean isNumberButton(String name) {
		if (name.equals("0") || name.equals("1") || name.equals("2")
				|| name.equals("3") || name.equals("4") || name.equals("5")
				|| name.equals("6") || name.equals("7") || name.equals("8")
				|| name.equals("9")) {
			return true;
		}
		return false;
	}

	public boolean isComputationInProgress() {
		return computationInProgress;
	}

	public void setComputationInProgress(boolean computationInProgress) {
		this.computationInProgress = computationInProgress;
	}

	public void setInfinity(boolean isInfinity) {
		this.isInfinity = isInfinity;
	}

	public void actionPerformed(ActionEvent e) {
		JButton but = (JButton) e.getSource();

		if (displayableButton("0", but)) {
			return;
		}
		if (displayableButton("1", but)) {
			return;
		}
		if (displayableButton("2", but)) {
			return;
		}
		if (displayableButton("3", but)) {
			return;
		}
		if (displayableButton("4", but)) {
			return;
		}
		if (displayableButton("5", but)) {
			return;
		}
		if (displayableButton("6", but)) {
			return;
		}
		if (displayableButton("7", but)) {
			return;
		}
		if (displayableButton("8", but)) {
			return;
		}
		if (displayableButton("9", but)) {
			return;
		}
		if (displayableButton(".", but)) {
			return;
		}
		if (displayableButton("^", but)) {
			return;
		}
		if (displayableButton("+", but)) {
			return;
		}
		if (displayableButton("-", but)) {
			return;
		}
		if (displayableButton("\u00D7", but)) {
			return;
		}
		if (displayableButton("9", but)) {
			return;
		}
		if (displayableButton("/", but)) {
			return;
		}
		if (displayableButton("(", but)) {
			return;
		}
		if (displayableButton(")", but)) {
			return;
		}
	}

	private boolean click(JButton JButton, char c) {
		if (Character.toString(c).equals(JButton.getText())) {
			JButton.doClick();
			return true;
		}
		return false;
	}

	public void keyTyped(KeyEvent e) {
		if (click(button0, e.getKeyChar()))
			return;
		if (click(button1, e.getKeyChar()))
			return;
		if (click(button2, e.getKeyChar()))
			return;
		if (click(button3, e.getKeyChar()))
			return;
		if (click(button4, e.getKeyChar()))
			return;
		if (click(button5, e.getKeyChar()))
			return;
		if (click(button6, e.getKeyChar()))
			return;
		if (click(button7, e.getKeyChar()))
			return;
		if (click(button8, e.getKeyChar()))
			return;
		if (click(button9, e.getKeyChar()))
			return;
		if (click(buttonDiv, e.getKeyChar()))
			return;
		if (click(buttonPlus, e.getKeyChar()))
			return;
		if (click(buttonMinus, e.getKeyChar()))
			return;
		if (click(buttonPOW, e.getKeyChar()))
			return;
		if (click(buttonPoint, e.getKeyChar()))
			return;
		if (click(openedBracket, e.getKeyChar()))
			return;
		if (click(closedBracket, e.getKeyChar()))
			return;
		// special buttons
		if (e.getKeyChar() == '\n') {
			calc.doClick();
			return;
		}
		if (e.getKeyChar() == '*') {
			buttonMult.doClick();
			return;
		}
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			reset.doClick();
			return;
		}
		if (e.getKeyChar() == '\u0008') {
			if (!res.getText().equals("")) {
				res.setText(res.getText().substring(0,
						res.getText().length() - 1));
			}
			System.out.println("[DEBUG] backspace");
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
