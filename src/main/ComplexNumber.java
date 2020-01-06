package main;

/**
 * Created by Kyle on 6/3/2017.
 */
public class ComplexNumber {
    private double realComponent;
    private double imaginaryCoefficient;

    public ComplexNumber(double realComponent, double imaginaryCoefficient) {
        this.realComponent = realComponent;
        this.imaginaryCoefficient = imaginaryCoefficient;
    }

    public double getRealComponent() {
        return realComponent;
    }

    public double getImaginaryCoefficient() {
        return imaginaryCoefficient;
    }

    public int getMandelbrotValue() {
        if (hasLengthLongerThanEscapeValue()) {
            return 0;
        }
        if (Main.EXPONENT_IS_REAL) {
            return mandelbrotValueRealExponentHelper(1, new ComplexNumber(realComponent, imaginaryCoefficient), this);
        } else {
            return mandelbrotValueHelper(1, new ComplexNumber(realComponent, imaginaryCoefficient), this);
        }
    }

    public void square() {
        double first = realComponent * realComponent;
        double middle = realComponent * imaginaryCoefficient * 2;
        double last = -1 * imaginaryCoefficient * imaginaryCoefficient;
        realComponent = first + last;
        imaginaryCoefficient = middle;
    }

    private boolean hasLengthLongerThanEscapeValue() {
        return realComponent * realComponent + imaginaryCoefficient * imaginaryCoefficient > 10000;
    }

    private static int mandelbrotValueHelper(int counter, ComplexNumber z, ComplexNumber c) {
        z.toThePowerOf(Main.MANDELBROT_EXPONENT);
        z.add(c);
        if (z.hasLengthLongerThanEscapeValue() || counter == Main.MANDELBROT_ITERATIONS) {
            return counter;
        }
        return mandelbrotValueHelper(counter + 1, z, c);
    }

    private static int mandelbrotValueRealExponentHelper(int counter, ComplexNumber z, ComplexNumber c) {
        z.toThePowerOf(Main.MANDELBROT_EXPONENT_REAL);
        z.add(c);
        if (z.hasLengthLongerThanEscapeValue() || counter == Main.MANDELBROT_ITERATIONS) {
            return counter;
        }
        return mandelbrotValueRealExponentHelper(counter + 1, z, c);
    }

    public void add(ComplexNumber numberToAdd) {
        realComponent = realComponent + numberToAdd.realComponent;
        imaginaryCoefficient = imaginaryCoefficient + numberToAdd.imaginaryCoefficient;
    }

    public void setRealComponent(double realComponent) {
        this.realComponent = realComponent;
    }

    public void setImaginaryCoefficient(double imaginaryCoefficient) {
        this.imaginaryCoefficient = imaginaryCoefficient;
    }

    public ComplexNumber clone() {
        return new ComplexNumber(realComponent, imaginaryCoefficient);
    }

    /** Angle (Measured counterclockwise from the postive real axis) */
    public static double arg(ComplexNumber c) {
        double a = c.getRealComponent();
        double b = c.getImaginaryCoefficient();
        if (a == 0) {
            if (b == 0) {
                return 0;
            } else if (b > 0) {
                return Math.PI / 2;
            } else {
                return Math.PI * 1.5;
            }
        } else if (a > 0) {
            if (b >= 0) {
                double tanTheta = b / a;
                return Math.atan(tanTheta);
            } else {
                double tanTheta = -b / a;
                return Math.PI * 2 - Math.atan(tanTheta);
            }
        } else {
            if (b >= 0) {
                double tanTheta = b / -a;
                return Math.PI - Math.atan(tanTheta);
            } else {
                double tanTheta = b / a;
                return Math.PI + Math.atan(tanTheta);
            }
        }
    }

    public void toThePowerOf(ComplexNumber exp) {
        double a = realComponent;
        double b = imaginaryCoefficient;
        double c = exp.realComponent;
        double d = exp.imaginaryCoefficient;
        double aSquaredPlusBSquared = a*a + b*b;
        double argBase = arg(this);
        double trigExpression = c * argBase + .5 * d * Math.log(aSquaredPlusBSquared);
        double constant = Math.pow(aSquaredPlusBSquared, c/2) * Math.exp(-d * argBase);
        realComponent = constant * Math.cos(trigExpression);
        imaginaryCoefficient = constant * Math.sin(trigExpression);
    }

    public void toThePowerOf(double exp) {
        double a = realComponent;
        double b = imaginaryCoefficient;
        double c = exp;
        double aSquaredPlusBSquared = a*a + b*b;
        double argBase = arg(this);
        double trigExpression = c * argBase;
        double constant = Math.pow(aSquaredPlusBSquared, c/2);
        realComponent = constant * Math.cos(trigExpression);
        imaginaryCoefficient = constant * Math.sin(trigExpression);
    }

    public static ComplexNumber pow(ComplexNumber base, ComplexNumber exp) {
        double a = base.getRealComponent();
        double b = base.getImaginaryCoefficient();
        double c = exp.getRealComponent();
        double d = exp.getImaginaryCoefficient();
        double aSquaredPlusBSquared = a*a + b*b;
        double argBase = arg(base);
        double trigExpression = c * argBase + .5 * d * Math.log(aSquaredPlusBSquared);
        double constant = Math.pow(aSquaredPlusBSquared, c/2) * Math.exp(-d * argBase);
        return new ComplexNumber(constant * Math.cos(trigExpression), constant * Math.sin(trigExpression));
    }

    /** Distance from origin */
    public static double abs(ComplexNumber c) {
        return Math.sqrt(c.getRealComponent()*c.getRealComponent() + c.getImaginaryCoefficient()*c.getImaginaryCoefficient());
    }
}
