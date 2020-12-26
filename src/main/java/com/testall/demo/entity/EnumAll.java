package com.testall.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EnumAll {


    @Getter
    @AllArgsConstructor
     enum LabelEnum {
        HX("核心团队","核心团队描述（手动更新）"),
        SC("核心团队","市场核心人物"),
        YY("核心团队","运营核心人物"),
        JD("核心团队","决定托管外包的关键人物"),
        BJ("核心团队","核心团队背景"),

        SF("投资绩效","是否量化交易"),

        HT("治理运营","合同时效"),
        JG("治理运营","特殊产品结构"),
        QK("治理运营","销售情况"),
        DJ("治理运营","份额登记"),
        QS("治理运营","TA清算"),
        TG("治理运营","托管划款"),
        GZ("治理运营","估值核算"),
        XB("治理运营","信批和报表"),
        HG("治理运营","合规风控"),


        PX("需求偏好","培训教育支持"),
        ZC("需求偏好","IT服务支持"),
        PH("需求偏好","其他需求偏好");
        private String desc;
        private String type;
        public static String getType(String type) {
            for (LabelEnum labelEnum : LabelEnum.values()) {
                if (labelEnum.getType().equals(type)) {
                    return labelEnum.getDesc();
                }
            }
            return null;
        }

    }

}
