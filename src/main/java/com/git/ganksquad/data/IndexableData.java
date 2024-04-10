package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.CannotIndexException;

public interface IndexableData<T extends Data> {

	public T get(IndexKeyData index) throws CannotIndexException ;

	public void set(IndexKeyData index, T value) throws CannotIndexException;
}
