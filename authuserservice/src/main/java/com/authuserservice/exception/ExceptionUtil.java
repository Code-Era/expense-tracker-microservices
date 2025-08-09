package com.authuserservice.exception;


import com.authuserservice.exception.common.*;

public class ExceptionUtil {

    public static OperationFailedException throwSaveFailed(String entityName) {
        throw new OperationFailedException("Failed to save " + entityName + ". Please try again.");
    }

    public static OperationFailedException throwUpdateFailed(String entityName) {
        throw new OperationFailedException("Failed to update " + entityName + ". Please try again.");
    }

    public static OperationFailedException throwDeleteFailed(String entityName) {
        throw new OperationFailedException("Failed to delete " + entityName + ". Please try again.");
    }

    public static UnauthorizedException throwUnauthorized(String action) {
        throw new UnauthorizedException("Unauthorized access to: " + action);
    }

    public static AccessDeniedException throwAccessDenied(String resourceName) {
        throw new AccessDeniedException("Access denied to " + resourceName);
    }

    public static ResourceNotFoundException throwResourceNotFound(String resource, String idOrKey) {
        throw new ResourceNotFoundException(resource + " not found with: " + idOrKey);
    }

    public static DuplicateResourceException duplicate(String field, String value) {
        throw  new DuplicateResourceException(field + " already exists: " + value);
    }

    public static BadRequestException throwBadRequest(String reason) {
        throw new BadRequestException("Bad Request: " + reason);
    }
}
