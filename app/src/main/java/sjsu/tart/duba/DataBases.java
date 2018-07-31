package sjsu.tart.duba;

import android.provider.BaseColumns;

/**
 * Created by lion7 on 2018-07-28.
 */

public final class DataBases {

    public static final class CreateDB implements BaseColumns {

        public static final String MARKERID = "markerid";
        public static final String TITLE = "title";
        public static final String TAG = "tag";
        public static final String LAN = "lan";
        public static final String LON = "lon";
        public static final String COLOR = "color";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +MARKERID+" text not null , "
                +TITLE+" text not null , "
                +TAG+" text not null , "
                +LAN+" text not null , "
                +LON+" text not null , "
                +COLOR+" text not null );";
    }

}