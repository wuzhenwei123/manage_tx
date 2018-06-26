package com.base.utils.yft.bean;

/**
 * Created by zhy on 2017/5/14.
 */
public class MerchantRegister {
    private String mcc;
    private String groupId;
    private String merchantCname;
    private String abbrCname;
    private String addressChn;
    private String cityCname;
    private String telephone;
    private String manager;
    private String angentId;
    private String creditRate;
    private String creditMaxAmt;
    private String debitRate;
    private String debitMaxAmt;
    private String connMd;
    private String stlCycle;
    private String stlBankCode;//结算账户所属银行编码
    private String stlAccName;//结算账户名称
    private String stlAccNo;//结算银行账户号
    private String stlAccType;//结算银行账户属性 0:对公 1：对私
    private String stlBankUnion;//结算银行联行号
    private String biCardNo;//营业证件号码
    private String legalName;//法人姓名
    private String legalCardType;//法人证件类型
    private String legalCardNo;//法人证件号码
    private String registerAccount;
    private String termSn;
    private String merchantId;
    private String searchCount;
    private String daySumAmt; // 日累计交易金额
    private String feeExtraction; //提现费率
    private String merName; //提现费率
    private String accNo; //提现费率
    private String certifTp; //提现费率
    private String certifId; //提现费率
    private String customerNm; //提现费率
    private String bankNo; //提现费率
    private String smsId; //提现费率
    private String smsCode; //提现费率
    private String status; //提现费率
    private String xwMerchantId; //提现费率
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXwMerchantId() {
		return xwMerchantId;
	}

	public void setXwMerchantId(String xwMerchantId) {
		this.xwMerchantId = xwMerchantId;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getCertifTp() {
		return certifTp;
	}

	public void setCertifTp(String certifTp) {
		this.certifTp = certifTp;
	}

	public String getCertifId() {
		return certifId;
	}

	public void setCertifId(String certifId) {
		this.certifId = certifId;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	private String phoneNo; //提现费率

    public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRegisterAccount() {
        return registerAccount;
    }

    public void setRegisterAccount(String registerAccount) {
        this.registerAccount = registerAccount;
    }

    public String getTermSn() {
        return termSn;
    }

    public void setTermSn(String termSn) {
        this.termSn = termSn;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(String searchCount) {
        this.searchCount = searchCount;
    }

    public String getDaySumAmt() {
        return daySumAmt;
    }

    public void setDaySumAmt(String daySumAmt) {
        this.daySumAmt = daySumAmt;
    }

    public String getFeeExtraction() {
        return feeExtraction;
    }

    public void setFeeExtraction(String feeExtraction) {
        this.feeExtraction = feeExtraction;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMerchantCname() {
        return merchantCname;
    }

    public void setMerchantCname(String merchantCname) {
        this.merchantCname = merchantCname;
    }

    public String getAbbrCname() {
        return abbrCname;
    }

    public void setAbbrCname(String abbrCname) {
        this.abbrCname = abbrCname;
    }

    public String getAddressChn() {
        return addressChn;
    }

    public void setAddressChn(String addressChn) {
        this.addressChn = addressChn;
    }

    public String getCityCname() {
        return cityCname;
    }

    public void setCityCname(String cityCname) {
        this.cityCname = cityCname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAngentId() {
        return angentId;
    }

    public void setAngentId(String angentId) {
        this.angentId = angentId;
    }

    public String getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(String creditRate) {
        this.creditRate = creditRate;
    }

    public String getCreditMaxAmt() {
        return creditMaxAmt;
    }

    public void setCreditMaxAmt(String creditMaxAmt) {
        this.creditMaxAmt = creditMaxAmt;
    }

    public String getDebitRate() {
        return debitRate;
    }

    public void setDebitRate(String debitRate) {
        this.debitRate = debitRate;
    }

    public String getDebitMaxAmt() {
        return debitMaxAmt;
    }

    public void setDebitMaxAmt(String debitMaxAmt) {
        this.debitMaxAmt = debitMaxAmt;
    }

    public String getConnMd() {
        return connMd;
    }

    public void setConnMd(String connMd) {
        this.connMd = connMd;
    }

    public String getStlCycle() {
        return stlCycle;
    }

    public void setStlCycle(String stlCycle) {
        this.stlCycle = stlCycle;
    }

    public String getStlBankCode() {
        return stlBankCode;
    }

    public void setStlBankCode(String stlBankCode) {
        this.stlBankCode = stlBankCode;
    }

    public String getStlAccName() {
        return stlAccName;
    }

    public void setStlAccName(String stlAccName) {
        this.stlAccName = stlAccName;
    }

    public String getStlAccNo() {
        return stlAccNo;
    }

    public void setStlAccNo(String stlAccNo) {
        this.stlAccNo = stlAccNo;
    }

    public String getStlAccType() {
        return stlAccType;
    }

    public void setStlAccType(String stlAccType) {
        this.stlAccType = stlAccType;
    }

    public String getStlBankUnion() {
        return stlBankUnion;
    }

    public void setStlBankUnion(String stlBankUnion) {
        this.stlBankUnion = stlBankUnion;
    }

    public String getBiCardNo() {
        return biCardNo;
    }

    public void setBiCardNo(String biCardNo) {
        this.biCardNo = biCardNo;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalCardType() {
        return legalCardType;
    }

    public void setLegalCardType(String legalCardType) {
        this.legalCardType = legalCardType;
    }

    public String getLegalCardNo() {
        return legalCardNo;
    }

    public void setLegalCardNo(String legalCardNo) {
        this.legalCardNo = legalCardNo;
    }
}
