package epam.schepov.multithreading.validator;

import epam.schepov.multithreading.exception.matrix.OutOfBoundsMatrixException;
import epam.schepov.multithreading.matrix.Matrix;

public class MatrixBoundsValidator {

    public static void validateIndex(int index) throws OutOfBoundsMatrixException {
        int size = Matrix.getMatrixSize();
        if(index < 0 || index >= size){
            throw new OutOfBoundsMatrixException("Index is out of bounds(idx: " +
                    index + ", size: " + size + ")!");
        }
    }
}
