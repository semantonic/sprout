package org.semantonic.sprout.entity;

public class TestDatasetEntity extends DatasetEntity {

	
	public TestDatasetEntity(String id, Long internalId, String name, String link) {
		this(datasetId(id), internalId(internalId), name, uriValue(link));
	}
	
	public TestDatasetEntity(DatasetId id, InternalId internalId, String name, UriValue link) {
		super(id, name, link);
		
		if (internalId != null) {
			internalId.applyTo(this);
		}
	}
	
	// ---------------------------------
	private static DatasetId datasetId(String idValue) {
		try {
			return DatasetId.create(idValue);
		} catch (InvalidUriValueException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static InternalId internalId(Long idValue) {
		return idValue != null ? InternalId.create(idValue) : null;
	}
	
	private static UriValue uriValue(String value) {
		try {
			return value != null ? UriValue.create(value) : null;
		} catch (InvalidUriValueException e) {
			throw new RuntimeException(e);
		}
	}
	
}
