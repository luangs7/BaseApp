package br.com.luan2.baseapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.luan2.baseapp.model.request.BaseRequest;


/**
 * Created by Luan on 15/05/17.
 */

public class User extends BaseRequest implements Parcelable{


    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("authentication")
    @Expose
    private String authentication;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;
    @SerializedName("exists")
    @Expose
    private Boolean exists;
    @SerializedName("contracts")
    @Expose
    private String contracts;
    @SerializedName("realties")
    @Expose
    private String realties;
    @SerializedName("renters2")
    @Expose
    private String renters;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("device")
    @Expose
    private String device;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts;
    }

    public String getRealties() {
        return realties;
    }

    public void setRealties(String realties) {
        this.realties = realties;
    }

    public String getRenters() {
        return renters;
    }

    public void setRenters(String renters) {
        this.renters = renters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.id);
        dest.writeString(this.authentication);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.type);
        dest.writeString(this.identifier);
        dest.writeString(this.zipcode);
        dest.writeString(this.number);
        dest.writeString(this.kind);
        dest.writeString(this.password);
        dest.writeString(this.password_confirmation);
        dest.writeValue(this.exists);
        dest.writeString(this.contracts);
        dest.writeString(this.realties);
        dest.writeString(this.renters);
        dest.writeString(this.code);
        dest.writeString(this.device);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.avatar = in.readString();
        this.id = in.readString();
        this.authentication = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.type = in.readString();
        this.identifier = in.readString();
        this.zipcode = in.readString();
        this.number = in.readString();
        this.kind = in.readString();
        this.password = in.readString();
        this.password_confirmation = in.readString();
        this.exists = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.contracts = in.readString();
        this.realties = in.readString();
        this.renters = in.readString();
        this.code = in.readString();
        this.device = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
