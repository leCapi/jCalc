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

package calc;

import javax.swing.UIManager;

import window.WinCalc;

import computer.Computer;

/**
 * 
 * @author olivier
 */

public class Calc {

	public static void main(String[] args) {
		// Creation of the display and the computer
		setBestLookAndFeelAvailable();
		WinCalc w = new WinCalc("jCalc");
		new Computer(w);
	}

	public static void setBestLookAndFeelAvailable() {
		String system_lf = UIManager.getSystemLookAndFeelClassName()
				.toLowerCase();
		try {
			if (system_lf.contains("metal")) {
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} else {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}