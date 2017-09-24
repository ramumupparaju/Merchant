package com.incon.connect.apimodel.components.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sensor implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sensor_username")
    @Expose
    private String sensorUserName;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSensorUserName(String sensorUserName) {
        this.sensorUserName = sensorUserName;
    }

    public String getSensorUserName() {
        return sensorUserName;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Sensor() {
    }

    protected Sensor(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        identifier = in.readString();
        mode = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = (String) in.readValue(String.class.getClassLoader());
        patientId = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(identifier);
        dest.writeString(mode);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeValue(deletedAt);
        if (patientId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(patientId);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Sensor> CREATOR = new Creator<Sensor>() {
        @Override
        public Sensor createFromParcel(Parcel in) {
            return new Sensor(in);
        }

        @Override
        public Sensor[] newArray(int size) {
            return new Sensor[size];
        }
    };
}