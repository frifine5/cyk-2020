package com.cyk.user.bean;




public class UserVo {

    String firstname;                   // 名字
    String updatepwd;                   // 修改密码时间
    String timezone;                    // 时区
    String description;                 // 描述
    String issyncthirdparty;            //
    String adloginid;                   // 用户的ad账号，默认为loginid
    String title;                       // 用户职务
    String password;                    // 密码
    int allowuseemail;                  // 是否发邮件
    String islg;                        //
    String prelang;                     // 首选语言
    int ordernumber;                    // 用户排序号
    String fax;                         // 传真
    String pinyininitials;              // 姓名拼音首字母
    String email;                       // 邮箱
    int isenabled;                      // 是否启用，1启用
    String leaderguid;                  // 直属领导编号
    String loginid;                     // 用户登录名
    String ouguid;                      // 用户对应的部门guid
    String telephoneoffice;             // 用户办公室电话号码
    String sex;                         // 用户性别
    String enabledtype;                 //
    String mobile;                      // 移动电话
    String middlename;                  // 中间名
    UserExtendInfo userextendinfo;    // 用户扩展信息
    String oucodelevel;                 // 部门编号级别
    String lastname;                    // 姓氏
    String userguid;                    // 用户guid
    String displayname;                 // 姓名
    String md5id;                       //
    String updatetime;                  // 最后更新时间
    int row_id;                         //
    String telephonehome;               // 用户家庭号码
    String tenantguid;                  //


    public String getFirstname() {
        return null==firstname?"": firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUpdatepwd() {
        return null==updatepwd?"":updatepwd;
    }

    public void setUpdatepwd(String updatepwd) {
        this.updatepwd = updatepwd;
    }

    public String getTimezone() {
        return null==timezone?"":timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDescription() {
        return null==description?"":description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssyncthirdparty() {
        return null==issyncthirdparty?"":issyncthirdparty;
    }

    public void setIssyncthirdparty(String issyncthirdparty) {
        this.issyncthirdparty = issyncthirdparty;
    }

    public String getAdloginid() {
        return null==adloginid?"":adloginid;
    }

    public void setAdloginid(String adloginid) {
        this.adloginid = adloginid;
    }

    public String getTitle() {
        return null==title?"":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return null==password?"":password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAllowuseemail() {
        return allowuseemail;
    }

    public void setAllowuseemail(int allowuseemail) {
        this.allowuseemail = allowuseemail;
    }

    public String getIslg() {
        return null==islg?"":islg;
    }

    public void setIslg(String islg) {
        this.islg = islg;
    }

    public String getPrelang() {
        return null==prelang?"":prelang;
    }

    public void setPrelang(String prelang) {
        this.prelang = prelang;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getFax() {
        return null==fax?"":fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPinyininitials() {
        return null==pinyininitials?"":pinyininitials;
    }

    public void setPinyininitials(String pinyininitials) {
        this.pinyininitials = pinyininitials;
    }

    public String getEmail() {
        return null==email?"":email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(int isenabled) {
        this.isenabled = isenabled;
    }

    public String getLeaderguid() {
        return null==leaderguid?"":leaderguid;
    }

    public void setLeaderguid(String leaderguid) {
        this.leaderguid = leaderguid;
    }

    public String getLoginid() {
        return null==loginid?"":loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getOuguid() {
        return null==ouguid?"":ouguid;
    }

    public void setOuguid(String ouguid) {
        this.ouguid = ouguid;
    }

    public String getTelephoneoffice() {
        return null==telephoneoffice?"":telephoneoffice;
    }

    public void setTelephoneoffice(String telephoneoffice) {
        this.telephoneoffice = telephoneoffice;
    }

    public String getSex() {
        return null==sex?"":sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEnabledtype() {
        return null==enabledtype?"":enabledtype;
    }

    public void setEnabledtype(String enabledtype) {
        this.enabledtype = enabledtype;
    }

    public String getMobile() {
        return null==mobile?"":mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMiddlename() {
        return null==middlename?"":middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public UserExtendInfo getUserextendinfo() {
        return userextendinfo;
    }

    public void setUserextendinfo(UserExtendInfo userextendinfo) {
        this.userextendinfo = userextendinfo;
    }

    public String getOucodelevel() {
        return null==oucodelevel?"":oucodelevel;
    }

    public void setOucodelevel(String oucodelevel) {
        this.oucodelevel = oucodelevel;
    }

    public String getLastname() {
        return null==lastname?"":lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserguid() {
        return null==userguid?"":userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public String getDisplayname() {
        return null==displayname?"":displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getMd5id() {
        return null==md5id?"":md5id;
    }

    public void setMd5id(String md5id) {
        this.md5id = md5id;
    }

    public String getUpdatetime() {
        return null==updatetime?"":updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getTelephonehome() {
        return null==telephonehome?"":telephonehome;
    }

    public void setTelephonehome(String telephonehome) {
        this.telephonehome = telephonehome;
    }

    public String getTenantguid() {
        return null==tenantguid?"":tenantguid;
    }

    public void setTenantguid(String tenantguid) {
        this.tenantguid = tenantguid;
    }
}
