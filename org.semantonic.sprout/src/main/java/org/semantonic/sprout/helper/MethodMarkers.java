package org.semantonic.sprout.helper;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodMarkers {

	private final Class<?> target;
	private final Map<Class<? extends Annotation>, Method> markerMap;

	private MethodMarkers(Class<?> target, Map<Class<? extends Annotation>, Method> map) {
		checkNotNull(target);
		checkNotNull(map);

		this.target = target;
		this.markerMap = Collections.unmodifiableMap(map);
	}

	public Method resolveOrFail(Class<? extends Annotation> markerType) {
		checkNotNull(markerType, "markerType");

		final Method result = this.markerMap.get(markerType);

		if (result == null) {
			throw new IllegalStateException(String.format("unable to resolve method marker '%s' in '%s'",
					markerType, this.target));
		}

		return result;
	}

	public Map<Class<? extends Annotation>, Method> map() {
		return this.markerMap;
	}

	public static MethodMarkers of(Class<?> cls) {
		final Map<Class<? extends Annotation>, Method> result = new HashMap<>();

		for (Method m : cls.getMethods()) {
			final Optional<Annotation> marker = extractMarker(m);
			if (marker.isPresent()) {
				Class<? extends Annotation> markerType = marker.get().annotationType();
				if (result.containsKey(markerType)) {
					throw new IllegalStateException(String.format("method marker '%s' is used more than once in '%s'",
							marker.get(), cls));
				}
				result.put(markerType, m);
			}
		}

		return new MethodMarkers(cls, result);
	}

	private static Optional<Annotation> extractMarker(Method m) {
		final Set<Annotation> markers = Arrays.asList(m.getAnnotations()).stream()
				.filter(a -> a.annotationType().isAnnotationPresent(MethodMarker.class)).collect(Collectors.toSet());

		switch (markers.size()) {
		case 0:
			return Optional.empty();
		case 1:
			return Optional.of(markers.iterator().next());
		default:
			throw new IllegalStateException(String.format("multiple markers found on method '%s': %d %s", m,
					markers.size(), markers));
		}
	}

}
