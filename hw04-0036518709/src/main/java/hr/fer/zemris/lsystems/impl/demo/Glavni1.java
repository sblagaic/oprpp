package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni1 {
	
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
		}
	
//	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
//		return provider.createLSystemBuilder()
//		.registerCommand('F', "draw 1")
//		.registerCommand('+', "rotate 25.7")
//		.registerCommand('-', "rotate -25.7")
//		.setOrigin(0.05, 0.4)
//		.setAngle(0)
//		.setUnitLength(0.9)
//		.setUnitLengthDegreeScaler(1.0/3.0)
//		.registerProduction('F', "F+F--F+F")
//		.setAxiom("F")
//		.build();
//		}
	
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.05 0.4",
		"angle 0",
		"unitLength 0.9",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"command F draw 1",
		"command + rotate 60",
		"command - rotate -60",
		"",
		"axiom F",
		"",
		"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
		}


	
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
}
