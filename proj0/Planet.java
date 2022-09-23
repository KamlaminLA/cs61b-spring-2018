public class Planet {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;

	}

	/* here we ceate an indentical planet */
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	/*we want to calculate the distance between the current planet with the planet in this calcDistance
	method like samh.calcDistance(rocinante); so when we invoked xxPos, that is the current planet variable,
	p.xxPos will  be the planet in this method*/

	public double calcDistance(Planet p) {
		double r = Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos) + (yyPos - p.yyPos)*(yyPos - p.yyPos));
		return r;

	}
	/* we must have 2 planet to calculate any data */
	public double calcForceExertedBy(Planet p) {
		double r = calcDistance(p);
		double f = G * p.mass * mass / (r * r);
		return f;
	}

	public double calcForceExertedByX(Planet p) {
		double f = calcForceExertedBy(p);
		double r = calcDistance(p);
		double fx = f * (p.xxPos - xxPos) / r;

		return fx;
	}

	public double calcForceExertedByY(Planet p) {
		double f = calcForceExertedBy(p);
		double r = calcDistance(p);
		double fy = f * (p.yyPos - yyPos) / r;

		return fy;
	}
	
	/* here we need to use this.equals(allPlanets[i]) to skip itsel, since
	we can not make force on ourself, and then use for loop to go through the
	rest elements */

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double totalForce = 0;
		for (int i = 0; i < allPlanets.length; i ++) {
			if (this.equals(allPlanets[i])) {
				continue;
			}
			totalForce += calcForceExertedByX(allPlanets[i]);
		}
		return totalForce;
	}
	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double totalForce = 0;
		for (int i = 0; i < allPlanets.length; i ++) {
			if (this.equals(allPlanets[i])) {
				continue;
			}
			totalForce += calcForceExertedByY(allPlanets[i]);
		}
		return totalForce;
	}
	
	/* here we want to use the net force fx and fy to cacluate the anet, x and anet y */ 

	public void update(double dt, double fx, double fy) {
		double anetX = fx / mass;
		double anetY = fy / mass;

		double vnewX = xxVel + dt * anetX;
		double vnewY = yyVel + dt * anetY;

		double pnewX = xxPos + dt * vnewX;
		double pnewY = yyPos + dt * vnewY;

		xxPos = pnewX;
		yyPos = pnewY;

		xxVel = vnewX;
		yyVel = vnewY;
	}

	/* make the planet able to draw itself in a appopriate position and return nothing,
	therfore we need a Standard Drawing command StdDraw.picture */

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}