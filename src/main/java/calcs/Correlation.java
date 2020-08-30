package calcs;

// JAVA Program to find correlation coefficient

class Correlation {

    // function that returns correlation coefficient.
    static float correlationCoefficient( int X[],
                                         int Y[], int n ) {

        int sum_X = 0, sum_Y = 0, sum_XY = 0;
        int squareSum_X = 0, squareSum_Y = 0;

        for ( int i = 0; i < n; i++ ) {
            // sum of elements of array X.
            sum_X = sum_X + X[ i ];

            // sum of elements of array Y.
            sum_Y = sum_Y + Y[ i ];

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + X[ i ] * Y[ i ];

            // sum of square of array elements.
            squareSum_X = squareSum_X + X[ i ] * X[ i ];
            squareSum_Y = squareSum_Y + Y[ i ] * Y[ i ];
        }

        // use formula for calculating correlation
        // coefficient.
        float corr = ( float ) ( n * sum_XY - sum_X * sum_Y ) /
                ( float ) ( Math.sqrt( ( n * squareSum_X -
                        sum_X * sum_X ) * ( n * squareSum_Y -
                        sum_Y * sum_Y ) ) );

        return corr;
    }
    
    // Driver function
    public static void main( String args[] ) {

        int x[] = { 1500, 1800, 2100, 2400, 2700, 4500, 2300, 500};
        int y[] = { 25, 25, 27, 31, 32, 65, 7, 8 };

        // Find the size of array.
        int n = x.length;

        // Function call to correlationCoefficient.
        System.out.printf( "%6f",
                correlationCoefficient( x, y, n ) );


    }
}

/*This code is contributed by Nikita Tiwari.*/
