package model;

import controller.persistence.annotation.Entity;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
@Entity(sourceName = "StarCategoryAssociationSource")
public class StarCategoryAssociation extends AssociativeEntity {
    public StarCategoryAssociation(long id, long primaryId, long secondaryId, long date) {
        super(id, primaryId, secondaryId, date);
    }
}
