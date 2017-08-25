package hr.marijanbistre.algebrafinalapplication.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import hr.marijanbistre.algebrafinalapplication.database.DatabaseHelper;

public class ScoreContentProvider extends ContentProvider {

    private DatabaseHelper databaseHelper;
    private static final String AUTHORITY = "hr.marijanbistre.algebrafinalapplication.providers";
    private static final String PATH = "scores";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
    private static final int SCORES = 10;
    private static final int SCORE_ID = 20;
    private static final UriMatcher URI_MATCHER = createUriMatcher();

    private static UriMatcher createUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, SCORES);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", SCORE_ID);
        return uriMatcher;
    }

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/scores";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/score";



    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case SCORES:
                return CONTENT_TYPE;
            case SCORE_ID:
                return CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    public ScoreContentProvider() {
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = databaseHelper.insert(values);
        Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String id = null;
        if (URI_MATCHER.match(uri) == SCORE_ID) {
            id = uri.getPathSegments().get(1);
        }
        return databaseHelper.query(id, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
