package com.example.demosecurity.Respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseListItemRespon<T> extends BaseRespon{
    private DataList<T> dataList;

    @Data
    public static class DataList<T>{
        private Integer total;
        private List<T> items;
    }

    public void setResult(Integer total, List<T> item){
        dataList = new DataList<>();
        dataList.setTotal(total);
        dataList.setItems(item);
    }
}
