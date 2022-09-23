public class NBody {

	/* Every time you call a read method from the In class,it reads 
	the next thing from the file, assuming it is of the specified type. */
	
	public static double readRadius(String textFile){
		In in = new In(textFile);

		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		return secondItemInFile;

	}

	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int num = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[num];

		for(int i = 0; i < num; i++){
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();

			planets[i] = new Planet(xP, yP, xV, yV, m, img);
		}
		return planets;

	}
	/* In Java args contains the supplied command-line arguments as an array of
	 String objects. */

	 public static void main(String[] args) {
	 	double T = Double.parseDouble(args[0]);
	 	double dt = Double.parseDouble(args[1]);
	 	String filename = args[2];

	 	double radius = readRadius(filename);
	 	Planet[] planets = readPlanets(filename);
	 	int num = planets.length;

	 	/* set the scale of the coordinate system */
	 	/** enable double buffer can store all the thing you draw offscreen, 
	 	and display when you can StdDraw.show() */
	 	StdDraw.enableDoubleBuffering(); 
	 	StdDraw.setScale(-radius, radius);
	 	StdDraw.picture(0, 0, "images/starfield.jpg");

	 	for(Planet planet : planets) {
	 		planet.draw();

	 	}

	 	double t = 0;
	 	while(t <= T){
	 		/** create two net force array 
	 		 */
	 		double[] xForce = new double[num];
	 		double[] yForce = new double[num];

	 		/** store the force into the array of each planet
	 		 */
	 		for(int i = 0; i < num; i++){
	 			xForce[i] = planets[i].calcNetForceExertedByX(planets);
	 			yForce[i] = planets[i].calcNetForceExertedByY(planets);
	 		}

	 		/** update all the postion of each planet
	 		 */ 
	 		for(int i = 0; i < num; i++){
	 			planets[i].update(dt, xForce[i], yForce[i]);	 			
	 		}
	 		// draw the backgroud.

	 		StdDraw.picture(0, 0, "images/starfield.jpg");
	 		
	 		// draw all the planet.
	 		/* draw method is written in the planet class,
	 		which invoked StdDraw.picture(x, y, fileName)
	 		*/
	 		for(Planet planet : planets){
	 			planet.draw();
	 		}

	 		StdDraw.show();
	 		StdDraw.pause(10);
	 		t += dt;

	 	}
	 		/* when the time is over, we should
	 		print the state of the universe */
	 		StdOut.printf("%d\n", planets.length);
			StdOut.printf("%.2e\n", radius);
			for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  		planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  		planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    	}
	 		
	} 	

}