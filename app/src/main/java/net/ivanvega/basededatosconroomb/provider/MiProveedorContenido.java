package net.ivanvega.basededatosconroomb.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.ivanvega.basededatosconroomb.data.AppDataBase;
import net.ivanvega.basededatosconroomb.data.User;
import net.ivanvega.basededatosconroomb.data.UserDao;

public class MiProveedorContenido
        extends ContentProvider {

    /*
        content://net.ivanvega.basededatosconroomb.provider/usuario ->  insert y query
        content://net.ivanvega.basededatosconroomb.provider/usuario/# -> delete, query y update
        content://net.ivanvega.basededatosconroomb.provider/usuario/*  ->  query

     */

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario", 1);
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario/#", 2);
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario/*", 3);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings,
                        @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {


        AppDataBase db =
                AppDataBase.getDataBaseInstance(getContext());

        UserDao dao = db.getUserDao();

        MatrixCursor cursor = new MatrixCursor(new String[]
                {"uid", "first_name", "last_name"});

          switch ( sURIMatcher.match(uri)){
              case 1:
                  AppDataBase.databaseWriteExecutor.execute(() -> {
                      for(User item : dao.getAll()){
                          cursor.newRow().add("uid", item.uid)
                                  .add("first_name", item.firstName)
                                  .add("last_name", item.lastName);

                          Log.d("TABla Usuario", item.uid + " " +  item.firstName
                                  + " " + item.lastName);
                      }
                  });

                  break;

              case 2:

                  break;

              case 3:

                  break;
          }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        /*Tipos MIME
            Documentos HTML     "text/html"
            Documentos XML      "text/xml"
            Documentos JavaScript

            Imagenes  PNG    "images/png"
            Imagenes JPG

            tipos mime para android

            vnd.android.cursor.item/vnd.net.ivanvega.basededatosconroomb.provider.usuario
            vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario


         */

        String tipoMIME = "";

        switch (sURIMatcher.match(uri)){
            case 1:
                tipoMIME = "vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario";
                break;

            case 2:
                tipoMIME = "vnd.android.cursor.item/vnd.net.ivanvega.basededatosconroomb.provider.usuario";

            case 3:
                tipoMIME = "vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario";
                break;
        }


        return tipoMIME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
