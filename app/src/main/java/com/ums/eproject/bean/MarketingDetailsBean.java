package com.ums.eproject.bean;

import java.io.Serializable;
import java.util.List;

public class MarketingDetailsBean implements Serializable{

    private static final long serialVersionUID = 8325234829755307236L;
    /**
     * submerInfo : null
     * id : 6061010
     * subjectCategoryName : test修改
     * unitName :
     * title : 全民营销测试
     * picUrl : null
     * gallery : null
     * contactPerson : test
     * contactTel : 13486193213
     * description : null
     * remark : null
     */

    private SubmerInfo submerInfo;
    private int id;
    private String subjectCategoryName;
    private String unitName;
    private String title;
    private String picUrl;
    private List<String> gallery;
    private String contactPerson;
    private String contactTel;
    private String detailDesc;
    private Object remark;

    @Override
    public String toString() {
        return "MarketingDetailsBean{" +
                "submerInfo=" + submerInfo +
                ", id=" + id +
                ", subjectCategoryName='" + subjectCategoryName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", gallery=" + gallery +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", description=" + detailDesc +
                ", remark=" + remark +
                '}';
    }

    public SubmerInfo getSubmerInfo() {
        return submerInfo;
    }

    public void setSubmerInfo(SubmerInfo submerInfo) {
        this.submerInfo = submerInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectCategoryName() {
        return subjectCategoryName;
    }

    public void setSubjectCategoryName(String subjectCategoryName) {
        this.subjectCategoryName = subjectCategoryName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public class SubmerInfo implements Serializable {
        private static final long serialVersionUID = 2675493570766471883L;
        private String id;
        private String submerName;
        private String iconPath;
        private String address;
        private String submerNo;
        private String contactPerson;
        private String telephone;

        @Override
        public String toString() {
            return "SubmerInfo{" +
                    "id='" + id + '\'' +
                    ", submerName='" + submerName + '\'' +
                    ", iconPath='" + iconPath + '\'' +
                    ", address='" + address + '\'' +
                    ", submerNo='" + submerNo + '\'' +
                    ", contactPerson='" + contactPerson + '\'' +
                    ", telephone='" + telephone + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubmerName() {
            return submerName;
        }

        public void setSubmerName(String submerName) {
            this.submerName = submerName;
        }

        public String getIconPath() {
            return iconPath;
        }

        public void setIconPath(String iconPath) {
            this.iconPath = iconPath;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSubmerNo() {
            return submerNo;
        }

        public void setSubmerNo(String submerNo) {
            this.submerNo = submerNo;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
