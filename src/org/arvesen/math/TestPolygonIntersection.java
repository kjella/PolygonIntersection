package org.arvesen.math;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TestPolygonIntersection {

	// X is horizontal
	// Y is vertical
	// Format: Pairs of X,Y
	private static int[] routeOfPolygons = new int[] { 2, 2, 8, 3, 9, 4, 6, 5, 6, 6, 5, 6, 3, 6, 2, 6, 2, 2 };
	private static List<Point> lineAPoints = new ArrayList<Point>( 3 );

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		// Initialize line
		lineAPoints.add( new Point( 1, 1 ) );
		lineAPoints.add( new Point( 8, 3 ) );
		lineAPoints.add( new Point( 9, 9 ) );

		int stopX = 10;
		int stopY = 20;

		System.out.println( "Printing out the grid!... X indicates points in PolyLine" );
		System.out
				.println( "=========================================================================================================" );
		for (int xPos = 0; xPos < stopX; xPos++) {
			System.out.print( xPos + "x" );

			for (int yPos = 1; yPos < stopY; yPos++) {

				if ( xPos < 1 ) {
					System.out.print( "|" + yPos + "y" );
				} else {
					System.out.print( "|" );
				}

				// Search if polygon is inside this position [ xCounter x
				// yCounter ]
				int pointer = 0;
				boolean shouldPlot = false;
				boolean shouldPlotLine = false;

				shouldPlot = plotPolygon( xPos, yPos );
				shouldPlotLine = plotLine( xPos, yPos, shouldPlotLine );

				if ( shouldPlotLine ) {
					System.out.print( " *" );
				} else {
					System.out.print( "  " );
				}

				if ( shouldPlot ) {
					System.out.print( " X" );
				} else {
					if ( xPos >= 1 ) {
						if ( yPos > 9 ) {
							System.out.print( "   " );
						} else {
							System.out.print( "  " );
						}
					}
				}
			}
			System.out.print( '\n' );
		}

		System.out
				.println( "=========================================================================================================" );

		for (Point point : lineAPoints) {
			boolean isWithinBoundary = pointInPolygon( point );
			System.out.println( "---------------------------------------------------------------------" );
			System.out.println( "Within boundary? " + isWithinBoundary );
			System.out.println( "=====================================================================" );
		}
	}

	private static boolean plotLine( int xPos, int yPos, boolean shouldPlotLine ) {
		for (Point point : lineAPoints) {
			if ( xPos == point.getX() && yPos == point.getY() ) {
				shouldPlotLine = true;
			}
		}
		return shouldPlotLine;
	}

	private static boolean plotPolygon( int xPos, int yPos ) {
		boolean shouldPlot = false;
		for (int polyCounter = 0; polyCounter < routeOfPolygons.length;) {
			if ( xPos == routeOfPolygons[polyCounter] && yPos == routeOfPolygons[polyCounter + 1] ) {
				shouldPlot = true;
			}
			polyCounter += 2;
		}
		return shouldPlot;
	}

	private static boolean pointInPolygon( Point point ) {
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
		System.out.println( "Number of coordinates in polygons is " + nrOfCoordinatesInPolygon );
		System.out.println( "j = " + j );
		boolean oddNodes = false;

		int polyY[] = new int[nrOfCoordinatesInPolygon];
		int polyX[] = new int[nrOfCoordinatesInPolygon];
		int countY = 0;
		int countX = 0;
		for (i = 0; i < routeOfPolygons.length; i++) {
			if ( i % 2 == 0 ) {
				polyX[countX] = routeOfPolygons[i];
				// System.out.print( "x = " + polyX[countX] );
				countX++;
			}
			if ( i % 2 == 1 ) {
				polyY[countY] = routeOfPolygons[i];
				// System.out.print( ", y = " + polyY[countY] + "\n" );
				countY++;
			}
		}

		System.out.println( "Number of horizontal points " + polyX.length );
		System.out.println( "Number of vertical points " + polyY.length );

		for (i = 0; i < nrOfCoordinatesInPolygon; i++) {
			System.out.println( "Calc: " + polyY[i] + " < " + point.getY() + " && " + polyY[j] + " >= " + point.getY()
					+ " || " + polyY[j] + " < " + point.getY() );

			if ( polyY[i] < point.getY() && polyY[j] >= point.getY() || polyY[j] < point.getY()
					&& polyY[i] >= point.getY() ) {

				System.out.println( point.getY() + " is within vertical plane!" );

				System.out.println( polyX[i] + ( point.getY() - polyY[i] ) / ( polyY[j] - polyY[i] )
						* ( polyX[j] - polyX[i] ) );

				if ( polyX[i] + ( point.getY() - polyY[i] ) / ( polyY[j] - polyY[i] ) * ( polyX[j] - polyX[i] ) < point
						.getX() ) {

					System.out.println( point.getX() + " is within horizontal plane!" );
					oddNodes = true;
				} else {
					System.out.println( point.getX() + " is not within horizontal plane!" );
				}
			} else {
				System.out.println( point.getY() + " is not within vertical plane!" );
			}
			j = i;
		}
		return oddNodes;
	}

}
