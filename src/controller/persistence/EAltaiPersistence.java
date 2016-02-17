package controller.persistence;

import java.io.File;

/**
 * (Obs.: 'RAW' paths just doesn't include the final file separator)
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public enum EAltaiPersistence {
    ENTITIES_RELATIVE_PATH_RAW("Entity"),
    ENTITIES_RELATIVE_PATH(ENTITIES_RELATIVE_PATH_RAW.getValue() + File.separator),
    MEDIA_RELATIVE_PATH_RAW("Media"),
    MEDIA_RELATIVE_PATH(MEDIA_RELATIVE_PATH_RAW.getValue() + File.separator),


    HOST_ICON_RELATIVE_PATH_RAW(MEDIA_RELATIVE_PATH.getValue() + "HostIcon"),
    STAR_MAIN_IMG_RELATIVE_PATH_RAW(MEDIA_RELATIVE_PATH.getValue() + "StarMainPic"),
    PICTURE_RELATIVE_PATH_RAW(MEDIA_RELATIVE_PATH.getValue() + "Picture"),
    MOVIE_RELATIVE_PATH_RAW(MEDIA_RELATIVE_PATH.getValue() + "Movie"),

    HOST_ICON_RELATIVE_PATH(HOST_ICON_RELATIVE_PATH_RAW.getValue() + File.separator),
    STAR_MAIN_IMG_RELATIVE_PATH(STAR_MAIN_IMG_RELATIVE_PATH_RAW.getValue() + File.separator),
    PICTURE_RELATIVE_PATH(PICTURE_RELATIVE_PATH_RAW.getValue() + File.separator),
    MOVIE_RELATIVE_PATH(MOVIE_RELATIVE_PATH_RAW.getValue() + File.separator),


    ENTITY_DEFAULT_EXTENSION(".json");


    private String value;

    EAltaiPersistence(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
