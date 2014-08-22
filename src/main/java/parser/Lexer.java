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

package parser;

import java.util.Iterator;
import java.util.LinkedList;

import parser.Token.tokenType;

/**
 * 
 * @author olivier
 */
public class Lexer {
	private static volatile Lexer lex = null;

	private LinkedList<Token> fifoTokens;
	private String expression;

	private Lexer() {
		this.fifoTokens = new LinkedList<Token>();
	}

	public final static Lexer getInstance() {
		if (Lexer.lex == null) {
			synchronized (Lexer.class) {
				if (Lexer.lex == null) {
					Lexer.lex = new Lexer();
				}
			}
		}
		return Lexer.lex;
	}

	public LinkedList<Token> generateTokens(String expression)
			throws LexException {
		this.expression = expression;
		this.fifoTokens.clear();
		Token buffer = new Token();
		String bufferString;
		int index = 0;
		boolean coma = false;

		while (index < expression.length()) {
			if (expression.charAt(index) == '\u00D7') {
				// MULT
				parseOperations(buffer, tokenType.MULT, index);
				buffer = new Token();
			} else if (expression.charAt(index) == '/') {
				// DIV
				parseOperations(buffer, tokenType.DIV, index);
				buffer = new Token();
			} else if (expression.charAt(index) == '+') {
				// ADD
				parseOperations(buffer, tokenType.ADD, index);
				buffer = new Token();
			} else if (expression.charAt(index) == '-') {
				// SUB
				parseOperations(buffer, tokenType.SUB, index);
				buffer = new Token();
			} else if (expression.charAt(index) == '^') {
				// POW
				parseOperations(buffer, tokenType.POW, index);
				buffer = new Token();
			} else if (expression.charAt(index) == '(') {
				// O_PAR
				buffer.setToken(tokenType.O_PAR);
				buffer.setsValue(Character.toString(expression.charAt(index)));
				fifoTokens.add(buffer);
				buffer = new Token();
			} else if (expression.charAt(index) == ')') {
				// C_PAR
				buffer.setToken(tokenType.C_PAR);
				buffer.setsValue(Character.toString(expression.charAt(index)));
				fifoTokens.add(buffer);
				buffer = new Token();
			} else if ('0' <= expression.charAt(index)
					&& expression.charAt(index) <= '9') {
				// Numbers
				buffer.setToken(tokenType.NUMBER);
				bufferString = Character.toString(expression.charAt(index));
				index++;
				while (index < expression.length()
						&& (('0' <= expression.charAt(index) && expression
								.charAt(index) <= '9') || (expression
								.charAt(index) == '.' && !coma))) {
					if (expression.charAt(index) == '.' && coma) {
						throw new LexException();
					}
					if (expression.charAt(index) == '.') {
						coma = true;
					}
					bufferString = bufferString
							+ Character.toString(expression.charAt(index));
					index++;
				}
				// Number ends by a coma
				if (expression.charAt(index - 1) == '.') {
					throw new LexException();
				}
				buffer.setToken(tokenType.NUMBER);
				buffer.setsValue(bufferString);
				buffer.setValue(Double.parseDouble((bufferString)));
				fifoTokens.add(buffer);
				buffer = new Token();
				coma = false;
			}
			if (!(fifoTokens.getLast().getToken() == tokenType.NUMBER)) {
				index++;
			}
		}
		return fifoTokens;
	}

	public LinkedList<Token> getFifo_token() {
		return fifoTokens;
	}

	public LinkedList<Token> getFifoToken() {
		return fifoTokens;
	}

	@Override
	public String toString() {
		Iterator<Token> i = fifoTokens.iterator();
		String res = new String();
		Token t;
		while (i.hasNext()) {
			t = i.next();
			res = res + t.getToken().toString() + " : " + t.getsValue() + "\n";
		}
		return res;
	}

	private void parseOperations(Token buffer, tokenType type, int index) {
		buffer.setToken(type);
		buffer.setsValue(Character.toString(expression.charAt(index)));
		fifoTokens.add(buffer);
		buffer = new Token();
	}
}
