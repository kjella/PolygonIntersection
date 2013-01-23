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

				shouldPlot = shouldPlotPolygon( xPos, yPos );
				shouldPlotLine = shouldPlotLine( xPos, yPos, shouldPlotLine );

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

		long start = System.currentTimeMillis();
		
		PolygonIntersectionTool tool = new PolygonIntersectionTool( routeOfPolygons );
		
		for (Point point : lineAPoints) {
			boolean isWithinBoundary = tool.isPointInPolygon( point );
			System.out.println( "---------------------------------------------------------------------" );
			System.out.println( point.getX() + "," + point.getY() + " is within boundary? " + isWithinBoundary );
			System.out.println( "=====================================================================" );
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Execution took " + (end - start) + "ms");
	}

	private static boolean shouldPlotLine( int xPos, int yPos, boolean shouldPlotLine ) {
		for (Point point : lineAPoints) {
			if ( xPos == point.getX() && yPos == point.getY() ) {
				shouldPlotLine = true;
			}
		}
		return shouldPlotLine;
	}

	private static boolean shouldPlotPolygon( int xPos, int yPos ) {
		boolean shouldPlot = false;
		for (int polyCounter = 0; polyCounter < routeOfPolygons.length;) {
			if ( xPos == routeOfPolygons[polyCounter] && yPos == routeOfPolygons[polyCounter + 1] ) {
				shouldPlot = true;
			}
			polyCounter += 2;
		}
		return shouldPlot;
	}



}
