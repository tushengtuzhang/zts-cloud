package com.zts.service.match.constant;

public class CaseConstant {

    public enum ConditionValueType{
        //临时语料
        TEMPORARY(0),
        //语料
        CORPUS(1),
        //标签
        LABEL(2);

        private int value;

        private ConditionValueType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum CaseActionGeneral{
        //专用（只用于自身的）
        ITSELF(0),
        //通用
        GENERAL(1);

        private int value;

        private CaseActionGeneral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
