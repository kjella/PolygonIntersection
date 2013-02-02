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
	private static List<Point> lineBPoints = new ArrayList<Point>( 3 );
	private static List<Point> currentLine = new ArrayList<Point>( 3 );
	private static int NR_OF_ITERATIONS = 10000000;
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		// Initialize line
		lineAPoints.add( new Point( 1, 1 ) );
		lineAPoints.add( new Point( 8, 3 ) );
		lineAPoints.add( new Point( 9, 9 ) );

		lineBPoints.add( new Point( 1, 1 ) );
		lineBPoints.add( new Point( 5, 2 ) );
		lineBPoints.add( new Point( 8, 2 ) );

		plotChart();
		
		System.out.println( "Iterating " + NR_OF_ITERATIONS + " times to see if two lines are crossing a polyline" );		

		long start = System.currentTimeMillis();
		for (int i = 0; i < NR_OF_ITERATIONS; i++) {
			currentLine.clear();
			if ( i % 2 == 1 ) {
				currentLine.addAll( lineAPoints );
			} else {
				currentLine.addAll( lineBPoints );
			}
			runTest();
		}
		long end = System.currentTimeMillis();

		System.out.println( "Total execution took " + ( end - start ) + "ms" );

	}

	private static void runTest() {

//		long start = System.currentTimeMillis();

		PolygonIntersectionTool tool = new PolygonIntersectionTool( routeOfPolygons );

		for (Point point : currentLine) {
			boolean isWithinBoundary = tool.isPointInPolygon( point );
			// System.out.println(
			// "---------------------------------------------------------------------"
			// );
//			System.out.println( point.getX() + "," + point.getY() + " is within boundary? " + isWithinBoundary );
			// System.out.println(
			// "====================================================================="
			// );
		}

//		long end = System.currentTimeMillis();

//		System.out.println( "Execution took " + ( end - start ) + "ms" );
	}

	private static void plotChart() {
		char plotPointInShape = '*';
		char plotPointInLine = '\'';

		int stopX = 10;
		int stopY = 20;

		System.out.println( "Printing out the grid!... " );
		System.out.println( plotPointInShape + " indicates point in PolyShape" );
		System.out.println( plotPointInLine + " indicates point in PolyLine" );
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
					System.out.print( " " + plotPointInLine );
				} else {
					System.out.print( "  " );
				}

				if ( shouldPlot ) {
					System.out.print( " " + plotPointInShape );
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
