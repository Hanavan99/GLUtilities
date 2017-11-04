package com.glutilities.text;

import java.awt.geom.PathIterator;

import org.lwjgl.opengl.GL11;

import com.glutilities.util.GLMath;

public class Glyph {

	private char character;
	private double[] xPoints;
	private double[] yPoints;
	private double width;
	private double height;
	private int[] flags;

	public Glyph(char character, double[] xPoints, double[] yPoints, double width, double height, int[] flags) {
		this.character = character;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.width = width;
		this.height = height;
		this.flags = flags;
	}
	
	public char getCharacter() {
		return character;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void draw(double x, double y) {
		double dx = 0;
		double dy = 0;
		double lastx = 0;
		double lasty = 0;
		int i = 0;
		GL11.glBegin(GL11.GL_LINES);
		while (i < xPoints.length) {
			switch (flags[i]) {
			case PathIterator.SEG_CLOSE:
				GL11.glVertex2d(lastx, lasty);
				GL11.glVertex2d(dx, dy);
				i++;
				break;
			case PathIterator.SEG_CUBICTO:
				GL11.glVertex2d(lastx, lasty);
				GL11.glVertex2d(lastx = xPoints[i + 2], lasty = yPoints[i + 2]);
				i += 3;
				break;
			case PathIterator.SEG_LINETO:
				GL11.glVertex2d(lastx, lasty);
				GL11.glVertex2d(lastx = xPoints[i], lasty = yPoints[i]);
				i++;
				break;
			case PathIterator.SEG_MOVETO:
				dx = lastx = xPoints[i];
				dy = lasty = yPoints[i];
				i++;
				break;
			case PathIterator.SEG_QUADTO:
				double _x = lastx;
				double _y = lasty;
				for (double t = 0; t <= 1; t += 0.1) {
					double[] b = GLMath.quadBezier(_x, _y, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1], t);
					GL11.glVertex2d(lastx, lasty);
					GL11.glVertex2d(lastx = b[0], lasty = b[1]);
				}
				i += 2;
				break;
			}
		}
		GL11.glEnd();
	}

}
