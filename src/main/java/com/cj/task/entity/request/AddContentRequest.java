package com.cj.task.entity.request;

import java.util.List;

/**
 * Created by cj on 2018/9/9.
 */
public class AddContentRequest {

    private List<ValuesBean> values;

    public List<ValuesBean> getValues() {
        return values;
    }

    public void setValues(List<ValuesBean> values) {
        this.values = values;
    }

    public static class ValuesBean {
        /**
         * field_id : 1
         * value : 男
         */

        private int fieldId;
        private String value;

        public int getFieldId() {
            return fieldId;
        }

        public void setFieldId(int fieldId) {
            this.fieldId = fieldId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
