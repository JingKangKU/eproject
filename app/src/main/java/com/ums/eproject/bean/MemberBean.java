package com.ums.eproject.bean;

public class MemberBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"memberId":310001011,"mobile":"****6866","isVerified":1,"verifyDate":null,"realName":"靖康","idcardType":1,"idCardNo":"3****712","gender":null,"nickName":null,"memberLevelAlias":"普通会员","memberTypeId":3051000,"memberTypeAlias":"成人卡","memberTypeExpire":null,"memberStatus":1,"memberStatusAlias":"正常","balance":1940.45,"leftScore":2106}
     */

    private int code;
    private String message;
    /**
     * memberId : 310001011
     * mobile : ****6866
     * isVerified : 1
     * verifyDate : null
     * realName : 靖康
     * idcardType : 1
     * idCardNo : 3****712
     * gender : null
     * nickName : null
     * memberLevelAlias : 普通会员
     * memberTypeId : 3051000
     * memberTypeAlias : 成人卡
     * memberTypeExpire : null
     * memberStatus : 1
     * memberStatusAlias : 正常
     * balance : 1940.45
     * leftScore : 2106
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int memberId;
        private String mobile;
        private int isVerified;
        private Object verifyDate;
        private String realName;
        private int idcardType;
        private String idCardNo;
        private Object gender;
        private Object nickName;
        private String memberLevelAlias;
        private int memberTypeId;
        private String memberTypeAlias;
        private Object memberTypeExpire;
        private int memberStatus;
        private String memberStatusAlias;
        private double balance;
        private int leftScore;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(int isVerified) {
            this.isVerified = isVerified;
        }

        public Object getVerifyDate() {
            return verifyDate;
        }

        public void setVerifyDate(Object verifyDate) {
            this.verifyDate = verifyDate;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getIdcardType() {
            return idcardType;
        }

        public void setIdcardType(int idcardType) {
            this.idcardType = idcardType;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public String getMemberLevelAlias() {
            return memberLevelAlias;
        }

        public void setMemberLevelAlias(String memberLevelAlias) {
            this.memberLevelAlias = memberLevelAlias;
        }

        public int getMemberTypeId() {
            return memberTypeId;
        }

        public void setMemberTypeId(int memberTypeId) {
            this.memberTypeId = memberTypeId;
        }

        public String getMemberTypeAlias() {
            return memberTypeAlias;
        }

        public void setMemberTypeAlias(String memberTypeAlias) {
            this.memberTypeAlias = memberTypeAlias;
        }

        public Object getMemberTypeExpire() {
            return memberTypeExpire;
        }

        public void setMemberTypeExpire(Object memberTypeExpire) {
            this.memberTypeExpire = memberTypeExpire;
        }

        public int getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(int memberStatus) {
            this.memberStatus = memberStatus;
        }

        public String getMemberStatusAlias() {
            return memberStatusAlias;
        }

        public void setMemberStatusAlias(String memberStatusAlias) {
            this.memberStatusAlias = memberStatusAlias;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public int getLeftScore() {
            return leftScore;
        }

        public void setLeftScore(int leftScore) {
            this.leftScore = leftScore;
        }
    }
}
