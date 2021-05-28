package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {

	private class LocalSystem implements LSystem {

		/**
		 * Draws the requested shape
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			TurtleState initialState = new TurtleState(origin, new Vector2D(cos(angle * PI/180), sin(angle * PI/180)), Color.BLACK, unitLength * pow(unitLengthDegreeScaler, arg0));
			ctx.pushState(initialState);
			char[] seq = generate(arg0).toCharArray();
			for (char symbol : seq) {
				if (commands.get(symbol) != null) {
					commands.get(symbol).execute(ctx, arg1);
				}
			}			
		}

		/**
		 * Generates productions for the given level
		 * @param arg0 level
		 */
		@Override
		public String generate(int arg0) {
			char[] prod = axiom.toCharArray();
			String curr = axiom;
			int ind = 1;
			while (ind <= arg0) {
				curr = "";
				for (int i = 0; i < prod.length; i++) {
					if (productions.get(prod[i]) != null) {
						curr += productions.get(prod[i]);
					} else {
						curr += Character.toString(prod[i]);
					}
				}
				prod = curr.toCharArray();	
				ind++;
			}
			return curr;
		}
		
	}
	
	private Dictionary<Character, String> productions = new Dictionary<>();
	private Dictionary<Character, Command> commands = new Dictionary<>();
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";
	
	@Override
	public LSystem build() {
		return new LocalSystem();
	}

	/**
	 * Configures the orders from the given text
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String str : arg0) {
			String[] args = str.split("\\s+");
 			switch(args[0]) {
				case "origin": 		this.setOrigin(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
									break;
									
				case "angle":		this.setAngle(Double.parseDouble(args[1]));
									break;
									
				case "unitLength": 	this.setUnitLength(Double.parseDouble(args[1]));
									break;
									
				case "unitLengthDegreeScaler":
									double num = args.length == 4 ? Double.parseDouble(args[1]) / Double.parseDouble(args[3]) :
										Double.parseDouble(args[1]) / Double.parseDouble(args[2].substring(1, args[2].length()));
									this.setUnitLengthDegreeScaler(num);
									break;
									
				case "command": 	if (args.length == 4) {
										this.registerCommand(args[1].charAt(0), args[2] + " " + args[3]);
									} else {
										this.registerCommand(args[1].charAt(0), args[2]);
									}
									break;
									
				case "axiom": 		this.setAxiom(args[1]);
									break;
									
				case "production": 	this.registerProduction(args[1].charAt(0), args[2]);
									break;	
			}
		}
		return this;
	}

	/**
	 * Creates a new command from the given input and 
	 * puts in the commands dicionary
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] tmp = arg1.split("\\s+");
		Command com = null;
		switch (tmp[0]) {
			case "color":	com = new ColorCommand(Color.decode("#"+tmp[1])); 
							break;
			case "draw": 	com = new DrawCommand(Double.parseDouble(tmp[1]));
						 	break;
			case "pop":		com = new PopCommand();
							break;
			case "push":	com = new PushCommand();	
							break;
			case "rotate":	com = new RotateCommand(Double.parseDouble(tmp[1]));
						   	break;
			case "scale": 	com = new ScaleCommand(Double.parseDouble(tmp[1]));
						  	break;
			case "skip":	com = new SkipCommand(Double.parseDouble(tmp[1]));
							break;
			
		}
		commands.put(arg0, com);
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
	
	
}
