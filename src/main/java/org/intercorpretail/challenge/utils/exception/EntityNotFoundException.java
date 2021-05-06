package org.intercorpretail.challenge.utils.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Class<?> entityClass, String id) {
        super(String.format("Entity %s with id %s not found", entityClass, id));
    }
}

