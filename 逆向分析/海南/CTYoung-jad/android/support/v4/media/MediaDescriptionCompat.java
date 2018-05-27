// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.*;
import android.text.TextUtils;

// Referenced classes of package android.support.v4.media:
//            MediaDescriptionCompatApi21

public final class MediaDescriptionCompat
    implements Parcelable
{
    public static final class Builder
    {

        public MediaDescriptionCompat build()
        {
            return new MediaDescriptionCompat(mMediaId, mTitle, mSubtitle, mDescription, mIcon, mIconUri, mExtras);
        }

        public Builder setDescription(CharSequence charsequence)
        {
            mDescription = charsequence;
            return this;
        }

        public Builder setExtras(Bundle bundle)
        {
            mExtras = bundle;
            return this;
        }

        public Builder setIconBitmap(Bitmap bitmap)
        {
            mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(Uri uri)
        {
            mIconUri = uri;
            return this;
        }

        public Builder setMediaId(String s)
        {
            mMediaId = s;
            return this;
        }

        public Builder setSubtitle(CharSequence charsequence)
        {
            mSubtitle = charsequence;
            return this;
        }

        public Builder setTitle(CharSequence charsequence)
        {
            mTitle = charsequence;
            return this;
        }

        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public Builder()
        {
        }
    }


    private MediaDescriptionCompat(Parcel parcel)
    {
        mMediaId = parcel.readString();
        mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        mSubtitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        mDescription = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        mIcon = (Bitmap)parcel.readParcelable(null);
        mIconUri = (Uri)parcel.readParcelable(null);
        mExtras = parcel.readBundle();
    }


    private MediaDescriptionCompat(String s, CharSequence charsequence, CharSequence charsequence1, CharSequence charsequence2, Bitmap bitmap, Uri uri, Bundle bundle)
    {
        mMediaId = s;
        mTitle = charsequence;
        mSubtitle = charsequence1;
        mDescription = charsequence2;
        mIcon = bitmap;
        mIconUri = uri;
        mExtras = bundle;
    }


    public static MediaDescriptionCompat fromMediaDescription(Object obj)
    {
        if(obj == null || android.os.Build.VERSION.SDK_INT < 21)
        {
            return null;
        } else
        {
            Object obj1 = new Builder();
            ((Builder) (obj1)).setMediaId(MediaDescriptionCompatApi21.getMediaId(obj));
            ((Builder) (obj1)).setTitle(MediaDescriptionCompatApi21.getTitle(obj));
            ((Builder) (obj1)).setSubtitle(MediaDescriptionCompatApi21.getSubtitle(obj));
            ((Builder) (obj1)).setDescription(MediaDescriptionCompatApi21.getDescription(obj));
            ((Builder) (obj1)).setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(obj));
            ((Builder) (obj1)).setIconUri(MediaDescriptionCompatApi21.getIconUri(obj));
            ((Builder) (obj1)).setExtras(MediaDescriptionCompatApi21.getExtras(obj));
            obj1 = ((Builder) (obj1)).build();
            obj1.mDescriptionObj = obj;
            return ((MediaDescriptionCompat) (obj1));
        }
    }

    public int describeContents()
    {
        return 0;
    }

    public CharSequence getDescription()
    {
        return mDescription;
    }

    public Bundle getExtras()
    {
        return mExtras;
    }

    public Bitmap getIconBitmap()
    {
        return mIcon;
    }

    public Uri getIconUri()
    {
        return mIconUri;
    }

    public Object getMediaDescription()
    {
        if(mDescriptionObj != null || android.os.Build.VERSION.SDK_INT < 21)
        {
            return mDescriptionObj;
        } else
        {
            Object obj = MediaDescriptionCompatApi21.Builder.newInstance();
            MediaDescriptionCompatApi21.Builder.setMediaId(obj, mMediaId);
            MediaDescriptionCompatApi21.Builder.setTitle(obj, mTitle);
            MediaDescriptionCompatApi21.Builder.setSubtitle(obj, mSubtitle);
            MediaDescriptionCompatApi21.Builder.setDescription(obj, mDescription);
            MediaDescriptionCompatApi21.Builder.setIconBitmap(obj, mIcon);
            MediaDescriptionCompatApi21.Builder.setIconUri(obj, mIconUri);
            MediaDescriptionCompatApi21.Builder.setExtras(obj, mExtras);
            mDescriptionObj = MediaDescriptionCompatApi21.Builder.build(obj);
            return mDescriptionObj;
        }
    }

    public String getMediaId()
    {
        return mMediaId;
    }

    public CharSequence getSubtitle()
    {
        return mSubtitle;
    }

    public CharSequence getTitle()
    {
        return mTitle;
    }

    public String toString()
    {
        return (new StringBuilder()).append(mTitle).append(", ").append(mSubtitle).append(", ").append(mDescription).toString();
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        if(android.os.Build.VERSION.SDK_INT < 21)
        {
            parcel.writeString(mMediaId);
            TextUtils.writeToParcel(mTitle, parcel, i);
            TextUtils.writeToParcel(mSubtitle, parcel, i);
            TextUtils.writeToParcel(mDescription, parcel, i);
            parcel.writeParcelable(mIcon, i);
            parcel.writeParcelable(mIconUri, i);
            parcel.writeBundle(mExtras);
            return;
        } else
        {
            MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), parcel, i);
            return;
        }
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public MediaDescriptionCompat createFromParcel(Parcel parcel)
        {
            if(android.os.Build.VERSION.SDK_INT < 21)
                return new MediaDescriptionCompat(parcel);
            else
                return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public MediaDescriptionCompat[] newArray(int i)
        {
            return new MediaDescriptionCompat[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

}
