package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IOperation;
import bla.linearalgebra.IRingReducer;
import bla.linearalgebra.matrix.IMatrix;

public class MatrixMultiplicationOperation<T> implements IOperation<IMatrix<T>> {
	protected final IMatrix<T> neutralElement;

	public MatrixMultiplicationOperation(IMatrix<T> neutralElement) {
		this.neutralElement = neutralElement;
	}

	@Override
	public IMatrix<T> add(final IMatrix<T> left, final IMatrix<T> right) {
		if (left.nbColumns() != right.nbRows()) {
			throw new RuntimeException("Incompatible sizes: (" + left.nbRows() + "," + left.nbColumns() + ")+(" + right.nbRows() + "," + right.nbColumns()
					+ ")");
		}

		if (!left.getCoeffRing().equals(right.getCoeffRing())) {
			throw new RuntimeException("Incompatible coeff rings");
		}

		final IMatrix<T> output = new DenseMatrix<T>(left.getCoeffRing(), left.nbRows(), right.nbColumns());

		final int nbSum = left.nbColumns();

		output.accept(new IParallelMatrixVisitor() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				T currentCellValue = left.getCoeffRing().zero();

				for (int i = 0; i < nbSum; i++) {
					currentCellValue = left.getCoeffRing().add(currentCellValue,
							left.getCoeffRing().mul(left.getValue(rowIndex, i), right.getValue(i, columnIndex)));
				}

				if (left.getCoeffRing() instanceof IRingReducer) {
					currentCellValue = ((IRingReducer<T>) left.getCoeffRing()).reduce(currentCellValue);
				}

				output.setValue(rowIndex, columnIndex, currentCellValue);

				return true;
			}
		});

		return output;
	}

	protected T cleanBeforeWrite(T currentCellValue) {
		return currentCellValue;
	}

	@Override
	public IMatrix<T> getNeutralElement() {
		return neutralElement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((neutralElement == null) ? 0 : neutralElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrixMultiplicationOperation<?> other = (MatrixMultiplicationOperation<?>) obj;
		if (neutralElement == null) {
			if (other.neutralElement != null)
				return false;
		} else if (!neutralElement.equals(other.neutralElement))
			return false;
		return true;
	}

	@Override
	public IMatrix<T> makeFromint(int i) {
		throw new RuntimeException("Can not make a IMatrix from an int");
	}

}
