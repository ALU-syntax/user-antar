package com.insoftbumdesku.passenger.json;

import java.io.Serializable;
import java.util.List;

public class MobileTopUpPostPaidStatusJson implements Serializable {

    private MobileTOpUpPostPaidDetailJson data;
    private List<?> meta;

    public MobileTOpUpPostPaidDetailJson getData() {
        return data;
    }

    public void setData(MobileTOpUpPostPaidDetailJson data) {
        this.data = data;
    }

    public List<?> getMeta() {
        return meta;
    }

    public void setMeta(List<?> meta) {
        this.meta = meta;
    }

    public static class MobileTOpUpPostPaidDetailJson {
        private int tr_id;
        private String code;
        private String datetime;
        private String hp;
        private String tr_name;
        private String period;
        private int nominal;
        private int admin;
        private int status;
        private String response_code;
        private String message;
        private int price;
        private int selling_price;
        private int balance;
        private String noref;
        private String ref_id;
        private MobileTopUpPostPaidDescriptionJson desc;

        public int getTr_id() {
            return tr_id;
        }

        public void setTr_id(int tr_id) {
            this.tr_id = tr_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getHp() {
            return hp;
        }

        public void setHp(String hp) {
            this.hp = hp;
        }

        public String getTr_name() {
            return tr_name;
        }

        public void setTr_name(String tr_name) {
            this.tr_name = tr_name;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public int getNominal() {
            return nominal;
        }

        public void setNominal(int nominal) {
            this.nominal = nominal;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getResponse_code() {
            return response_code;
        }

        public void setResponse_code(String response_code) {
            this.response_code = response_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(int selling_price) {
            this.selling_price = selling_price;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getNoref() {
            return noref;
        }

        public void setNoref(String noref) {
            this.noref = noref;
        }

        public String getRef_id() {
            return ref_id;
        }

        public void setRef_id(String ref_id) {
            this.ref_id = ref_id;
        }

        public MobileTopUpPostPaidDescriptionJson getDesc() {
            return desc;
        }

        public void setDesc(MobileTopUpPostPaidDescriptionJson desc) {
            this.desc = desc;
        }

        public static class MobileTopUpPostPaidDescriptionJson {

            private String kode_area;
            private String divre;
            private String datel;
            private int jumlah_tagihan;
            private MobileTopUpPostPaidBillJson tagihan;

            public String getKode_area() {
                return kode_area;
            }

            public void setKode_area(String kode_area) {
                this.kode_area = kode_area;
            }

            public String getDivre() {
                return divre;
            }

            public void setDivre(String divre) {
                this.divre = divre;
            }

            public String getDatel() {
                return datel;
            }

            public void setDatel(String datel) {
                this.datel = datel;
            }

            public int getJumlah_tagihan() {
                return jumlah_tagihan;
            }

            public void setJumlah_tagihan(int jumlah_tagihan) {
                this.jumlah_tagihan = jumlah_tagihan;
            }

            public MobileTopUpPostPaidBillJson getTagihan() {
                return tagihan;
            }

            public void setTagihan(MobileTopUpPostPaidBillJson tagihan) {
                this.tagihan = tagihan;
            }

            public static class MobileTopUpPostPaidBillJson {
                private List<MobileTopUpPostPaidBillDetailJson> detail;

                public List<MobileTopUpPostPaidBillDetailJson> getDetail() {
                    return detail;
                }

                public void setDetail(List<MobileTopUpPostPaidBillDetailJson> detail) {
                    this.detail = detail;
                }

                public static class MobileTopUpPostPaidBillDetailJson {

                    private String periode;
                    private String nilai_tagihan;
                    private String admin;
                    private int total;

                    public String getPeriode() {
                        return periode;
                    }

                    public void setPeriode(String periode) {
                        this.periode = periode;
                    }

                    public String getNilai_tagihan() {
                        return nilai_tagihan;
                    }

                    public void setNilai_tagihan(String nilai_tagihan) {
                        this.nilai_tagihan = nilai_tagihan;
                    }

                    public String getAdmin() {
                        return admin;
                    }

                    public void setAdmin(String admin) {
                        this.admin = admin;
                    }

                    public int getTotal() {
                        return total;
                    }

                    public void setTotal(int total) {
                        this.total = total;
                    }
                }
            }
        }
    }
}
