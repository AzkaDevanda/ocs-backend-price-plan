package com.ocs.portal.svc.role.repository.custom;

public interface RolePortalCustomRepository {
    public int fieldIsReferenced(String tableName, Long roleId);
}
