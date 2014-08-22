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
public class Parser {

	// GRAMMAR :
	//
	// EXPRESSION : TERM NEXTEXPRESSION [number, (]
	// | '-' TERM NEXTEXPRESSION [-]
	//
	// NEXTEXPRESSION : '+' TERM NEXTEXPRESSION [+]
	// | '-' TERM NEXTEXPRESSION [-]
	// | epsillon [),$]
	//
	// TERM : FACTOR NEXTTERM [number, (]
	//
	// NEXTTERM :
	// | '*' FACTOR NEXTTERM [*]
	// | '/' FACTOR NEXTTERM [/]
	// | epsilon [+,-,),$]
	//
	// FACTOR : PRIMARY NEXTFACTOR [number, (]
	//
	// NEXTFACTOR : epsilon [*,/,+,-,),$]
	// | '^' FACTOR [^]
	//
	// PRIMARY : NUMBER [number]
	// | '(' EXPRESSION ')' [(]

	private static volatile Parser parser = null;

	private Token currentLexeme;
	private Iterator<Token> indexLexeme;

	private Parser() {
	}

	public final static Parser getInstance() {
		if (Parser.parser == null) {
			synchronized (Parser.class) {
				if (Parser.parser == null) {
					Parser.parser = new Parser();
				}
			}
		}
		return Parser.parser;
	}

	private double expression() throws ParseException {
		double res = 0;
		if (match(tokenType.NUMBER) || match(tokenType.O_PAR)) {
			res = term();
			res += nextExpression();
		} else if (match(tokenType.SUB)) {
			nextToken();
			res = -term();
			res += nextExpression();
		} else if (match(null)) {
			return res;
		} else {
			throw new ParseException("expression failed.");
		}
		return res;
	}

	private double nextExpression() throws ParseException {
		double res = 0;
		if (match(tokenType.ADD)) {
			nextToken();
			res = term();
			res += nextExpression();
		} else if (match(tokenType.SUB)) {
			nextToken();
			res = -term();
			res += nextExpression();
		} else if (match(tokenType.C_PAR) || match(null)) {
			res = 0;
		} else {
			throw new ParseException("nextExpression failed.");
		}
		return res;
	}

	private double term() throws ParseException {
		double res = 0;
		if (match(tokenType.NUMBER) || match(tokenType.O_PAR)) {
			res = factor();
			res = nextTerm(res);
		} else {
			throw new ParseException("term failed.");
		}
		return res;
	}

	private double nextTerm(double arg) throws ParseException {
		double res = 0;
		if (match(tokenType.MULT)) {
			nextToken();
			res = arg * factor();
		} else if (match(tokenType.DIV)) {
			nextToken();
			res = arg / factor();
		} else if (match(null) || match(tokenType.ADD) || match(tokenType.SUB)
				|| match(tokenType.C_PAR)) {
			return arg;
		} else {
			throw new ParseException("nextTerm failed.");
		}
		return res;
	}

	private double factor() throws ParseException {
		double res = 0;
		if (match(tokenType.NUMBER) || match(tokenType.O_PAR)) {
			res = primary();
			res = nextFactor(res);
		} else {
			throw new ParseException("factor failed.");
		}
		return res;
	}

	private double nextFactor(double arg) throws ParseException {
		double res = 0;
		if (match(tokenType.MULT) || match(tokenType.DIV)
				|| match(tokenType.C_PAR) || match(tokenType.ADD)
				|| match(tokenType.SUB) || match(null)) {
			res = arg;
		} else if (match(tokenType.POW)) {
			nextToken();
			res = Math.pow(arg, factor());
		} else {
			throw new ParseException("nextFactor failed.");
		}
		return res;
	}

	private double primary() throws ParseException {
		double res = 0;
		if (match(tokenType.NUMBER)) {
			res = currentLexeme.getValue();
			nextToken();
		} else if (match(tokenType.O_PAR)) {
			nextToken();
			res = expression();
			if (!match(tokenType.C_PAR)){
				throw new ParseException("primary failed.");
			}
			nextToken();
		} else {
			throw new ParseException("primary failed.");
		}
		return res;
	}

	public double parse(LinkedList<Token> tokens) throws ParseException {
		double result = 0;
		indexLexeme = tokens.iterator();
		nextToken();
		result = expression();
		return result;
	}

	private boolean match(tokenType t) {
		if (t == null) {
			return currentLexeme == null;
		} else if (currentLexeme == null) {
			return t == null;
		}
		return currentLexeme.getToken() == t;
	}

	private void nextToken() {
		if (indexLexeme.hasNext()) {
			currentLexeme = indexLexeme.next();
		} else {
			currentLexeme = null;
		}
	}
}
