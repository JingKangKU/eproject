package com.ums.eproject.bean;

public class UserBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"tokenHead":"Tetmall","userInfo":{"memberId":310001011,"mobile":"****6866","isVerified":0,"verifyDate":null,"realName":"用户6866","idcardType":1,"idCardNo":null,"gender":null,"nickName":null,"memberLevelAlias":"普通会员","memberTypeId":3051000,"memberTypeAlias":"成人卡","memberTypeExpire":null,"memberStatus":1,"memberStatusAlias":"正常","balance":0,"leftScore":0},"token":"eyJhbGciOiJIUzUxMiJ9.eyJhcHAiOiJBUFAiLCJzdWIiOjMxMDAwMTAxMSwiY3JlYXRlZCI6MTY3Mzc1MTY1OTE1NiwiZXhwIjoxNjc2MzQzNjU5fQ.8k6ytbzpzmjZzBJK4S2uGnJt0wc0vPq8Zg9j-tKH5x5-1Wzx1T8nKraVsyJto2DKdN6pmigQAsCC-itcbzrSVA"}
     */

    private int code;
    private String message;
    /**
     * tokenHead : Tetmall
     * userInfo : {"memberId":310001011,"mobile":"****6866","isVerified":0,"verifyDate":null,"realName":"用户6866","idcardType":1,"idCardNo":null,"gender":null,"nickName":null,"memberLevelAlias":"普通会员","memberTypeId":3051000,"memberTypeAlias":"成人卡","memberTypeExpire":null,"memberStatus":1,"memberStatusAlias":"正常","balance":0,"leftScore":0}
     * token : eyJhbGciOiJIUzUxMiJ9.eyJhcHAiOiJBUFAiLCJzdWIiOjMxMDAwMTAxMSwiY3JlYXRlZCI6MTY3Mzc1MTY1OTE1NiwiZXhwIjoxNjc2MzQzNjU5fQ.8k6ytbzpzmjZzBJK4S2uGnJt0wc0vPq8Zg9j-tKH5x5-1Wzx1T8nKraVsyJto2DKdN6pmigQAsCC-itcbzrSVA
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
        private String tokenHead;
        /**
         * memberId : 310001011
         * mobile : ****6866
         * isVerified : 0
         * verifyDate : null
         * realName : 用户6866
         * idcardType : 1
         * idCardNo : null
         * gender : null
         * nickName : null
         * memberLevelAlias : 普通会员
         * memberTypeId : 3051000
         * memberTypeAlias : 成人卡
         * memberTypeExpire : null
         * memberStatus : 1
         * memberStatusAlias : 正常
         * balance : 0
         * leftScore : 0
         */

        private UserInfoBean userInfo;
        private String token;

        public String getTokenHead() {
            return tokenHead;
        }

        public void setTokenHead(String tokenHead) {
            this.tokenHead = tokenHead;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserInfoBean {
            private int memberId;
            private String mobile;
            private int isVerified;
            private Object verifyDate;
            private String realName;
            private int idcardType;
            private Object idCardNo;
            private Object gender;
            private Object nickName;
            private String memberLevelAlias;
            private int memberTypeId;
            private String memberTypeAlias;
            private Object memberTypeExpire;
            private int memberStatus;
            private String memberStatusAlias;
            private int balance;
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

            public Object getIdCardNo() {
                return idCardNo;
            }

            public void setIdCardNo(Object idCardNo) {
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

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
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
}
