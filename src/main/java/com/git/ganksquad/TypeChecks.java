package com.git.ganksquad;

import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;

public class TypeChecks {

	// https://stackoverflow.com/a/18606772
	public static Set<Class<?>> getSuperclasses(Class<?> clazz) {

		final Set<Class<?>> result = new LinkedHashSet<>();

		final Queue<Class<?>> queue = new ArrayDeque<>();

		queue.add(clazz);

		while (!queue.isEmpty()) {

			Class<?> c = queue.remove();

			if (result.add(c)) {

				Class<?> sup = c.getSuperclass();

				if (sup != null) queue.add(sup);

				queue.addAll(Arrays.asList(c.getInterfaces()));
			}
		}
		return result;
	}


	public static Set<Class<?>> commonSuperclasses(Iterable<Class<?>> classes) {

	    Iterator<Class<?>> it = classes.iterator();

	    if (!it.hasNext()) {
	        return Collections.emptySet();
	    }

	    // begin with set from first hierarchy
	    Set<Class<?>> result = getSuperclasses(it.next());

	    // remove non-superclasses of remaining
	    while (it.hasNext()) {

	        Class<?> c = it.next();

	        Iterator<Class<?>> resultIt = result.iterator();

	        while (resultIt.hasNext()) {

	            Class<?> sup = resultIt.next();

	            if (!sup.isAssignableFrom(c)) {
	                resultIt.remove();
	            }
	        }
	    }

	    return result;
	}
	
	public static boolean reimuTypeAssignable(Iterable<Class<?>> classes) {
		
		Set<Class<?>> t = commonSuperclasses(classes);
		
		if(t.size() == 0) {
			return false;
		}
		

		for(Class<?> c : t) {
			
			System.out.println(c);

			return (c != ReimuType.class);
		}

		return false;
	}
	
	public static void main(String args[]) {

		List<Class<?>> a = Arrays.asList(
//				PrimitiveType.BOOLEAN.getClass(), 
//				PrimitiveType.INT.getClass()
				
			AggregateType.UNKNOWN_ARRAY_TYPE.getClass()
	,			AggregateType.STRING_TYPE.getClass()
				);

//		commonSuperclasses(a).forEach(x -> System.out.println(x));;
//		lowestClasses(a).forEach(x -> System.out.println(x));;
		System.out.println(reimuTypeAssignable(a));
	}


}
