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
package computer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JTextField;

import parser.LexException;
import parser.Lexer;
import parser.ParseException;
import parser.Parser;
import parser.Token;
import window.WinCalc;

/**
 * 
 * @author olivier
 */
public class Computer implements ActionListener {
	private double result;
	private final JTextField display;
	private LinkedList<Token> tokens;
	private final Parser parser;
	private final Lexer lexer;
	private DecimalFormat df;
	private WinCalc window;

	public Computer(WinCalc w) {
		this.result = 0;
		this.window = w;
		w.getCalc().addActionListener(this);
		w.getReset().addActionListener(this);
		this.display = w.getRes();
		this.lexer = Lexer.getInstance();
		this.parser = Parser.getInstance();
		df = new DecimalFormat();
		df.setMaximumFractionDigits(6);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		df.setDecimalSeparatorAlwaysShown(false);
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(
				"English")));
	}

	public void compute() throws LexException, ParseException {
		tokens = lexer.generateTokens(display.getText());
		if (tokens.size() > 0) {
			this.result = parser.parse(tokens);
		} else {
			this.result = Double.NaN;
		}
	}

	public void actionPerformed(ActionEvent e) {
		JButton but = (JButton) e.getSource();
		if (but.getText().equals("=")) {
			System.out.println("[DEBUG] calc : " + display.getText());
			try {
				compute();
				if (!Double.isNaN(result)) {
					if (Double.isInfinite(result)) {
						window.setInfinity(true);
					}
					display.setText(df.format(result));
					System.out.println("[DEBUG] result : " + result);
					window.setComputationInProgress(true);
				} else {
					this.result = 0;
					display.setText("");
				}
			} catch (LexException ex) {
				System.err.println("[ERROR] LEX FAILED");
				result = 0;
				display.setText("");
			} catch (ParseException ex) {
				System.err.println("[ERROR] PARSING FAILED");
				result = 0;
				display.setText("");
			}
		} else if (but.getText().equals("C")) {
			System.out.println("[DEBUG] reset");
			result = 0;
			display.setText("");
		}
	}

}
