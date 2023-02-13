package com.ums.eproject.bean;

import java.util.List;

public class BookBalance {

    /**
     * code : 200
     * message : 操作成功
     * data : {"pageNum":1,"pageSize":10,"totalPage":1,"total":1,"list":[{"cardNo":"8982001680000232","tradeNote":"会员卡充值","beginBalance":0,"tradeBalance":2000.45,"finalBalance":2000.45,"tradeUserTypeAlias":"App应用","tradeUnitName":"","tradeDatetime":"2023-02-08 15:32:57"}]}
     */

    private int code;
    private String message;
    /**
     * pageNum : 1
     * pageSize : 10
     * totalPage : 1
     * total : 1
     * list : [{"cardNo":"8982001680000232","tradeNote":"会员卡充值","beginBalance":0,"tradeBalance":2000.45,"finalBalance":2000.45,"tradeUserTypeAlias":"App应用","tradeUnitName":"","tradeDatetime":"2023-02-08 15:32:57"}]
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
        private int pageNum;
        private int pageSize;
        private int totalPage;
        private int total;
        /**
         * cardNo : 8982001680000232
         * tradeNote : 会员卡充值
         * beginBalance : 0.0
         * tradeBalance : 2000.45
         * finalBalance : 2000.45
         * tradeUserTypeAlias : App应用
         * tradeUnitName :
         * tradeDatetime : 2023-02-08 15:32:57
         */

        private List<ListBean> list;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String cardNo;
            private String tradeNote;
            private double beginBalance;
            private double tradeBalance;
            private double finalBalance;
            private String tradeUserTypeAlias;
            private String tradeUnitName;
            private String tradeDatetime;

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public String getTradeNote() {
                return tradeNote;
            }

            public void setTradeNote(String tradeNote) {
                this.tradeNote = tradeNote;
            }

            public double getBeginBalance() {
                return beginBalance;
            }

            public void setBeginBalance(double beginBalance) {
                this.beginBalance = beginBalance;
            }

            public double getTradeBalance() {
                return tradeBalance;
            }

            public void setTradeBalance(double tradeBalance) {
                this.tradeBalance = tradeBalance;
            }

            public double getFinalBalance() {
                return finalBalance;
            }

            public void setFinalBalance(double finalBalance) {
                this.finalBalance = finalBalance;
            }

            public String getTradeUserTypeAlias() {
                return tradeUserTypeAlias;
            }

            public void setTradeUserTypeAlias(String tradeUserTypeAlias) {
                this.tradeUserTypeAlias = tradeUserTypeAlias;
            }

            public String getTradeUnitName() {
                return tradeUnitName;
            }

            public void setTradeUnitName(String tradeUnitName) {
                this.tradeUnitName = tradeUnitName;
            }

            public String getTradeDatetime() {
                return tradeDatetime;
            }

            public void setTradeDatetime(String tradeDatetime) {
                this.tradeDatetime = tradeDatetime;
            }
        }
    }
}
