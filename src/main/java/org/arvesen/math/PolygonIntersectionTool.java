package org.arvesen.math;

import java.awt.Point;

public class PolygonIntersectionTool {
	private int[] routeOfPolygons;

	public PolygonIntersectionTool(int[] polygons) {
		routeOfPolygons = polygons;
	}

	public boolean isPointInPolygon( Point point ) {
		// Globals which should be set before calling this function:
		//
		// int polySides = how many corners the polygon has
		// float polyX[] = horizontal coordinates of corners
		// float polyY[] = vertical coordinates of corners
		// float x, y = point to be tested
		//
		// (Globals are used in this example for purposes of speed. Change as
		// desired.)
		//
		// The function will return YES if the point x,y is inside the polygon,
		// or
		// NO if it is not. If the point is exactly on the edge of the polygon,
		// then the function may return YES or NO.
		//
		// Note that division by zero is avoided because the division is
		// protected
		// by the "if" clause which surrounds it.

		int nrOfCoordinatesInPolygon = routeOfPolygons.length / 2;
		int i = 0;
		int j = nrOfCoordinatesInPolygon - 1;
		boolean oddNodes = false;

		int polyY[] = new int[nrOfCoordinatesInPolygon];
		int polyX[] = new int[nrOfCoordinatesInPolygon];
		int countY = 0;
		int countX = 0;
		for (i = 0; i < routeOfPolygons.length; i++) {
			if ( i % 2 == 0 ) {
				polyX[countX] = routeOfPolygons[i];
				countX++;
			}
			if ( i % 2 == 1 ) {
				polyY[countY] = routeOfPolygons[i];
				countY++;
			}
		}

		for (i = 0; i < nrOfCoordinatesInPolygon; i++) {

			if ( polyY[i] < point.getY() && polyY[j] >= point.getY() || polyY[j] < point.getY()
					&& polyY[i] >= point.getY() ) {

				if ( polyX[i] + ( point.getY() - polyY[i] ) / ( polyY[j] - polyY[i] ) * ( polyX[j] - polyX[i] ) < point
						.getX() ) {

					oddNodes = true;
				}
			}
			j = i;
		}
		return oddNodes;
	}
}
