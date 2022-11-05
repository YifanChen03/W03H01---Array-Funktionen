package pgdp.arrayfun;

import java.math.BigInteger;
import java.util.Arrays;

public class ArrayFunctions {

    protected ArrayFunctions() {
        throw new IllegalStateException("Don't create objects of type 'ArrayFunctions'!");
    }

    public static void main(String[] args) {
        //example call
        //System.out.println(sumOfSquares(new int[]{})); //(checked) negative numbers, Long.MAX_VALUE, Integer.MAX_VALUE

        //System.out.println(Arrays.toString(zip(new int[]{1, 3, 5, 7, 9, 11, 13, 15, 16, 17, 18, 19}, new int[]{2, 4, 6, 8, 10, 12, 14})));

        //System.out.println(Arrays.toString(zipMany(new int[][]{{1, 4, 7, 10}, {2, 5, 8, 11, 13, 15, 17, 18, 19}, {3, 6, 9, 12, 14, 16}})));

        //System.out.println(Arrays.toString(filter(new int[]{3, 2, 6, 5, 1, 7, 7, 5, 9}, 0, 9)));

        rotate(new int[]{1, 2, 3, 4, 5}, 0);
    }

    /** Berechnet für das übergebene Array die Summe der Quadrate der Einträge.
     *  Gibt dabei einen Fehler aus und -1 zurück, wenn ein Overflow entsteht.
     *
     * @param array Ein beliebiges Integer-Array.
     * @return Die Summe der Quadrate, wenn diese in einen 'long' passt, -1 sonst.
     */
    public static long sumOfSquares(int[] array) {
        BigInteger sum = BigInteger.valueOf(0L);
        //loop to calculate sum of squares of every entry
        for (int i = 0; i < array.length; i++) {
            long factor_long = array[i]; //int to long to bigint
            BigInteger factor_bigint = BigInteger.valueOf(factor_long);
            sum = sum.add(factor_bigint.multiply(factor_bigint));
        }
        //check if sum is too large
        if (sum.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 1) {
            System.out.println("Overflow!");
            return -1L;
        } else {
            return sum.longValue();
        }
    }


    /** Methode, die zwei Arrays zu einem verbindet, indem sie abwechselnd Einträge des ersten und des zweiten Input-
     *  Arrays verwendet.
     *
     * @param a Ein beliebiges Integer-Array.
     * @param b Ein beliebiges Integer-Array.
     * @return 'a' und 'b' zusammengezipped.
     */
    public static int[] zip(int[] a, int[] b) {
        int[] z_ab = new int[a.length + b.length];
        int i_a = 0;
        int i_b = 0;
        //loop to zip arrays
        for (int i = 0; i < a.length + b.length; i++) {
            //if round is even add from a else add from b
            if (i % 2 == 0) {
                //if a is empty add from b and the other way round
                if (i_a < a.length) {
                    z_ab[i] = a[i_a];
                    i_a++;
                } else if (i_b < b.length) {
                        z_ab[i] = b[i_b];
                        i_b++;
                }
            } else {
                if (i_b < b.length) {
                    z_ab[i] = b[i_b];
                    i_b++;
                } else if (i_a < a.length) {
                        z_ab[i] = a[i_a];
                        i_a++;
                }
            }
        }
        return z_ab;
    }

    /** Methode, die eine beliebige Zahl an Arrays (dargestellt als Array von Arrays) zu einem einzigen Array verbindet,
     *  indem sie abwechselnd von jedem Array einen Eintrag nimmt, bis alle aufgebraucht sind.
     *
     * @param arrays Array von Integer-Arrays
     * @return Die Arrays in 'arrays' zusammengezipped
     */
    public static int[] zipMany(int[][] arrays) {
        int n = 0; //calculate sum of entries of all arrays
        int y = 0; // arrays[y][x] just for better visualization
        int x = 0;
        int e = 0; //entry in zip_array
        int max_length = 0; //longest arrays[y]
        int round = 0;
        int[] zip_array;
        //calculate amount of entries
        for (int i = 0; i < arrays.length; i++) {
            n = n + arrays[i].length;
            if (arrays[i].length > max_length) {
                max_length = arrays[i].length;
            }
        }
        zip_array = new int[n];
        //start zipping arrays
        while (round < max_length) {
            x = round;
            for (int i = 0; i < arrays.length; i++) {
                y = i;
                if (arrays[y].length > x) {
                    zip_array[e] = arrays[y][x];
                    e++;
                }
            }
            round++;
        }
        return zip_array;
    }

    /** Behält aus dem übergebenen Array nur die Einträge, die innerhalb der übergebenen Grenzen liegen.
     *  Gibt das Ergebnis als neues Array zurück.
     *
     * @param array Ein beliebiges Integer-Array
     * @param min Ein beliebiger Integer
     * @param max Ein beliebiger Integer
     * @return Das gefilterte Array
     */
    public static int[] filter(int[] array,int min,int max) {
        int[] filtered_array;
        int n_length = 0;
        int r = 0;
        String filtered_numbers = "";
        //check if maximum number is greater or equal to minimum number
        if (max >= min) {
            //get all allowed numbers in a String
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= min && array[i] <= max) {
                    filtered_numbers = filtered_numbers + "*" + array[i];
                }
            }
            //calculate how many numbers there are to determine length of filtered_array
            for (int i = 0; i < filtered_numbers.length(); i++) {
                if (filtered_numbers.charAt(i) == '*') {
                    n_length++;
                }
            }
            filtered_array = new int[n_length];
            //insert values into filtered_array
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= min && array[i] <= max) {
                    filtered_array[r] = array[i];
                    r++;
                }
            }
        } else {
            //return empty array if min greater than max
            return new int[0];
        }
        return filtered_array;
    }

    /** Rotiert das übergebene Array um die übergebene Anzahl an Schritten nach rechts.
     *  Das Array wird In-Place rotiert. Es gibt keine Rückgabe.
     *
     * @param array Ein beliebiges Integer-Array
     * @param amount Ein beliebiger Integer
     */
    public static void rotate(int[] array, int amount) {
        //tested with Integer.MIN % MAX, 0, 1, -1
        int save_value = 0;
        int n_at_index = 0;
        //check for empty arrays
        if (array.length > 0) {
            //convert negative amount into positive, mod is for simplification - see l.179f.
            amount = (amount % array.length + array.length);
            //avoid unnecessary rotations by making most possible rotations the length of array
            amount = amount % array.length;
            for (int r = 0; r < amount; r++) {
                //rotate by one
                save_value = array[0];
                for (int i = 0; i < array.length - 1; i++) {
                    if (i == 0) {
                        array[0] = array[array.length - 1];
                    }
                    if (i < array.length - 1) {
                        n_at_index = array[i + 1];
                        array[i + 1] = save_value;
                        save_value = n_at_index;
                    }
                }
            }
        }
    }

    /** Zählt die Anzahl an Vorkommen jeder Zahl im übergebenen Array, die in diesem mindestens einmal vorkommt.
     *  Die Rückgabe erfolgt über ein 2D-Array, bei dem jedes innere Array aus zwei Einträgen besteht: Einer Zahl,
     *  die im übergebenen Array vorkommt sowie der Anzahl an Vorkommen dieser.
     *  Für jede im übergebenen Array vorkommenden Zahl gibt es ein solches inneres Array.
     *  Diese tauchen im Rückgabewert in der gleichen Reihenfolge auf, in der die jeweils ersten Vorkommen der Zahlen
     *  im übergebenen Array auftauchen.
     *
     * @param array Ein beliebiges Integer-Array
     * @return Das Array mit den Vielfachheiten der einzelnen Zahlen, wiederum als Integer-Arrays mit zwei Einträgen dargestellt.
     */
    public static int[][] quantities(int[] array) {
        // TODO
        return null;
    }
}
