package com.zljx.pojo;

import java.io.Serializable;

/**
 * @Author: sunzhihan
 * @Date：2019/08/21 9:15
 * @Version 1.0
 **/
public class CTAndMRI implements Serializable {

    private String skill_pName;

    private String skill_mrNo;

    private String skill_operDate;

    public String getSkill_pName() {
        return skill_pName;
    }

    public void setSkill_pName(String skill_pName) {
        this.skill_pName = skill_pName;
    }

    public String getSkill_mrNo() {
        return skill_mrNo;
    }

    public void setSkill_mrNo(String skill_mrNo) {
        this.skill_mrNo = skill_mrNo;
    }

    public String getSkill_operDate() {
        return skill_operDate;
    }

    public void setSkill_operDate(String skill_operDate) {
        this.skill_operDate = skill_operDate;
    }
}
