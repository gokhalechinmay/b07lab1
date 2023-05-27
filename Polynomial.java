import java.util.Arrays;

public class Polynomial {
    double[] coeff;

    public Polynomial() {
        coeff = new double[1];
        coeff[0] = 0;
    }

    public Polynomial(double setcoeff[]) {
        coeff = setcoeff.clone();
    }
    public Polynomial add(Polynomial new_poly) {
        int arr_len = new_poly.coeff.length;
        int coeff_len = coeff.length;
        int m = Math.max(arr_len, coeff_len);

        double[] ret_poly;
        ret_poly = new double[m];
        for (int i = 0; i < arr_len; i++) {
            ret_poly[i] = new_poly.coeff[i];

        }
        for (int i = 0; i < coeff_len; i++) {
            ret_poly[i] += coeff[i];
        }
        Polynomial p = new Polynomial(ret_poly);
        return p;
    }
    public double evaluate(double input_var) {
        double res = 0;
        int arr_len = coeff.length;
        if(arr_len == 1) {
            return coeff[0];
        }
        else{
            for(int i = 1; i < arr_len; i++){
            double power = Math.pow(input_var,i);
            res += coeff[i] * power;
                }
            }
        return res + coeff[0];
    }
    public boolean hasRoot(double input_var){
        if(evaluate(input_var) == 0){
            return true;
        }
        else{
            return false;
        }

    }

    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        Polynomial p1 = new Polynomial(c1);
        double [] c2 = {0,-2,0,0,-9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
    }
    }