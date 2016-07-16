package org.semantonic.sprout.entity;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class DatasetEntityTest {
	
	@Test
	public void shouldCreateEntity() throws InvalidUriValueException {
		final DatasetId id = DatasetId.create("http://sprout.org/dataset/abcd1234");
		final String name = "Some Fancy Dataset";
		final UriValue link = UriValue.create("http://sprout.org/dataset/abcd1234?x=y");
		
		final DatasetEntity ds = new DatasetEntity(id, name, link);
		
		Assert.assertNotNull(ds.getInternalIdAsOptional());
		Assert.assertEquals(id, ds.getId());
		Assert.assertEquals(name, ds.getName().get());
		Assert.assertEquals(link, ds.getLink().get());
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailCreationIfIdIsNull() throws InvalidUriValueException {
		final DatasetId id = null;
		final String name = "Some Fancy Dataset";
		final UriValue link = UriValue.create("http://sprout.org/dataset/abcd1234?x=y");
		
		new DatasetEntity(id, name, link);
	}

	@Test
	public void shouldPresetInternalId() {
		final DatasetEntity ds = createValidDatasetEntity();
		
		Assert.assertFalse(ds.getInternalIdAsOptional().isPresent());

		final InternalId internalId = InternalId.create(1L);
		ds.presetInternalId(internalId);
		
		Assert.assertEquals(internalId, ds.getInternalId());
		Assert.assertEquals(internalId, ds.getInternalIdAsOptional().get());
		Assert.assertEquals(internalId.getValue(), ds.internalId);
	}
	
	@Test
	public void shouldUpdateId() throws InvalidUriValueException {
		final DatasetEntity ds = createValidDatasetEntity();
		
		final DatasetId newId = DatasetId.create("http://sprout.org/dataset/zyxw9876");
		ds.setId(newId);
		
		Assert.assertEquals(newId, ds.getId());
		Assert.assertEquals(newId.getValueAsString(), ds.id);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailUpdatingIdToNull() {
		final DatasetEntity ds = createValidDatasetEntity();
		
		ds.setId(null);
	}

	@Test
	public void shouldUpdateName() {
		final DatasetEntity ds = createValidDatasetEntity();
		
		final String newName = "Yet Another Dataset"; 
		ds.setName(Optional.of(newName));
		
		Assert.assertEquals(newName, ds.getName().get());
		Assert.assertEquals(newName, ds.name);
	}

	@Test
	public void shouldUpdateLink() throws InvalidUriValueException {
		final DatasetEntity ds = createValidDatasetEntity();
		
		final UriValue newLink = UriValue.create("http://sprout.org/dataset/abcd1234?a=b");
		ds.setLink(Optional.of(newLink));
		
		Assert.assertEquals(newLink, ds.getLink().get());
		Assert.assertEquals(newLink.getValueAsString(), ds.link);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailAccessWhenInternalIdIsNull() {
		final DatasetEntity ds = createValidDatasetEntity();
		
		ds.getInternalId();
	}
	
	@Test(expected=InvalidPersistentDataException.class)
	public void shouldFailLoadingFromDbIfInternalIdIsNull() {
		final DatasetEntity ds = createValidDatasetEntity();
		ds.internalId = null;
		
		ds.onPostLoad();
	}
	
	@Test(expected=InvalidPersistentDataException.class)
	public void shouldFailLoadingFromDbIfIdIsNull() {
		final DatasetEntity ds = createValidDatasetEntity();
		ds.presetInternalId(InternalId.create(1L));
		ds.id = null;
		
		ds.onPostLoad();
	}
	
	@Test(expected=InvalidPersistentDataException.class)
	public void shouldFailLoadingFromDbIfIdIsInvalid() {
		final DatasetEntity ds = createValidDatasetEntity();
		ds.presetInternalId(InternalId.create(1L));
		ds.id = " a";
		
		ds.onPostLoad();
	}
	
	
	// -------------------------------
	private DatasetEntity createValidDatasetEntity() {
		try {
			final DatasetId id = DatasetId.create("http://sprout.org/dataset/abcd1234");
			final String name = "Some Fancy Dataset";
			final UriValue link = UriValue.create("http://sprout.org/dataset/abcd1234?x=y");
			
			final DatasetEntity ds = new DatasetEntity(id, name, link);
			return ds;
		} catch (InvalidUriValueException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
