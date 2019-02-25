package com.example.medrec_1.slider_demo.model;


import android.os.Parcel;
import android.os.Parcelable;

public class CreateUserResponse implements Parcelable {


    /**
     * VideoSourceId : 17
     * VideoTitle : Super hit stage show in Bihar
     * MediaUrl : VideoFiles/2016_September_14/2016-Sep-14-17-57-26_videoplayback(3).mp4
     * MainThumbnailUrl : ImageFiles/2016_September_14/2016-Sep-14-17-57-26_d7ceb2d3-7da7-4c51-92fe-2803c01232191.jpg
     * SmallThumbnailUrl : ImageFiles/2016_September_14/2016-Sep-14-17-57-26_d7ceb2d3-7da7-4c51-92fe-2803c01232192.jpg
     * StandardThumbnailUrl : ImageFiles/2016_September_14/2016-Sep-14-17-57-26_d7ceb2d3-7da7-4c51-92fe-2803c01232193.jpg
     * ShouldDisplay : true
     * VideoDescription : very very hot sexy and steamy dance in bihar stage show in siwan. ...
     * CreatedDate : 2016-09-14T10:57:29
     * CreatedBy : 766cb4b9-8cc6-42b9-af36-555e33c9003d
     * UserIp :
     * TotalViews : 624
     * ModifiedDate : 2016-09-14T10:57:29
     * ModifiedBy : 766cb4b9-8cc6-42b9-af36-555e33c9003d
     * IsActive : true
     * StateId : 5
     * TotalLike : 58163
     * TotalDislike : 37
     * EncryptedId : JKdPh2vwq3w=
     * HowLong : 862 days ago
     */

    private int VideoSourceId;
    private String VideoTitle;
    private String MediaUrl;
    private String MainThumbnailUrl;
    private String SmallThumbnailUrl;
    private String StandardThumbnailUrl;
    private boolean ShouldDisplay;
    private String VideoDescription;
    private String CreatedDate;
    private String CreatedBy;
    private String UserIp;
    private int TotalViews;
    private String ModifiedDate;
    private String ModifiedBy;
    private boolean IsActive;
    private int StateId;
    private int TotalLike;
    private int TotalDislike;
    private String EncryptedId;
    private String HowLong;

    public int getVideoSourceId() {
        return VideoSourceId;
    }

    public void setVideoSourceId(int VideoSourceId) {
        this.VideoSourceId = VideoSourceId;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String VideoTitle) {
        this.VideoTitle = VideoTitle;
    }

    public String getMediaUrl() {
        return MediaUrl;
    }

    public void setMediaUrl(String MediaUrl) {
        this.MediaUrl = MediaUrl;
    }

    public String getMainThumbnailUrl() {
        return MainThumbnailUrl;
    }

    public void setMainThumbnailUrl(String MainThumbnailUrl) {
        this.MainThumbnailUrl = MainThumbnailUrl;
    }

    public String getSmallThumbnailUrl() {
        return SmallThumbnailUrl;
    }

    public void setSmallThumbnailUrl(String SmallThumbnailUrl) {
        this.SmallThumbnailUrl = SmallThumbnailUrl;
    }

    public String getStandardThumbnailUrl() {
        return StandardThumbnailUrl;
    }

    public void setStandardThumbnailUrl(String StandardThumbnailUrl) {
        this.StandardThumbnailUrl = StandardThumbnailUrl;
    }

    public boolean isShouldDisplay() {
        return ShouldDisplay;
    }

    public void setShouldDisplay(boolean ShouldDisplay) {
        this.ShouldDisplay = ShouldDisplay;
    }

    public String getVideoDescription() {
        return VideoDescription;
    }

    public void setVideoDescription(String VideoDescription) {
        this.VideoDescription = VideoDescription;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getUserIp() {
        return UserIp;
    }

    public void setUserIp(String UserIp) {
        this.UserIp = UserIp;
    }

    public int getTotalViews() {
        return TotalViews;
    }

    public void setTotalViews(int TotalViews) {
        this.TotalViews = TotalViews;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int StateId) {
        this.StateId = StateId;
    }

    public int getTotalLike() {
        return TotalLike;
    }

    public void setTotalLike(int TotalLike) {
        this.TotalLike = TotalLike;
    }

    public int getTotalDislike() {
        return TotalDislike;
    }

    public void setTotalDislike(int TotalDislike) {
        this.TotalDislike = TotalDislike;
    }

    public String getEncryptedId() {
        return EncryptedId;
    }

    public void setEncryptedId(String EncryptedId) {
        this.EncryptedId = EncryptedId;
    }

    public String getHowLong() {
        return HowLong;
    }

    public void setHowLong(String HowLong) {
        this.HowLong = HowLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.VideoSourceId);
        dest.writeString(this.VideoTitle);
        dest.writeString(this.MediaUrl);
        dest.writeString(this.MainThumbnailUrl);
        dest.writeString(this.SmallThumbnailUrl);
        dest.writeString(this.StandardThumbnailUrl);
        dest.writeByte(this.ShouldDisplay ? (byte) 1 : (byte) 0);
        dest.writeString(this.VideoDescription);
        dest.writeString(this.CreatedDate);
        dest.writeString(this.CreatedBy);
        dest.writeString(this.UserIp);
        dest.writeInt(this.TotalViews);
        dest.writeString(this.ModifiedDate);
        dest.writeString(this.ModifiedBy);
        dest.writeByte(this.IsActive ? (byte) 1 : (byte) 0);
        dest.writeInt(this.StateId);
        dest.writeInt(this.TotalLike);
        dest.writeInt(this.TotalDislike);
        dest.writeString(this.EncryptedId);
        dest.writeString(this.HowLong);
    }

    public CreateUserResponse() {
    }

    protected CreateUserResponse(Parcel in) {
        this.VideoSourceId = in.readInt();
        this.VideoTitle = in.readString();
        this.MediaUrl = in.readString();
        this.MainThumbnailUrl = in.readString();
        this.SmallThumbnailUrl = in.readString();
        this.StandardThumbnailUrl = in.readString();
        this.ShouldDisplay = in.readByte() != 0;
        this.VideoDescription = in.readString();
        this.CreatedDate = in.readString();
        this.CreatedBy = in.readString();
        this.UserIp = in.readString();
        this.TotalViews = in.readInt();
        this.ModifiedDate = in.readString();
        this.ModifiedBy = in.readString();
        this.IsActive = in.readByte() != 0;
        this.StateId = in.readInt();
        this.TotalLike = in.readInt();
        this.TotalDislike = in.readInt();
        this.EncryptedId = in.readString();
        this.HowLong = in.readString();
    }

    public static final Parcelable.Creator<CreateUserResponse> CREATOR = new Parcelable.Creator<CreateUserResponse>() {
        @Override
        public CreateUserResponse createFromParcel(Parcel source) {
            return new CreateUserResponse(source);
        }

        @Override
        public CreateUserResponse[] newArray(int size) {
            return new CreateUserResponse[size];
        }
    };
}
