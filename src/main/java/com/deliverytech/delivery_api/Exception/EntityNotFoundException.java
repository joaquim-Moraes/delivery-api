package com.deliverytech.delivery_api.Exception;

/**
 * Exceção lançada quando uma entidade não é encontrada.
 * HTTP Status: 404 Not Found
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String entityName;
    private Long entityId;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long entityId) {
        super(String.format("%s com ID %d não encontrado", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public EntityNotFoundException(String entityName, String fieldName, String fieldValue) {
        super(String.format("%s com %s '%s' não encontrado", entityName, fieldName, fieldValue));
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public Long getEntityId() {
        return entityId;
    }
}
