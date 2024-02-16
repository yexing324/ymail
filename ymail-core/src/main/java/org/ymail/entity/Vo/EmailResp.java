package org.ymail.entity.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ymail.entity.Email;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailResp extends Email {
    String statusText;

}
