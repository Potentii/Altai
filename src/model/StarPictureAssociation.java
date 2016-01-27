package model;

import controller.persistence.annotation.Entity;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
@Entity(sourceName = "StarPictureAssociationSource")
public class StarPictureAssociation extends AssociativeEntity {
    public StarPictureAssociation(long id, long primaryId, long secondaryId, long date) {
        super(id, primaryId, secondaryId, date);
    }
}
