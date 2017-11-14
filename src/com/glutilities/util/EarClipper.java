package com.glutilities.util;

import java.util.ArrayList;
import java.util.List;

public class EarClipper {

	public static float[] clip(float[] points, int cutoff, float tolerance) {
		System.out.println("\n\n\nClipping...");
		List<Vertex2f> aPoints = new ArrayList<Vertex2f>();
		List<Vertex2f> oldPoints = new ArrayList<Vertex2f>();
		// List<Vertex2f> oldPointsCopy = new ArrayList<Vertex2f>();
		for (int i = 0; i < points.length; i += 3) {
			oldPoints.add(new Vertex2f(points[i], points[i + 1]));
			aPoints.add(new Vertex2f(points[i], points[i + 1]));
		}
		List<Vertex2f> newPoints = new ArrayList<Vertex2f>();

		int pindex = 0;
		int iterations = 0;
		int oldpointssize = oldPoints.size();
		while (oldpointssize > 3 && iterations < cutoff) {
			oldpointssize = oldPoints.size();
			// System.out.printf("Array size: %d, index: %d\n", oldpointssize, pindex);
			if (pindex < oldPoints.size() - 2) {
				Vertex2f[] triangle = findTriangle(pindex, oldPoints);
				if (triangle.length != 3) {
					pindex = 0;
					//System.out.println("Couldn't find a suitable triangle, restarting at 0");
					continue;
				}
				Vertex2f p1 = triangle[0];//oldPoints.get(pindex);
				Vertex2f p2 = triangle[1];//oldPoints.get(pindex + 1);
				Vertex2f p3 = triangle[2];//oldPoints.get(pindex + 2);
				//System.out.printf("Checking triangle %s %s %s\n", p1, p2, p3);
				// System.out.println(angle(p1, p2) + " - " + angle(p2, p3));
				// System.out.println(oldPoints.indexOf(p1));
				// Vertex2f midhyp = interpolate(p1, p3, 0.5f);
				// System.out.printf("%s %s %s - %s\n", p1, p2, p3, midhyp);
				boolean inside = true;
				for (float t = tolerance + 0.015f; t <= 1; t += tolerance) {
					Vertex2f inter = interpolate(p1, p3, t);
					inside = isInside(inter, aPoints);
					if (inside == false) {
						break;
					}
				}
				if (/*p1 != null && p2 != null && p3 != null && */inside) {
					//System.out.printf("%s %s %s - %s\n", p1, p2, p3, interpolate(p1, p3, 0.5f));
					newPoints.add(p1);
					newPoints.add(p2);
					newPoints.add(p3);
					oldPoints.set(pindex + 1, null);
					oldpointssize--;
					pindex += 2;
					//System.out.println("Triangle passed");
				} else {
					// System.out.println(p1 + " + " + p2 + " + " + p3 + " failed");
					pindex++;
				}
			} else {
				pindex = 0;
				for (int i = 0; i < oldPoints.size(); i++) {
					if (oldPoints.get(i) == null) {
						oldPoints.remove(i);
					}
				}
				//System.out.println("Completed a pass and the array size is now " + oldPoints.size());
				// break;
			}
			iterations++;
		}
		//System.out.println("Finished in " + iterations + " iterations");

		float[] result = new float[newPoints.size() * 3];
		for (int i = 0; i < newPoints.size() * 3; i += 3) {
			// System.out.println(newPoints.get(i / 3));
			result[i] = newPoints.get(i / 3).getX();
			result[i + 1] = newPoints.get(i / 3).getY();
		}

		return result;
	}
	
	private static Vertex2f[] findTriangle(int startIndex, List<Vertex2f> points) {
		List<Vertex2f> triangle = new ArrayList<Vertex2f>();
		int index = startIndex;
		while (triangle.size() < 3 && index < points.size()) {
			if (points.get(index) != null) {
				triangle.add(points.get(index));
			}
			index++;
		}
		return triangle.toArray(new Vertex2f[0]);
	}

	private static float angle(Vertex2f base, Vertex2f offset) {
		return (float) Math.atan2(offset.getY() - base.getY(), offset.getX() - base.getX());
	}

	private static boolean isInside(Vertex2f point, List<Vertex2f> points) {
		int intersections = 0;
		Vertex2f rayStart = new Vertex2f(-100, point.getY());
		Vertex2f rayEnd = new Vertex2f(1000, point.getY());
		for (int i = 0; i < points.size() - 1; i++) {
			Vertex2f start = points.get(i);
			Vertex2f end = points.get(i + 1);
			if (start != null && end != null && fakeIntersect(/*rayStart, rayEnd, */start, end, point.getX(), point.getY()) && start.getY() != end.getY() && end.getY() != point.getY()) {
				//System.out.printf("%s and %s intersect the line y=%.2f\n", start, end, point.getY());
				intersections++;
			} else {
				//System.out.printf("%s and %s don't intersect the line y=%.2f\n", start, end, point.getY());
			}
		}
		//System.out.printf("Point %s intersected %d times\n", point, intersections);
		return intersections % 2 == 1;
	}

	public static Vertex2f intersection(Vertex2f a1, Vertex2f a2, Vertex2f b1, Vertex2f b2) {
		float x1 = a1.getX();
		float x2 = a2.getX();
		float x3 = b1.getX();
		float x4 = b2.getX();
		float y1 = a1.getY();
		float y2 = a2.getY();
		float y3 = b1.getY();
		float y4 = b2.getY();
		if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
			return null;
		} else if (x1 == x2 && y3 == y4) {
			return new Vertex2f(x1, y3);
		} else {
			float val1 = x1 * y2 - y1 * x2;
			float val2 = x3 * y4 - y3 * x4;
			float divisor = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
			if (divisor == 0f) {
				return new Vertex2f(x1, y1);
			}
			return new Vertex2f((val1 * (x3 - x4) - (x1 - x2) * val2) / divisor,
					(val1 * (y3 - y4) - (y1 - y2) * val2) / divisor);
		}
	}

	public static boolean intersect(Vertex2f a1, Vertex2f a2, Vertex2f b1, Vertex2f b2) {
		Vertex2f inter = intersection(a1, a2, b1, b2);
		float alx = (float) Math.min(a1.getX(), a2.getX());
		float aux = (float) Math.max(a1.getX(), a2.getX());
		float aly = (float) Math.min(a1.getY(), a2.getY());
		float auy = (float) Math.max(a1.getY(), a2.getY());
		float blx = (float) Math.min(b1.getX(), b2.getX());
		float bux = (float) Math.max(b1.getX(), b2.getX());
		float bly = (float) Math.min(b1.getY(), b2.getY());
		float buy = (float) Math.max(b1.getY(), b2.getY());
		boolean onLine1 = inter.getX() >= alx && inter.getX() <= aux && inter.getY() >= aly && inter.getY() <= auy;
		boolean onLine2 = inter.getX() >= blx && inter.getX() <= bux && inter.getY() >= bly && inter.getY() <= buy;
		return onLine1 && onLine2;
	}
	
	public static boolean fakeIntersect(Vertex2f a1, Vertex2f a2, float xclip, float y) {
		
		return /*(a2.getY() != y && a1.getY() != y) &&  */((a1.getY() <= y && a2.getY() >= y) || (a1.getY() >= y && a2.getY() <= y)) && (a1.getX() >= xclip && a2.getX() >= xclip);
	}

	private static Vertex2f interpolate(Vertex2f p1, Vertex2f p2, float a) {
		return new Vertex2f(p1.getX() * a + p2.getX() * (1 - a), p1.getY() * a + p2.getY() * (1 - a));
	}

}
