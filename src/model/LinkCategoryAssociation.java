package model;

import controller.persistence.annotation.Entity;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
@Entity(sourceName = "LinkCategoryAssociationSource")
public class LinkCategoryAssociation extends AssociativeEntity {
    public LinkCategoryAssociation(long id, long primaryId, long secondaryId, long date) {
        super(id, primaryId, secondaryId, date);
    }
}
