package com.git.ganksquad.data;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public interface NativeMethod<T> {

	public T call(ReimuRuntime runtime) throws ReimuRuntimeException;

	public T call(ReimuRuntime runtime, String... args) throws ReimuRuntimeException;
}
