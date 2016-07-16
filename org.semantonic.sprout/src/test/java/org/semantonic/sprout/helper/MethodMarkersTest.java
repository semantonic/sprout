package org.semantonic.sprout.helper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Assert;
import org.junit.Test;
import org.semantonic.sprout.helper.MethodMarker;
import org.semantonic.sprout.helper.MethodMarkers;

public class MethodMarkersTest {
	
	@Test
	public void shouldExtractMarkers() throws NoSuchMethodException {
		final MethodMarkers markers = MethodMarkers.of(ClassOK_TwoMarkers.class);
		
		Assert.assertNotNull(markers);
		Assert.assertNotNull(markers.map());
		Assert.assertEquals(2, markers.map().size());
		
		Assert.assertEquals(ClassOK_TwoMarkers.class.getMethod("markedMethod1"), markers.resolveOrFail(Marker1.class));
		Assert.assertEquals(ClassOK_TwoMarkers.class.getMethod("markedMethod2"), markers.resolveOrFail(Marker2.class));
	}
	
	@Test
	public void shouldBeFineWithoutMarkers() {
		final MethodMarkers markers = MethodMarkers.of(ClassOK_NoMarkers.class);
		
		Assert.assertNotNull(markers);
		Assert.assertNotNull(markers.map());
		Assert.assertTrue(markers.map().isEmpty());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldReturnUnmodifiableCollection() {
		final MethodMarkers markers = MethodMarkers.of(ClassOK_TwoMarkers.class);
		
		markers.map().clear();
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldAbortOnMultipleMarkersPerMethod() {
		MethodMarkers.of(ClassNOK_MultiMarkers.class);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldAbortOnMarkerReUse() {
		MethodMarkers.of(ClassNOK_MarkerReUse.class);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailIfMarkerNotResolvable() {
		MethodMarkers.of(ClassOK_OneMarker.class).resolveOrFail(Marker2.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailOnResolveWithNull() {
		MethodMarkers.of(ClassOK_TwoMarkers.class).resolveOrFail(null);
	}
	
	// -----------------
	@MethodMarker
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Marker1 {}
	
	@MethodMarker
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Marker2 {}

	public static class ClassOK_OneMarker {		
		public void unmarkedMethod() {}
		
		@Marker1 public void markedMethod1() {}
	}
	
	public static class ClassOK_TwoMarkers {		
		public void unmarkedMethod() {}
		
		@Marker1 public void markedMethod1() {}
		@Marker2 public void markedMethod2() {}
	}
	
	public static class ClassOK_NoMarkers {		
		public void unmarkedMethod1() {}
		public void unmarkedMethod2() {}
	}
	
	public static class ClassNOK_MultiMarkers {		
		public void unmarkedMethod() {}
		
		@Marker1 @Marker2 public void markedMethod1() {}
	}
	
	public static class ClassNOK_MarkerReUse {		
		public void unmarkedMethod() {}
		
		@Marker1 public void markedMethod1() {}
		@Marker1 public void markedMethod2() {}
	}
	
}
