import java.io.*;
import java.util.Arrays;

public class Polynomial{
	
	public double[] coeff;
	public int[] exp;

	public Polynomial(){
		this.coeff = new double[] {0};
		this.exp = new int[] {0};
	}

	public Polynomial(double[] coeff, int[] powers){
		this.coeff = coeff;
		this.exp = powers;
	}

	public Polynomial(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String polynomial = reader.readLine();
		reader.close();

		String[] terms;
		terms = polynomial.split("(?=[+-])");

		double[] newCoeffs = new double[terms.length];
		int[] newPowers = new int[terms.length];

		for (int i = 0; i < terms.length; i++) {
			String term = terms[i];
			int split = term.indexOf('x');

			if (split != -1) {
				newCoeffs[i] = Double.parseDouble(term.substring(0, split));
				newPowers[i] = Integer.parseInt(term.substring(split + 1));
			} else {
				newCoeffs[i] = Double.parseDouble(term);
				newPowers[i] = 0;
			}
		}

		this.coeff = newCoeffs;
		this.exp = newPowers;
	}

	public Polynomial add(Polynomial p){
		int length = this.coeff.length + p.coeff.length;
		double[] newCoeff = new double[length];
		int[] newExp = new int[length];

		int i = 0;
		int j = 0;
		int k = 0;

		while (i < coeff.length && j < p.coeff.length) {
			if (this.exp[i] < p.exp[j]) {
				newCoeff[k] = this.coeff[i];
				newExp[k] = this.exp[i];
				i++;
			} else if (this.exp[i] > p.exp[j]) {
				newCoeff[k] = p.coeff[j];
				newExp[k] = p.exp[j];
				j++;
			} else {
				newCoeff[k] = this.coeff[i] + p.coeff[j];
				newExp[k] = this.exp[i];
				i++;
				j++;
			}
			k++;
		}

		while (i < coeff.length) {
			newCoeff[k] = this.coeff[i];
			newExp[k] = this.exp[i];
			i++;
			k++;
		}

		while (j < p.coeff.length) {
			newCoeff[k] = p.coeff[j];
			newExp[k] = p.exp[j];
			j++;
			k++;
		}

		return returnCut(newCoeff, newExp, length);
	}
	
	public double evaluate(double x) {
		double res = 0;
		for(int i = 0; i < this.coeff.length; i++) {
			res += this.coeff[i] * (Math.pow(x, this.exp[i]));
		}
		return res;
	}
	
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0);
	}

	public Polynomial multiply(Polynomial p) {
		int a = this.exp.length;
		int b = p.exp.length;
		int newLength = a + b + 1;

		double[] newCoeff = new double[newLength];
		int[] newExp = new int[newLength];

		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				int newExponent = this.exp[i] + p.exp[j];
				newCoeff[newExponent] += (this.coeff[i] * p.coeff[j]);
				newExp[newExponent] = newExponent;
			}
		}

		return returnCut(newCoeff, newExp, newLength);
	}

	public Polynomial returnCut(double[] coeffs, int[] powers, int length){
		int retLength = 0;
		double[] retCoeff = new double[length];
		int[] retExp = new int[length];
		for (int i = 0; i < length; i++) {
			if (coeffs[i] != 0) {
				retCoeff[retLength] = coeffs[i];
				retExp[retLength] = powers[i];
				retLength++;
			}
		}

		retCoeff = Arrays.copyOf(retCoeff, retLength);
		retExp = Arrays.copyOf(retExp, retLength);

		return new Polynomial(retCoeff, retExp);
	}

	public void saveToFile(String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

		boolean isFirstTerm = true;

		for (int i = 0; i < this.coeff.length; i++) {
			if (this.coeff[i] != 0) {
				if (!isFirstTerm) {
					writer.write(this.coeff[i] > 0 ? "+" : "-");
				} else {
					isFirstTerm = false;
				}

				writer.write(Double.toString(Math.abs(this.coeff[i])));

				if (this.exp[i] > 0) {
					writer.write("x" + this.exp[i]);
				}
			}
		}

		writer.close();
	}
}
