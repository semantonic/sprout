package org.semantonic.sprout.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class DatasetEntity {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Long internalId;

	protected static final String ID_FIELD_NAME = "id";
	protected String id; // permanent, possibly non-local URI

	protected String name;	
	protected String link;
	
	private transient InternalId internalIdVO;
	private transient DatasetId idVO;
	private transient Optional<UriValue> linkVO;

	public DatasetEntity(DatasetId id, String name, UriValue link) {
		this(id, Optional.ofNullable(name), Optional.ofNullable(link));
	}
	
	public DatasetEntity(DatasetId id, Optional<String> name, Optional<UriValue> link) {
		setId(checkNotNull(id, "id"));
		setName(checkNotNull(name, "name"));
		setLink(checkNotNull(link, "link"));
	}

	protected DatasetEntity() {
	}

	@PostLoad protected void onPostLoad() {
		this.internalIdVO = initInternalIdFromPersistence();
		this.idVO = initDatasetIdFromPersistence();
		this.linkVO = initLinkFromPersistence();
		
		// do sanity checks
		checkNotNull(this.internalIdVO);
		checkNotNull(this.idVO);
		checkNotNull(this.linkVO);
	}
	
	// TODO: testcase - aktualisierung von internalIdVO nach der erzeugung
	@PostPersist protected void onPostPersist() {
		this.internalIdVO = initInternalIdFromPersistence();
		checkNotNull(this.internalIdVO);
	}
	
	protected InternalId initInternalIdFromPersistence() {
		if (internalId == null) {
			throw new InvalidPersistentDataException("internalId is null");
		}
		
		return InternalId.create(internalId);
	}
	
	protected DatasetId initDatasetIdFromPersistence() {
		if (id == null) {
			throw new InvalidPersistentDataException("id is null");
		}
		
		try {
			return DatasetId.create(id) ;
		} catch (InvalidUriValueException ex) {
			throw new InvalidPersistentDataException(String.format("id is \"%s\"", id), ex);
		}
	}
	
	protected Optional<UriValue> initLinkFromPersistence() {
		if (link == null) {
			return Optional.empty();
		}
		
		try {
			return Optional.of(UriValue.create(link));
		} catch (InvalidUriValueException ex) {
			throw new InvalidPersistentDataException(String.format("link is \"%s\"", link), ex);
		}
	}

	protected void presetInternalId(InternalId targetInternalId) {
		this.internalId = targetInternalId.getValue();
		this.internalIdVO = targetInternalId;
	}
	public InternalId getInternalId() {
		if (internalId == null) throw new IllegalStateException("internalId is <null>");		
		return internalIdVO;
	}
	protected Optional<InternalId> getInternalIdAsOptional() {
		return Optional.ofNullable(internalIdVO);
	}

	public DatasetId getId() {
		return idVO;
	}
	
	public void setId(DatasetId id) {
		this.id = id.getValueAsString();
		this.idVO = id;
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	
	public void setName(Optional<String> name) {
		this.name = name.orElse(null);
	}

	public Optional<UriValue> getLink() {
		return linkVO;
	}

	public void setLink(Optional<UriValue> link) {
		this.link = link.map(l -> l.getValueAsString()).orElse(null);
		this.linkVO = link;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", DatasetEntity.class.getName(), this.id);
	}

}
