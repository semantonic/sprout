package org.semantonic.sprout.boundary;

import org.junit.Assert;
import org.junit.Test;
import org.semantonic.sprout.entity.DatasetEntity;
import org.semantonic.sprout.entity.TestDatasetEntity;
import org.semantonic.sprout.entity.UriValue;

public class DatasetConverterTest {

	private final DatasetConverter converter = new DatasetConverter();
	private final ApiUriBuilder uriBuilder = ApiUriBuilder.fromDummyBase();

	@Test
	public void shouldCreateDatasetEntityFromDto() throws Exception {
		final DatasetDTO dto = new DatasetDTO(null, "my_id", "my_name", "my_link");

		final DatasetEntity target = converter.createEntityForDataset(dto);
		assertCorrectConversion(dto, target);
	}

	@Test
	public void shouldConvertRelationEntityToDto() throws Exception {
		final DatasetEntity entity = new TestDatasetEntity("http://sprout.org/dataset/abcd1234", 42L, "foo i'm called", "some_link");

		final DatasetDTO target = this.converter.convertToDTO(entity, this.uriBuilder);
		DatasetDtoOutCompareUtil.assertContentIsEqual(entity, target, this.uriBuilder);
	}

	private void assertCorrectConversion(DatasetDTO dto, DatasetEntity target) throws Exception {
		Assert.assertNotNull(target);

		Assert.assertEquals(dto.id, target.getId().getValueAsString());
		Assert.assertEquals(dto.name, target.getName().orElse(null));
		Assert.assertEquals(dto.link, UriValue.asString(target.getLink(), null));
	}

}
