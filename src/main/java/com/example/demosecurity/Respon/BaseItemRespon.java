package com.example.demosecurity.Respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseItemRespon<T> extends BaseRespon{
    private T data;
    public BaseItemRespon(boolean success, T data){
        super(success);
        this.data = data;
    }
}
