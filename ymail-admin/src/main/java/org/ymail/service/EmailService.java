package org.ymail.service;

import org.ymail.entity.Email;
import org.ymail.entity.Group;
import org.ymail.entity.Vo.GroupVo;
import org.ymail.resp.EmailBo;
import org.ymail.util.Result;

import java.util.List;

public interface EmailService {


    /**
     * 收信接口
     * @return 收件信息
     */
    Result<Object> getMessage();

    Result<EmailBo> readEmail(String emailId);

    Result<Void> deleteEmail(List<Email> deleteEmail);

    Result<Void> markRead(List<Email> markReadEmail);

    Result<Void> markNotRead(List<Email> markNotReadEmail);

    Result<Void> markAllRead();

    Result<Object> getSendBox();

    Result<Object> getEmailByReceiveGroup(String group,int page,int size);
    Result<Object> getEmailByGroup(String group, int page, int size);

    Result<Void> moveEmailGroup(List<Email>emails,String group);

    Result<Void> reportEmail(List<Email> email,String reason);


    Result<Void> setEmailPinned(List<Email> setEmailPinnedList,String group);

    Result<Void> cancelSetEmailPinned(List<Email> emails, String group);

    Result<Void> markEmailColor(List<Email> emails, String color);

    Result<Void> createEmailFolder(List<Email> emails, String group);

    Result<List<Group>> getGroupList();

    Result<Void> sendCode(String phone);

    Result<Void> changePassWord(String oldP, String newP);

    Result<List<GroupVo>>  getGroupInfo();

    Result<Void> changeGroupName(String oldGroupName, String newGroupName);

    Result<Void> deleteGroup(String groupName);
}
